package com.cuiyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.model.domain.User;
import com.cuiyq.service.UserService;
import com.cuiyq.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cuiyq.contant.UserContant.USER_LOGIN_STATE;


/**
 * @author cuiyq
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-03-06 10:30:18
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static  String SALT = "cuiyq"; //密码盐
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer userLogout(HttpServletRequest request) {
        return null;
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

//        判断非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

//        长度不小于4位
        if (userAccount.length() < 4) {
            return -1;
        }

//        密码不小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

//        账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }


        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }

//      密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

//        对密码进行加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

//        向数据库中插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        boolean save = this.save(user);

        if (!save) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //        判断非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

//        长度不小于4位
        if (userAccount.length() < 4) {
            return null;
        }

//        密码不小于8位
        if (userPassword.length() < 8) {
            return null;
        }


        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

//        对密码进行加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

//        数据库中查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
//        用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

//        用户脱敏
        User safetyUser = getSafetyUser(user);

//        保存登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

//    用户脱敏方法
    @Override
    public User getSafetyUser(User user) {
        if (user == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUserRole(user.getUserRole());
        return safetyUser;
    }


}




