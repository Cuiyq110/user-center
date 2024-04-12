package com.cuiyq.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.cuiyq.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAdd() {
        User user = new User();
        user.setUsername("1");
        user.setUserAccount("1");
        user.setAvatarUrl("1");
        user.setGender(0);
        user.setUserPassword("1");
        user.setEmail("1");
        user.setUserStatus(0);
        user.setPhone("1");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        boolean save = userService.save(user);
        System.out.println(user.getId());
        assertTrue(save);
    }

    @Test
    void register() {
//        非空
        String userAccount = "cuiyq1";
        String userPassword = "";
        String checkPassword = "12345678";
        String planetCode = "100";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

//        账户长度不小于4位
        userAccount = "cu";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

//        密码不小于8位
        userAccount = "cuiyq1";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

//        账户不能重复
        userAccount = "cuiyq";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

//        账户不能包含特殊字符
        userAccount = "cuiyq1%";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

//      密码和校验密码相同
        userAccount = "cuiyq1";
        userPassword = "12345678";
        checkPassword = "1234567";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

//        正常插入
        userAccount = "cuiyq2";
        userPassword = "12345678";
        checkPassword = "12345678";
        planetCode = "101";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        assertTrue(result > 0); //正常插入
//        Assertions.assertEquals(-1, result);

    }

    @Test
    void searchUserByTags() {
//        Java和python紧贴在一起
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchUserByTags(tagNameList);
        assertTrue(userList.size() > 0);

//        java和python中间搁着其他标签
        tagNameList = Arrays.asList("java", "python");
        userList = userService.searchUserByTags(tagNameList);
        assertTrue(userList.size() > 1);

        //        只有java (false)
/*        tagNameList = Arrays.asList("java", "python");
        userList = userService.searchUserByTags(tagNameList);
        assertTrue(userList.size() > 2); */
    }


    @Test
    void searchUserByTagsMemory() {
//        Java和python紧贴在一起
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchUserByTagsMemory(tagNameList);
        assertTrue(userList.size() > 0);

//        java和python中间搁着其他标签
        tagNameList = Arrays.asList("java", "python");
        userList = userService.searchUserByTagsMemory(tagNameList);
        assertTrue(userList.size() > 1);

        //        只有java (false)
/*        tagNameList = Arrays.asList("java", "python");
        userList = userService.searchUserByTags(tagNameList);
        assertTrue(userList.size() > 2); */

        //        java和不存在
        tagNameList = Arrays.asList("java", "null");
        userList = userService.searchUserByTagsMemory(tagNameList);
        assertTrue(userList.size() == 0); //等于0
    }
}