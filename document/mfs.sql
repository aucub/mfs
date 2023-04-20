create table if not exists role
(
    id          bigint auto_increment
        primary key,
    name        varchar(100)      null comment '名称',
    description varchar(500)      null comment '描述',
    deleted     tinyint default 0 null comment '启用状态：0->启用；1->禁用'
)
    comment '角色表';

create table if not exists role_relation
(
    id      bigint auto_increment
        primary key,
    user_id varchar(32) null,
    role_id bigint      null
)
    comment '用户和角色关系表';

create table if not exists user
(
    id               varchar(32)       not null
        primary key,
    username         varchar(64)       null comment '用户名',
    password         varchar(255)      null,
    nickname         varchar(64)       null,
    creator          varchar(32)       null comment '创建者',
    create_time      datetime          null comment '注册时间',
    updater          varchar(32)       null comment '更新者',
    last_update_time datetime          null,
    login_time       datetime          null comment '最后登录时间',
    type             tinyint default 1 null comment '用户类型:0->系统用户',
    note             varchar(255)      null comment '备注信息',
    deleted          tinyint default 0 null,
    constraint id
        unique (id),
    constraint username
        unique (username)
);

create table if not exists user_login_log
(
    id          bigint auto_increment
        primary key,
    user_id     varchar(32) null,
    create_time datetime    null,
    ip          varchar(64) null
)
    comment '登录记录';