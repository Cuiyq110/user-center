package com.cuiyq.service;

import java.util.Date;

import com.cuiyq.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAdd() {
        User user = new User();
        user.setUsername("1");
        user.setUserAccount("1");
        user.setAvatarUrl("1");
        user.setGender(0);
        user.setUserPassword("1");
        user.setEmail("1");
        user.setUserStatus(0);
        user.setPhone("1");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);
    }

    @Test
    void register() {
//        非空
        String userAccount = "cuiyq1";
        String userPassword = "";
        String checkPassword = "12345678";
        long result = userService.register(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

//        账户长度不小于4位
        userAccount = "cu";
        result = userService.register(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

//        密码不小于8位
        userAccount = "cuiyq1";
        userPassword = "123456";
        result = userService.register(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

//        账户不能重复
        userAccount = "cuiyq";
        userPassword = "12345678";
        result = userService.register(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

//        账户不能包含特殊字符
        userAccount = "cuiyq1%";
        result = userService.register(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

//      密码和校验密码相同
        userAccount = "cuiyq1";
        userPassword = "12345678";
        checkPassword = "1234567";
        result = userService.register(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}