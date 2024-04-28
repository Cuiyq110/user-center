
use cuiyq;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

drop table user;
create table user
(
    id           bigint auto_increment primary key,
    username     varchar(256)                       null comment '用户昵称',
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 null comment '状态 0-正常',
    phone        varchar(128)                       null comment '电话',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '用户表';

insert into user (username, userAccount, avatarUrl, gender, userPassword, email, userStatus, phone, createTime, updateTime, isDelete) values
                                                                                                                                          ('张三', 'zhangsan123', 'https://example.com/avatar1.jpg', 1, 'password123', 'zhangsan@example.com', 0, '123456789', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('李四', 'lisi456', 'https://example.com/avatar2.jpg', 2, 'password456', 'lisi@example.com', 1, '987654321', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('王五', 'wangwu789', 'https://example.com/avatar3.jpg', 1, 'password789', 'wangwu@example.com', 0, '456789123', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('赵六', 'zhaoliu987', 'https://example.com/avatar4.jpg', 2, 'password987', 'zhaoliu@example.com', 1, '654321789', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('钱七', 'qianqi123', 'https://example.com/avatar5.jpg', 1, 'passwordQ7', 'qianqi@example.com', 0, '321654987', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('孙八', 'sunba123', 'https://example.com/avatar6.jpg', 2, 'passwordS8', 'sunba@example.com', 1, '789456123', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('周九', 'zhoujiu456', 'https://example.com/avatar7.jpg', 1, 'passwordZ9', 'zhoujiu@example.com', 0, '987123654', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('吴十', 'wushi789', 'https://example.com/avatar8.jpg', 2, 'passwordW10', 'wushi@example.com', 1, '159357852', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('郑十一', 'zhengshiyi123', 'https://example.com/avatar9.jpg', 1, 'passwordZ11', 'zhengshiyi@example.com', 0, '258147369', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('王十二', 'wangshier456', 'https://example.com/avatar10.jpg', 2, 'passwordW12', 'wangshier@example.com', 1, '369258147', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('李十三', 'lishisan789', 'https://example.com/avatar11.jpg', 1, 'passwordL13', 'lishisan@example.com', 0, '147258369', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('张十四', 'zhangshisi123', 'https://example.com/avatar12.jpg', 2, 'passwordZ14', 'zhangshisi@example.com', 1, '369147258', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('赵十五', 'zhaoshiwu456', 'https://example.com/avatar13.jpg', 1, 'passwordZ15', 'zhaoshiwu@example.com', 0, '147369258', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('孙十六', 'sunshiliu789', 'https://example.com/avatar14.jpg', 2, 'passwordS16', 'sunshiliu@example.com', 1, '258369147', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('周十七', 'zhoushiqi123', 'https://example.com/avatar15.jpg', 1, 'passwordZ17', 'zhoushiqi@example.com', 0, '369147258', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('吴十八', 'wushiba456', 'https://example.com/avatar16.jpg', 2, 'passwordW18', 'wushiba@example.com', 1, '147258369', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('郑十九', 'zhengshijiu789', 'https://example.com/avatar17.jpg', 1, 'passwordZ19', 'zhengshijiu@example.com', 0, '369258147', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0),
                                                                                                                                          ('王二十', 'wangershi123', 'https://example.com/avatar18.jpg', 2, 'passwordW20', 'wangershi@example.com', 1, '258369147', '2024-03-05 12:00:00', '2024-03-05 12:00:00', 0);

alter table user add column tags varchar(1024) null comment '标签列表';

alter table user
    add profile varchar(255) null comment '个人简介' after gender;
