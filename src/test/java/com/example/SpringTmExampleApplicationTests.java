package com.example;

import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;
import com.example.service.TransactionalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTmExampleApplicationTests {
    @Autowired
    private TransactionalService transactionalService;
    @Test
    public void saveUserInfo()throws Exception{
        UserInfo userInfo=new UserInfo();
        userInfo.setId(1);
        userInfo.setAccount(null);
        userInfo.setName("1");
        userInfo.setAddress("成都");
        userInfo.setPwd("123456");
        userInfo.setPhone("1234567");
        userInfo.setOrderList(null);
        transactionalService.saveUserInfo(userInfo);
    }
    @Test
    public void saveOrder() throws Exception{
        UOrder uOrder=new UOrder();
        uOrder.setUserId((long) 1);
        uOrder.setName("bao");
        uOrder.setPrice(BigDecimal.valueOf(1100));
        uOrder.setUserId((long)1);
        transactionalService.saveOrder(uOrder);
    }
    @Test
    public void saveProduct(){
        Product product=new Product();
        product.setName("yan");
        product. setPrice(BigDecimal.valueOf(100));
        transactionalService.saveProduct(product);
    }
    @Test
    public void updateUserInfo(){
        UserInfo userInfo=new UserInfo();
        userInfo.setPhone("1234567890");
        userInfo.setAddress("四川城都");
        userInfo.setPwd("12212");
        transactionalService.updateUserInfo(userInfo);
    }
    @Test
    public void updateProduct(){
        Product product=new Product();
        product.setName("yan");
        product. setPrice(BigDecimal.valueOf(100));
        transactionalService.updateProduct(product);
    }
    @Test
    public void updateOrder(){
        UOrder uOrder=new UOrder();
        UserInfo userInfo=new UserInfo();
        userInfo.setPhone("1234567890");
        userInfo.setAddress("四川城都");
        userInfo.setPwd("12212");
        transactionalService.updateOrder(uOrder);
    }

}
