

CREATE TABLE `tb_yjs_app_url` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `md5sum` VARCHAR(32) NOT NULL COMMENT 'URL MD5SUM',
   `url` VARCHAR(500) NOT NULL COMMENT 'app url',
   `status` SMALLINT(2) NOT NULL DEFAULT '0' COMMENT '是否解析成功（0-未解析，1-解析失败）',
   `create_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   `recruit_type` smallint(2) NOT NULL COMMENT '招聘类型--1(全职)2(实习)',
   PRIMARY KEY (`id`),
   UNIQUE KEY `md5sum` (`md5sum`),
   KEY `idx_status`(`status`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;

 CREATE TABLE `tb_yjs_project` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '抓站项目ID',
   `name` VARCHAR(200) NOT NULL COMMENT '项目名称--抓站必填',
   `yjs_url` VARCHAR(500) NOT NULL COMMENT '应届生地址--抓站必填',
   `apply_url` VARCHAR(500) NOT NULL COMMENT '网申地址--抓站必填',
   `refer_url` VARCHAR(500) NOT NULL COMMENT '来源地址--抓站必填',
   `recruit_type` VARCHAR(50) NOT NULL COMMENT '招聘类型--1(全职)2(实习)3(其它)--抓站必填',
   `content` LONGTEXT NOT NULL COMMENT '内容--抓站必填',
   `major` VARCHAR(500) NOT NULL COMMENT '专业--抓站必填',
   `yjs_major` VARCHAR(500) NOT NULL COMMENT '专业--项目原始专业数据',
   `degree` VARCHAR(200) NOT NULL COMMENT '学历--抓站必填',
   `school` VARCHAR(500) DEFAULT NULL COMMENT '学校',
   `place` VARCHAR(100) NOT NULL COMMENT '地点--抓站必填',
   `industry` VARCHAR(100) DEFAULT NULL COMMENT '公司行业',
   `graduate_year` VARCHAR(100) DEFAULT NULL COMMENT '毕业年份',
   `audit_status` SMALLINT(11) NOT NULL DEFAULT '0' COMMENT '审核状态:0 待审核 1 通过 -1 不通过 ',
   `auditor` VARCHAR(200) DEFAULT NULL COMMENT '审核人',
   `schedule_begin` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '日程开始时间--发布时间--抓站必填',
   `schedule_end` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '日程结束时间',
   `audit_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核时间',
   `update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   `create_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   PRIMARY KEY (`id`),
   KEY `idx_audit_status` (`audit_status`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;