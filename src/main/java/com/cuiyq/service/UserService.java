package com.cuiyq.service;

import com.cuiyq.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author cuiyq
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-03-06 10:30:18
*/
public interface UserService extends IService<User> {


    /**
     * 注册
     * @param userAccount 用户名
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 返回id
     */
    long register(String userAccount, String userPassword,String checkPassword);

}
