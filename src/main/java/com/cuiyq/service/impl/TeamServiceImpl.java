package com.cuiyq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.model.domain.Team;
import com.cuiyq.service.TeamService;
import com.cuiyq.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author cyq11
* @description 针对表【team(队伍)】的数据库操作Service实现
* @createDate 2024-06-08 15:33:21
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




