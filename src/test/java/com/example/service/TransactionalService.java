package com.example.service;

import com.example.mybatis.demo.domain.MUser;
import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;
import com.example.mybatis.demo.params.UserParams;

/**
 * @ClassName: TransactionalService
 * @Description: TODO
 * @Author: yanrong
 * @Date: 8/22/2019 10:15 AM
 * @Version: 1.0
 */
public interface TransactionalService {
    /**
     *  添加用户信息
     * @param userInfo
     * @return
     */
    Integer saveUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 添加订单
     * @param uOrder
     * @return
     */
    Integer saveOrder(UOrder uOrder) throws Exception;

    /**
     * 添加商品信息
     * @param product
     * @return
     */
    Integer saveProduct(Product product);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    Integer updateUserInfo(UserInfo userInfo);

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
