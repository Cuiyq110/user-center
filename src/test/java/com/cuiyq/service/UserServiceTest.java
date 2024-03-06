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
}