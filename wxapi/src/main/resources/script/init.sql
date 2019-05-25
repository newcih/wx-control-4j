CREATE DATABASE wx_control;

CREATE TABLE wx_info
(
    id        int auto_increment primary key,
    name      varchar(36) comment '公众号名称',
    wechat_id varchar(36) comment '公众号微信号',
    appid     varchar(36) comment 'appid',
    appsecret varchar(36) comment 'appsecret',
    status    char(2) comment '启用状态：0，禁用。1，启用'
) CHARSET = UTF8MB4
  ENGINE = INNODB