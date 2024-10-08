package com.cuiyq.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.common.ErrorCode;
import com.cuiyq.exception.BusinessException;
import com.cuiyq.mapper.UserMapper;
import com.cuiyq.model.domain.User;
import com.cuiyq.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cuiyq.contant.UserContant.ADMIN_USERROLE;
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

    private static String SALT = "cuiyq"; //密码盐
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 根据标签搜索用户(sql查询版)（已弃用）
     *
     * @param tagNameList 标签json表
     * @return
     */
    @Deprecated
    @Override
    public List<User> searchUserByTagsBySql(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//       拼接and语句
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for (String tagName : tagNameList) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }

        List<User> userList = userMapper.selectList(queryWrapper);
        //返回一个脱敏的集合
        return userList.stream()
                .map((User -> getSafetyUser(User)))
                .collect(Collectors.toList());
    }

    /**
     * 根据标签搜索用户内存(内存过滤版)
     * @param tagNameList 标签json
     * @return
     */
    @Override
    public List<User> searchUserByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
//        1.先查询所有用户
        QueryWrapper queryWrapper = new QueryWrapper();
        List<User> userList = userMapper.selectList(queryWrapper);//所有用户
        Gson gson = new Gson();
//        2.在内存中判断中判断是否包含要求的标签
       return userList.stream().filter(user -> {
//           如果user没有这个标签直接返回false
            String tagsStr = user.getTags();
           if (tagsStr == null) {
               return false;
           }
            // 使用Gson将json字符串转化为String
            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
            }.getType());
            for (String tagName : tagNameList) {
                if (!tempTagNameSet.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).map(user -> getSafetyUser(user)).collect(Collectors.toList());
    }

    /**
     * 用户信息修改
     * @param user
     * @param loginUser
     * @return
     */
    @Override
    public Integer updateUser(User user, User loginUser) {
        long userId = user.getId();
        if (userId <= 0 ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        判断仅管理员和自己可以修改
        if (!isAdmin(loginUser) && userId != loginUser.getId()) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

//        从数据库中查找用户
        User userold = userMapper.selectById(userId);
        if (userold == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return userMapper.updateById(user);
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) attribute;
    }

    /**
     * 判断是否是管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
        if (user == null) {
            return false;
        }
        return user.getUserRole() == ADMIN_USERROLE;
    }

    /**
     * 判断是否为管理员
     * @param loginUser
     * @return
     */
    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == ADMIN_USERROLE;
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {

//        判断非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

//        长度不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }

//        密码不小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

//        补充对星球验证码的校验，不能大于5
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号过长");
        }

//        账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        //星球验证码不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号重复");
        }

        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号含有特殊字符");
        }

//      密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }

//        对密码进行加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

//        向数据库中插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setPlanetCode(planetCode);
        /**
         * 自动添加默认头像
         */
        user.setAvatarUrl("http://image.cuiyq.top//img/0");
        boolean save = this.save(user);

        if (!save) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存数据库失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //        判断非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码不能为空");
        }

//        长度不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度小于4位");
        }

//        密码不小于8位
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }


        // 账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含特殊字符");
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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在或密码不正确");
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
        safetyUser.setPlanetCode(user.getPlanetCode());
        safetyUser.setTags(user.getTags());
        return safetyUser;
    }


}




