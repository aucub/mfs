create table if not exists link_log
(
    id          bigint auto_increment
        primary key,
    user_id     varchar(32) null,
    create_time datetime    null,
    route       varchar(32) null,
    ip          varchar(64) null
);

create table if not exists role
(
    id          bigint auto_increment
        primary key,
    name        varchar(100)      null comment '名称',
    description varchar(500)      null comment '描述',
    deleted     tinyint default 0 null comment '启用状态：0->启用；1->禁用'
)
    comment '角色表' auto_increment = 13;

INSERT INTO mfs.role (id, name, description, deleted)
VALUES (1, 'userMan', '用户管理', 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (3, 'generateJwt', null, 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (4, 'searchOnline', '', 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (5, 'role', '角色管理', 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (8, 'connect', null, 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (9, 'push', null, 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (10, 'publish', null, 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (11, 'consume', null, 0);
INSERT INTO mfs.role (id, name, description, deleted)
VALUES (12, 'query', null, 0);

create table if not exists role_relation
(
    id      bigint auto_increment
        primary key,
    user_id varchar(32) null,
    role_id bigint      null
)
    comment '用户和角色关系表' auto_increment = 13;

INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (1, '0c59989d3970380ae168880686c4a070', 1);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (3, '0c59989d3970380ae168880686c4a070', 3);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (4, '0c59989d3970380ae168880686c4a070', 4);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (5, '0c59989d3970380ae168880686c4a070', 5);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (8, '0c59989d3970380ae168880686c4a070', 8);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (9, '0c59989d3970380ae168880686c4a070', 9);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (10, '0c59989d3970380ae168880686c4a070', 10);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (11, '0c59989d3970380ae168880686c4a070', 11);
INSERT INTO mfs.role_relation (id, user_id, role_id)
VALUES (12, '0c59989d3970380ae168880686c4a070', 12);

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

INSERT INTO mfs.user (id, username, password, nickname, creator, create_time, updater, last_update_time, login_time,
                      type, note, deleted)
VALUES ('0a30044edb19099f6e00e4593ed3fb42', 'test1', 'AWLT6P2Mx9qCMNSwPIcPWnVUvO4GlmZn+KI=', 'test1', null,
        '2023-04-25 10:49:50', null, '2023-04-25 10:49:50', null, 1, 'test1', 0);
INSERT INTO mfs.user (id, username, password, nickname, creator, create_time, updater, last_update_time, login_time,
                      type, note, deleted)
VALUES ('0c59989d3970380ae168880686c4a070', 'root', 'AWLT6P1xxJ3Dize/bcMKDr478IJoiRbPlw==', 'root', null,
        '2023-04-02 17:40:20', null, '2023-04-02 17:43:33', '2023-04-02 17:43:33', 0, null, 0);
INSERT INTO mfs.user (id, username, password, nickname, creator, create_time, updater, last_update_time, login_time,
                      type, note, deleted)
VALUES ('9513bf1221694ab1f1a7e2aa0e9fab41', 'username_5ci6f', 'AWLT6P1ZIW9AntqvjCsjwpg6OjzXY7RuH4dpjWH4NK1pdKA=',
        'nickname_gwh9w', null, '2023-04-25 11:25:03', null, '2023-04-25 11:25:03', null, 1, 'note_n3jpw', 0);

create table if not exists user_login_log
(
    id          bigint auto_increment
        primary key,
    user_id     varchar(32) null,
    create_time datetime null
)
    comment '登录记录' auto_increment = 18;
