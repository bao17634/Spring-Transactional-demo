package com.example.byr.demo.service;


import com.example.byr.demo.domain.Account;
import com.example.byr.demo.domain.MUser;
import com.example.byr.demo.domain.Product;
import com.example.byr.demo.domain.UOrder;
import com.example.byr.demo.dto.UserInfo;
import java.util.List;
import java.util.Map;

public interface UserService {

    int insertUser(MUser user);


    MUser getUserById(Integer id);

    Map<Integer,MUser> getUserMapByUserName(String userName);

    List<Product> getProduct(Product record);

    UserInfo getUserInfo(Integer userId);

    Account getAccount(Integer userId);

    List<UOrder> getOrderList(Integer userId);

    boolean updateUserInfo1(UserInfo userInfo, boolean OneFlag, boolean TwoFlag) throws Exception;

    boolean updateUserInfo2(UserInfo userInfo, boolean OneFlag, boolean TwoFlag) throws Exception;

    boolean updateUserInfo3(UserInfo userInfo, boolean OneFlag, boolean TwoFlag) throws Exception;

    boolean updateUserInfo4(UserInfo userInfo, boolean OneFlag, boolean TwoFlag) throws Exception;




}
