package com.example.service.impl;

import com.example.byr.demo.domain.Account;
import com.example.byr.demo.domain.MUser;
import com.example.byr.demo.domain.Product;
import com.example.byr.demo.domain.UOrder;
import com.example.byr.demo.dto.UserInfo;
import com.example.byr.demo.mapper.AccountMapper;
import com.example.byr.demo.mapper.MUserMapper;
import com.example.byr.demo.mapper.ProductMapper;
import com.example.byr.demo.mapper.UOrderMapper;
import com.example.service.TransactionalService;
import com.example.service.TransactionalService1;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: TransactionalServiceImpl
 * @Description: TODO
 * @Author: yanrong
 * @Date: 8/22/2019 10:16 AM
 * @Version: 1.0
 */
@Service
@Log4j2
public class TransactionalServiceImpl implements TransactionalService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private MUserMapper mUserMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UOrderMapper uOrderMapper;
    @Autowired
    private TransactionalService1 transactionalService1;

    private static  String date=new Date().toString();
    /**
     * 事务A调用事务B，如A发生错误则B事务不会滚
     * @param userInfo
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public boolean saveUserInfo(UserInfo userInfo,boolean oneThrow,boolean twoThrow) throws Exception {
      Account account=new Account();
        MUser mUser = mUserMapper.selectUserById(userInfo.getId());
        if(mUser == null){
            return false;
        }
        //调用事务B
        twoThrow=transactionalService1.saveUserInfo(userInfo,twoThrow);

        account.setUserId(userInfo.getId().longValue());
        account.setInfo("事务A"+date);
        //更新账号表
        Integer a=accountMapper.insert(account);
//        int i=10/0;
        if(!twoThrow){
            throw new Exception("事务B运行失败");
        }
        if(a <= 0){
            return false;
        }
        return true;
    }

    /**
     * 被事务方法调用，会起一个新的子事务并设置savepoint（数据库设置子事务）, 在方法已提交，但是调用端事务回滚时，本方法也会回滚
     * @param uOrder
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveOrder(UOrder uOrder,boolean oneThrow,boolean twoThrow) throws Exception{
        Integer conut=0;
        Account account=new Account();
            MUser mUser = mUserMapper.selectUserById(uOrder.getUserId().intValue());
            if(mUser == null){
                return false;
            }
            //调用事务B
            oneThrow=transactionalService1.saveOrder(uOrder,twoThrow);
            if(!oneThrow){
                throw new RuntimeException("调用事务A失败");
            }
            account.setInfo("调用事务A"+date);
            account.setUserId(uOrder.getUserId());
            conut=accountMapper.insert(account);
            if(!(conut > 0)){
                throw new RuntimeException("调用事务A失败！");
            }
            return true;
        }

    /**
     *  更新用户信息 调用端（A）没有事务，被调用端(B)为事务方法, B 的结果不会引起A的回滚
     * @param product
     * @return
     * @throws RuntimeException
     */
    @Override
    public boolean saveProduct(Product product ,boolean oneThrow,boolean twoThrow) throws RuntimeException {
        Integer productCount =0;
        product.setName("事务A"+date);
        productCount=productMapper.insert(product);
        //调用事务B
        oneThrow=transactionalService1.saveProduct(product,twoThrow);
        if (!oneThrow){
            throw new RuntimeException("调用事务B失败");
        }
        if(!(productCount > 0)){
            return false;
        }
        return true;
    }


    /**
     *
     * @param userInfo
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public boolean updateUserInfo(UserInfo userInfo,boolean oneThrow,boolean twoThrow)throws RuntimeException{
        Integer userCount=0;
            MUser mUser = mUserMapper.selectUserById(userInfo.getId());
            if(mUser == null){
                return false;
            }
            //调用事务B
            oneThrow=transactionalService1.updateUserInfo(userInfo,twoThrow);
            mUser.setName("事务A"+date);
            mUser.setPwd(userInfo.getPwd());
            userCount=mUserMapper.updateByPrimaryKey(mUser);
            if(!oneThrow){
                throw new RuntimeException("事务B运行错误");
            }
           if(!(userCount > 0)){
               throw new RuntimeException("事务A运行错误");
           }
        return  true;
    }

    @Override
    public Integer updateProduct(Product product) {
        return null;
    }

    /**
     * 如果没有，就以非事务方式执行；如果有，就使用当前事务。
     * @param uOrder
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    public Integer updateOrder(UOrder uOrder) throws RuntimeException {
        Integer orderCount=0;
        Product product=new Product();
        try {
            uOrder.setName("事务A"+date);
            orderCount=uOrderMapper.updateByPrimaryKey(uOrder);
            if (orderCount > 0) {
                product.setId(uOrder.getUserId());
                productMapper.updateByPrimaryKey(product);
            }
            return orderCount;
        }catch (RuntimeException e){
            throw  new RuntimeException("订单更新失败",e);
        }
    }
}
