package com.example;

import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;
import com.example.service.TransactionalService;
import org.apache.tomcat.jni.Error;
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

    /**
     * 用来测试事务A调用事务B时事务A报错事务B不回回滚
     *
     * @throws Exception
     */
    @Test
    public void saveUserInfo() throws Exception {
        boolean oneThrow = false;
        boolean twoThrow = false;
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(1);
            userInfo.setAccount(null);
            userInfo.setName("事务A");
            userInfo.setAddress("成都");
            userInfo.setPwd("123456");
            userInfo.setPhone("1234567");
            userInfo.setOrderList(null);
            oneThrow = transactionalService.saveUserInfo(userInfo, oneThrow, twoThrow);
            if (oneThrow) {
                System.out.print("调用成功！");
            }else {
                System.out.print("调用失败！");
            }
        } catch (Exception e) {
            throw new Exception("添加失败", e);
        }

    }

    /**
     * 测试被事务方法调用，会起一个新的子事务并设置savepoint（数据库设置子事务）, 在方法已提交，但是调用端事务回滚时，本方法也会回滚
     *
     * @throws Exception
     */
    @Test
    public void saveOrder() throws Exception {
        boolean oneThrow = false;
        boolean twoThrow = false;
        try {
            UOrder uOrder = new UOrder();
            uOrder.setUserId((long) 1);
            uOrder.setName("baoyyyyyyyy");
            uOrder.setPrice(BigDecimal.valueOf(1100));
            uOrder.setUserId((long) 1);
            oneThrow = transactionalService.saveOrder(uOrder, oneThrow, twoThrow);
            if (oneThrow) {
                System.out.print("调用成功！");
            }else {
                System.out.print("调用失败！");
            }
        } catch (Exception e) {
            throw new Exception("插入失败");
        }
    }

    /**
     * 测试更新用户信息 调用端（A）没有事务，被调用端(B)为事务方法, B 的结果不会引起A的回滚
     */
    @Test
    public void saveProduct() throws RuntimeException {
        Product product = new Product();
        boolean oneThrow = false;
        boolean twoThrow = false;
        try {
            product.setName("yan");
            product.setPrice(BigDecimal.valueOf(100));
            oneThrow = transactionalService.saveProduct(product, oneThrow, twoThrow);
            if (oneThrow) {
                System.out.append("调用成功");
            }else {
                System.out.print("调用失败！");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("调用失败",e);
        }

    }

    /**
     * 测试事务A调用事务B，事务A出现错误事务B也会回滚
     */
    @Test
    public void updateUserInfo() {
        boolean oneThrow = false;
        boolean twoThrow = false;
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(1);
            userInfo.setPhone("1234567890");
            userInfo.setAddress("四川城都");
            userInfo.setPwd("12212");
            oneThrow = transactionalService.updateUserInfo(userInfo, oneThrow, twoThrow);
            if (oneThrow) {
                System.out.print("调用成功！");
            }else {
                System.out.print("调用失败！");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("调用失败！");
        }
    }

}
