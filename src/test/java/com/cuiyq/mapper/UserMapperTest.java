package com.cuiyq.mapper;

import com.cuiyq.model.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/5/16 11:17
 */
@SpringBootTest
public class UserMapperTest {
@Resource
UserMapper userMapper;
@Test
public void doInsertUser2() {

    User user = new User();
    user.setUsername("假数据s");
    user.setUserAccount("cuiyqqq1");
    user.setUserPassword("12345678");
    user.setIsDelete(0);
    user.setUserRole(0);
    System.out.println(user);
//    userService.save(user);
//    mapper.insert(user);
//    System.out.println(mapper);


    System.out.println(userMapper.selectById(20));

}
}