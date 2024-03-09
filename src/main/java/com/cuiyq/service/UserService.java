package com.cuiyq.service;

import com.cuiyq.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
* @author cuiyq
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-03-06 10:30:18
*/
public interface UserService extends IService<User> {

       String SALT = "cuiyq"; //密码盐

       String USER_LOGIN_STATE = "userLoginState";
    /**
     * 注册
     * @param userAccount 用户名
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 返回id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


    /**
     * 登录
     * @param userAccount 用户名
     * @param userPassword 密码
     * @return 返回脱敏后的信息
     */

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

}
