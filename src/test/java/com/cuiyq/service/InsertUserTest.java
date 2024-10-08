package com.cuiyq.service;

import com.cuiyq.model.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


@SpringBootTest
public class InsertUserTest {

    @Resource
    private UserService userService;

//    开启多线程
//线程设置
private ExecutorService executorService = new ThreadPoolExecutor(16, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));
    @Test
    public void doInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;

        for (int i = 0; i < INSERT_NUM; i++) {
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
            userService.save(user);
        }
//            关闭定时任务
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis()); //耗时162179ms
    }

    @Test
    public void doInsertUser2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < INSERT_NUM; i++) {
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
            userList.add(user);
        }
        userService.saveBatch(userList,100);
//            关闭定时任务
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis()); //耗时81165ms

    }

    /**
     * 并发批量插入用户   5000 耗时： 59930ms
     * 1000  16825
     * 10000 93190
     * 十万  989481
     */
    @Test
    public void doConcurrencyInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int batchSize = 1000;
        // 分十组
        int j = 0;

        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        // i 要根据数据量和插入批量来计算需要循环的次数。
        for (int i = 0; i < 100; i++) { //插入10万
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("假数据");
                user.setUserAccount("cuiyq");
                user.setAvatarUrl("http://image.cuiyq.top//img/0");
                user.setProfile("一条咸鱼");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("123456789108");
                user.setEmail("123@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setPlanetCode("113");
                user.setTags("[]");
                userList.add(user);
                if (j % batchSize == 0 ){
                    break;
                }
            }
            //异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->{
                System.out.println("ThreadName：" + Thread.currentThread().getName());
                userService.saveBatch(userList,batchSize);
            },executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());

    }




    @Test
    void searchUserByTags() {

    }
}