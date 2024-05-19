package com.cuiyq.once;
import java.util.Date;

import com.cuiyq.mapper.UserMapper;
import com.cuiyq.model.domain.User;

import com.cuiyq.service.UserService;
import org.junit.Test;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/5/14 15:57
 */
public class InsertUsers {


    @Resource
    public UserService userService;
    @Resource
    public UserMapper userMapper;

    @Test
    public void doInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 2000;

        for (int i = 1000; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假数据");
            user.setUserAccount("cuiyq" + i);
            user.setAvatarUrl("http://image.cuiyq.top//img/0");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setEmail("123@qq.com");
            user.setUserStatus(0);
            user.setPhone("123213112");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setIsDelete(0);
            user.setUserRole(0);
            user.setPlanetCode("111");
            user.setTags("[]");
//            userMapper.insert(user);
        }
//            关闭定时任务
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis()); //耗时162179ms

    }


    @Test
    public void doInsertUser2() {

    User user = new User();
    user.setUsername("假数据s");
    user.setUserAccount("cuiyqs");
        System.out.println(user);
//    userService.save(user);
        userMapper.insert(user);


    }
}