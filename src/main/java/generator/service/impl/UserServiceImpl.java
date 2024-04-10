package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.User;
import generator.service.UserService;
import com.cuiyq.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author cuiyq
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-04-10 22:45:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




