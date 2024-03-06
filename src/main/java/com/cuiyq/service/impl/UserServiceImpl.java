package com.cuiyq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.domain.User;
import com.cuiyq.service.UserService;
import com.cuiyq.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author cuiyq
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-03-06 10:30:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




