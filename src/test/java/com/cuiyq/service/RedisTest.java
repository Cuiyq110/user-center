package com.cuiyq.service;

import com.cuiyq.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/5/19 17:19
 */
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
//        增
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "cuiyq");
        valueOperations.set("age", 25);
        valueOperations.set("double", 2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("cuiyq");
        valueOperations.set("cuiyqUser",user);

        //查
        Object obj = valueOperations.get("name");
        Assertions.assertTrue("cuiyq".equals((String)obj));
        obj = valueOperations.get("age");
        Assertions.assertTrue(25 == (Integer) obj);
        obj = valueOperations.get("double");
        Assertions.assertTrue(2.0 == (Double) obj);
        System.out.println(valueOperations.get("cuiyqUser"));

//        改
        valueOperations.set("name", "cuiyq2");

//        删
        redisTemplate.delete("name");
    }
}
