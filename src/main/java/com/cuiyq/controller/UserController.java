package com.cuiyq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuiyq.model.domain.User;
import com.cuiyq.model.domain.request.UserRegisterRequest;
import com.cuiyq.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.cuiyq.contant.UserContant.ADMIN_USERROLE;
import static com.cuiyq.contant.UserContant.USER_LOGIN_STATE;

/**
 * @author cuiyq
 * @createDate
 * @description 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (null == userRegisterRequest) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
//        如果参数为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);

    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return user;

    }

    /**
     * 根据用户名模糊查询
     *
     * @return
     */
    @GetMapping("/search")
    public List<User> userSearch(String username, HttpServletRequest request) {

//        鉴权
        if (!isAdmin(request)) { //如果不是管理员，返回一个空数组
            return new ArrayList<>();
        }
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("username", username);
        }

        return userService.list(queryWrapper);
    }


    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    @PostMapping("/delete")
    public boolean userDelete(@Param("id") Long id,HttpServletRequest request) {
//      鉴权
        if (!isAdmin(request)) {
            return false;
        }

//      如果id <=0则代表错误
            if (id <= 0) {
                return false;
            }

        return userService.removeById(id);
    }

    /**
     * 判断是否是管理员
     *
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
        if (user == null) {
            return false;
        }
        return user.getUserRole() == ADMIN_USERROLE;
    }
}
