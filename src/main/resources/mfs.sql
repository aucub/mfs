create table permission
(
    id         bigint auto_increment
        primary key,
    pid        bigint       null comment '父级权限id',
    name       varchar(100) null comment '名称',
    value      varchar(200) null comment '权限值',
    status     int          null comment '启用状态；0->禁用；1->启用',
    createTime datetime     null comment '创建时间'
)
    comment '权限表';

create table role
(
    id          bigint auto_increment
        primary key,
    name        varchar(100)  null comment '名称',
    description varchar(500)  null comment '描述',
    count       int           null comment '用户数量',
    createTime  datetime      null comment '创建时间',
    status      int default 1 null comment '启用状态：0->禁用；1->启用'
)
    comment '角色表';

create table role_permission_relation
(
    id            bigint not null
        primary key,
    role_id       bigint null,
    permission_id bigint null
)
    comment '用户角色和权限关系表';

create table role_relation
(
    id      bigint auto_increment
        primary key,
    user_id varchar(32) null,
    role_id bigint      null
)
    comment '用户和角色关系表';

create table user
(
    username       varchar(64)  null comment '用户名',
    password       varchar(255) null,
    nickname       varchar(64)  null,
    status         tinyint      null comment '启用状态0->禁用；1->启用',
    createTime     datetime     null comment '注册时间',
    id             varchar(32)  not null
        primary key,
    deleted        tinyint      null,
    lastUpdateTime datetime     null,
    loginTime      datetime     null comment '最后登录时间',
    note           varchar(255) null comment '备注信息',
    constraint id
        unique (id),
    constraint username
        unique (username)
);

create table user_login_log
(
    id         bigint auto_increment
        primary key,
    user_id    varchar(32) null,
    createTime datetime    null,
    ip         varchar(64) null
)
    comment '登录记录';

create table user_permission_relation
(
    id            bigint auto_increment
        primary key,
    user_id       varchar(32) null,
    permission_id bigint      null,
    type          int         null
)
    comment '用户和权限关系表';