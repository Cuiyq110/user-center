package com.cuiyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.domain.User;
import com.cuiyq.service.UserService;
import com.cuiyq.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author cuiyq
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-03-06 10:30:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    public final String SALT = "cuiyq";
    @Resource
    private UserMapper userMapper;

    @Override
    public long register(String userAccount, String userPassword, String checkPassword) {

//        判断非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

//        长度不小于4位
        if (userPassword.length() < 4) {
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
}




