package com.cuiyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuiyq.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @param planetCode 星球验证码
     * @return 返回id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);


    /**
     * 登录
     * @param userAccount 用户名
     * @param userPassword 密码
     * @return 返回脱敏后的信息
     */

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     *     用户脱敏方法
     * @param user
     * @return
     */
    User getSafetyUser(User user);

    /**
     * 用户注销
     * @param request 用户登录态
     * @return
     */
    Integer userLogout(HttpServletRequest request);


    /**
     * 根据标签搜索用户（已弃用）
     *
     * @param tagNameList 标签json表
     * @return
     */
    List<User> searchUserByTagsBySql(List<String> tagNameList);
    /**
     * 根据标签搜索，搜索出来放到内存里进行判断
     * @param tagNameList 标签json
     * @return
     */
    List<User> searchUserByTags(List<String> tagNameList);

    /**
     * 更新用户
     * @param user
     * @param loginUser
     * @return
     */
    Integer updateUser(User user, User loginUser);

    /**
     * 获取当前用户登录信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 判断是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);


    /**
     * 判断是否为管理员
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);
}
