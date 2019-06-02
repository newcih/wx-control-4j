CREATE DATABASE IF NOT EXISTS wx_control;

DROP TABLE wx_control.wx_info;
CREATE TABLE wx_control.wx_info
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(36) COMMENT '公众号名称',
    wechat_id         VARCHAR(36) COMMENT '公众号微信号',
    appid             VARCHAR(36) COMMENT 'appid',
    appsecret         VARCHAR(36) COMMENT 'appsecret',
    status            CHAR(2) COMMENT '启用状态：0，禁用。1，启用' DEFAULT '1',
    access_token      VARCHAR(256) COMMENT 'access token',
    jsapi_ticket      VARCHAR(256) COMMENT 'js api ticket',
    api_ticket        VARCHAR(256) COMMENT 'api ticket',
    token_update_time DATETIME COMMENT 'access token最近更新时间',
    version           INT COMMENT '版本号'                DEFAULT 0,
    UNIQUE (appid)
) CHARSET = UTF8MB4
  ENGINE = INNODB;

ALTER TABLE wx_control.wx_info
    ADD INDEX (appid, status);
