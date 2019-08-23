package com.example.service.impl;

import com.example.mybatis.demo.domain.Account;
import com.example.mybatis.demo.domain.MUser;
import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;
import com.example.mybatis.demo.mapper.AccountMapper;
import com.example.mybatis.demo.mapper.MUserMapper;
import com.example.mybatis.demo.mapper.ProductMapper;
import com.example.mybatis.demo.mapper.UOrderMapper;
import com.example.mybatis.demo.params.UserParams;
import com.example.service.TransactionalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer saveUserInfo(UserInfo userInfo) throws Exception {
      Integer userCount=0;
      Account account=new Account();
    try {
        //添加账号
        log.debug("添加前用户信息：{}",userInfo);
        MUser mUser = mUserMapper.selectUserById(userInfo.getId());
        if(mUser == null){
            return 0;
        }
        mUser.setName(userInfo.getName());
        mUser.setPwd(userInfo.getPwd());
        userCount=mUserMapper.insertUser(mUser);
        log.debug("更新信息条数：{}",userCount);
        if(userCount == null){
            return 0;
        }
        account.setUserId(userInfo.getId().longValue());
        account.setInfo("这是数据更新！");
        //更新账号表
        accountMapper.updateByPrimaryKey(account);
        return userCount;
    }catch (Exception e){
        throw new Exception("用户添加失败添加",e);
    }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    public Integer saveOrder(UOrder uOrder) throws RuntimeException{
        Integer orderCount=0;
        Integer conut=0;
        Account account=new Account();
        try {
            MUser mUser = mUserMapper.selectUserById(uOrder.getUserId().intValue());
            if(mUser == null){
                return 0;
            }
            log.debug("运单原数据：{}",uOrder);
            orderCount=uOrderMapper.insert(uOrder);
            //添加账单信息
            account.setInfo(uOrder.getOrderList());
            //添加用户信息
            account.setUserId(uOrder.getUserId());
            conut=accountMapper.insert(account);
            return orderCount+conut;
        }catch (RuntimeException e){
            throw new RuntimeException("添加运单失败！",e);
        }
    }
    @Override
    public Integer saveProduct(Product product) throws RuntimeException {
        Integer productCount =0;
        try {
            productCount=productMapper.insert(product);
            if(productCount > 0){
                log.debug("开始更新库存数量");
                /*
                执行方法
                 */
            }
            return null;
        }catch (RuntimeException e){
            throw new RuntimeException("添加菜单失败",e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    public Integer updateUserInfo(UserInfo userInfo)throws RuntimeException{
        Integer userCount=0;
        try {
            MUser mUser = mUserMapper.selectUserById(userInfo.getId());
            if(mUser == null){
                return 0;
            }
            mUser.setName(userInfo.getName());
            mUser.setPwd(userInfo.getPwd());
            userCount=mUserMapper.updateByPrimaryKey(mUser);
            return userCount;
        }catch (RuntimeException e){
            throw new RuntimeException("用户资料更新失败",e);
        }
    }

    @Override
    public Integer updateProduct(Product product) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    public Integer updateOrder(UOrder uOrder) throws RuntimeException {
        Integer orderCount=0;
        Product product=new Product();
        try {
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
