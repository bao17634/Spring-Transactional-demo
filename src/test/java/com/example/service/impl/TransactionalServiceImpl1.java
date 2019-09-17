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
import com.example.service.TransactionalService1;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: TransactionalServiceImpl1
 * @Description: TODO
 * @Author: yanrong
 * @Date: 8/23/2019 2:42 PM
 * @Version: 1.0
 */
@Service
@Log4j2
public class TransactionalServiceImpl1 implements TransactionalService1 {
    @Autowired
    private MUserMapper mUserMapper;
    @Autowired
    private UOrderMapper uOrderMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ProductMapper productMapper;
    private static  String date=""+new Date().toString();
    /**
     * REQUIRES_NEW 被已有事务的方法调用，会重新开启一个事务
     * @param userInfo
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class,propagation = Propagation.REQUIRES_NEW)
    public boolean saveUserInfo(UserInfo userInfo,boolean twoThrow) throws RuntimeException {
        Integer userCount=0;
        Account account=new Account();
            MUser mUser = mUserMapper.selectUserById(userInfo.getId());
            if(mUser == null){
                return false;
            }
            mUser.setName("事务B"+date);
            mUser.setPwd(userInfo.getPwd());
            userCount=mUserMapper.insertUser(mUser);
            if(userCount == 0){
                throw new RuntimeException("事务A出现异常，被调用的事务B不会回滚");
            }
            account.setUserId(userInfo.getId().longValue());
            account.setInfo("被调用端事务B");
            //更新账号表
            Integer a=accountMapper.updateByPrimaryKey(account);
           if(a > 0 && userCount > 0){
               throw new RuntimeException("事务A出现异常，被调用的事务B不会回滚");
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
    public boolean saveOrder(UOrder uOrder,boolean twoThrow) throws RuntimeException{
        Integer orderCount=0;
        Integer conut=0;
        Account account=new Account();
            orderCount=uOrderMapper.insert(uOrder);
            if(!(orderCount > 0)){
                throw new RuntimeException("被调用事务B失败！");
            }
            //添加账单信息
            account.setInfo("被调用事务B"+date);
            //添加用户信息
            account.setUserId(uOrder.getUserId());
            conut=accountMapper.insert(account);
            if(!(conut > 0)){
                throw new RuntimeException("事务B调用失败");
            }
        return true;
    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public boolean saveProduct(Product product,boolean twoThrow) throws RuntimeException {
        product.setName("事务B"+date);
        Integer a=productMapper.insert(product);
//        int i=10/0;
       if(!(a > 0)){
           throw new RuntimeException("被调用事务B失败");
       }
       return true;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class,propagation = Propagation.NESTED)
    public boolean updateUserInfo(UserInfo userInfo,boolean twoThrow) {
        MUser mUser = mUserMapper.selectUserById(userInfo.getId());
        if(mUser == null){
            return false;
        }
        mUser.setName("事务B"+date);
        mUser.setPwd(userInfo.getPwd());
        Integer a=mUserMapper.updateByPrimaryKey(mUser);
        if(!(a > 0)){
            throw new RuntimeException("调用事务B失败");
        }
        return true;
    }

    @Override
    public Integer updateProduct(Product product) {
        return null;
    }

    @Override
    public Integer updateOrder(UOrder uOrder) {
        return null;
    }
}
