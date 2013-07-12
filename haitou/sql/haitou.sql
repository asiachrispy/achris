-- haitou xjh table
create table DB_CRAWLER.`tb_crawler_haitou_xjh`(
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `url` VARCHAR(100) NOT NULL COMMENT '海投的抓站地址',
   `url_md5` VARCHAR(32) NOT NULL COMMENT 'URL MD5SUM',
   `title` VARCHAR(200) NOT NULL COMMENT '宣讲会标题-公司标题',
   `city` INT(11) NOT NULL default 0 COMMENT '宣讲会所在城市',
   `school` INT(11) NOT NULL default 0 COMMENT '发布宣讲会的学校',
   `address` VARCHAR(50) NOT NULL COMMENT '宣讲会举办地点',
   `content` MEDIUMTEXT NOT NULL default '' COMMENT '宣讲会内容',

   `update_count` SMALLINT(2) NOT NULL DEFAULT 0 COMMENT '宣讲会更新次数，默认为0',
   `schedule_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '宣讲会举办时间',
   `publish_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '宣讲会发布时间',
   `create_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '',
   `update_date` TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '',

   PRIMARY KEY (`id`),
   UNIQUE KEY `url_md5` (`url_md5`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '海投网宣讲会';