package com.cuiyq.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuiyq.common.BaseResponse;
import com.cuiyq.common.ErrorCode;
import com.cuiyq.common.ResultUtils;
import com.cuiyq.exception.BusinessException;
import com.cuiyq.model.domain.User;
import com.cuiyq.model.domain.request.UserRegisterRequest;
import com.cuiyq.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.cuiyq.contant.UserContant.ADMIN_USERROLE;
import static com.cuiyq.contant.UserContant.USER_LOGIN_STATE;

/**
 * @author cuiyq
 * @createDate
 * @description 用户接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173"})
public class UserController {

    @Resource
    private UserService userService;



    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

//        移除登录态
        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (null == userRegisterRequest) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
//        如果参数为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long l = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(l);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User safetyUser = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 根据用户名模糊查询
     *
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> userSearch(String username, HttpServletRequest request) {

//        鉴权
        /**
         * //如果不是管理员，返回一个空数组
         *
         */
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不是管理员");
        }
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("username", username);
        }

        List<User> UserList = userService.list(queryWrapper);

        //用户不存在
        if (UserList.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        List<User> list = UserList.stream()
                .map(user -> userService.getSafetyUser(user))
                .collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 根据标签查询
     *
     * @param tagNameList
     * @return
     */
    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUserByTags( @RequestParam(required = false)List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        List<User> userList = userService.searchUserByTags(tagNameList);
        return ResultUtils.success(userList);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> userUpdate(@RequestBody User user, HttpServletRequest request) {
        //校验参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        2.鉴权
        User loginUser = userService.getLoginUser(request);

//        3.触发更新
        return ResultUtils.success(userService.updateUser(user, loginUser));
    }
    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@Param("id") Long id, HttpServletRequest request) {
//      鉴权
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不是管理员");
        }

//      如果id <=0则代表错误
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        }

        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }


}
