package com.cuiyq.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuiyq.model.domain.User;
import com.cuiyq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @Title:
 * @Description: 缓存预热
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/5/22 22:12
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 重点用户
    private List<Long> mainUserList = Arrays.asList(1L);

    // 每天执行，预热推荐用户
    @Scheduled(cron = "0 30 22 1/1 * * ")   //每晚22:30进行预热
    public void doCacheRecommendUser() {
        //查数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> userPage = userService.page(new Page<>(1,20),queryWrapper);
        String redisKey = String.format("cuiyq:user:recommend:%s",mainUserList);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //写缓存,30s过期
        try {
            valueOperations.set(redisKey,userPage,5184000, TimeUnit.MILLISECONDS);
        } catch (Exception e){
            log.error("redis set key error",e);
        }
    }

}