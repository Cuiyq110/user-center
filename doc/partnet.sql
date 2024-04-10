-- 伙伴匹配系统数据库语句
create table tag
(
    id        int auto_increment comment 'id' primary key ,
    tagName   varchar(256) null comment '标签名',
    userId    bigint       null comment '用户id',
    parentId  bigint       null comment '父标签id',
    isParent  tinyint      null comment '是否为父标签 0-否 1-是',
    creatTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete  tinyint default 0  not null comment '是否删除'
) comment '标签表';

create index idx_user_id
    on tag (userId);

create index uniIdx_tag_name
    on tag (tagName);