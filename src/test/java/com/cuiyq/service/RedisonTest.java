package com.cuiyq.service;

import com.cuiyq.config.RedissonConfig;
import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/5/24 14:05
 */

@SpringBootTest
public class RedisonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("cuiyq");
        list.add("cuiyq2");
        list.remove(0);
        System.out.println("list: " + list);

        RList<String> rList = redissonClient.getList("test-list");
        rList.add("rList");
        System.out.println("rList: " + rList);
      rList.move(0);

        // map
        Map<String, Integer> map = new HashMap<>();
        map.put("map:cuiyq", 10);
        map.get("map:cuiyq");

        RMap<Object, Object> map1 = redissonClient.getMap("test-map");
        map1.put("test-map","RMap:cuiyq");
        System.out.println("map1: " + map1);
        map1.remove(map1);
    }

}
