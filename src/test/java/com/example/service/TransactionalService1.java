package com.example.service;

import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;

/**
 * @ClassName: TransactionalService
 * @Description: TODO
 * @Author: yanrong
 * @Date: 8/22/2019 10:15 AM
 * @Version: 1.0
 */
public interface TransactionalService1 {
    /**
     *  添加用户信息
     * @param userInfo
     * @return
     */
    boolean saveUserInfo(UserInfo userInfo,boolean twoThrow) throws Exception;

    /**
     * 添加订单
     * @param uOrder
     * @return
     */
    boolean saveOrder(UOrder uOrder,boolean oneThrow) throws RuntimeException;

    /**
     * 添加商品信息
     * @param product
     * @return
     */
    boolean saveProduct(Product product,boolean twoThrow);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    boolean updateUserInfo(UserInfo userInfo,boolean twoThrow);

    /**
     * 更新商品信息
     * @return
     */
    Integer updateProduct(Product product);
    /**
     * 修改订单
     * @param uOrder
     * @return
     */
    Integer updateOrder(UOrder uOrder);
}
