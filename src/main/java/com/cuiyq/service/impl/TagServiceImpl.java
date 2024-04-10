package com.cuiyq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuiyq.model.domain.Tag;
import com.cuiyq.service.TagService;
import com.cuiyq.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author cuiyq
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2024-04-10 22:16:06
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




