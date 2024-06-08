package com.cuiyq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.model.domain.UserTeam;
import com.cuiyq.service.UserTeamService;
import com.cuiyq.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author cyq11
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-06-08 15:43:49
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




