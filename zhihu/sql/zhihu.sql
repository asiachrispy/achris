CREATE TABLE `tb_crawler_zhihu_question` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `uid` INT(11) NOT NULL COMMENT 'dajie uid',
   `qid` INT(11) NOT NULL COMMENT 'zhihu question id',
   `answer_count` INT(11) NOT NULL COMMENT 'zhihu answers count',
   `tags` VARCHAR(550) DEFAULT '' COMMENT 'zhihu tags',
   `content` VARCHAR(550) NOT NULL COMMENT 'zhihu question title',
   `detail` TEXT(500) DEFAULT '' COMMENT 'zhihu question detail/content',
   `pub_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00',
   `create_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (`id`),
   UNIQUE KEY `qid` (`qid`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'zhihu question';

 --
 CREATE TABLE `tb_crawler_zhihu_answer` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `uid` INT(11) NOT NULL COMMENT 'dajie uid',
   `qid` INT(11) NOT NULL COMMENT 'zhihu question id',
   `aid` INT(11) NOT NULL COMMENT 'zhihu answer id',
   `content` TEXT NOT NULL COMMENT 'zhihu answer content',
   `pub_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00',
   `create_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (`id`),
   UNIQUE KEY `qid_aid` (`qid`,`aid`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'zhihu answer';

  --
 CREATE TABLE `tb_crawler_zhihu_comment` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `uid` INT(11) NOT NULL COMMENT 'dajie uid',
   `qid` INT(11) NOT NULL COMMENT 'zhihu question id',
   `aid` INT(11) NOT NULL COMMENT 'zhihu answer id',
   `cid` INT(11) NOT NULL COMMENT 'zhihu comment id',
   `content` TEXT NOT NULL COMMENT 'zhihu comment content',
   `pub_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00',
   `create_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'zhihu comment';

--
  CREATE TABLE `tb_crawler_zhihu_log` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `end_id` INT(11) NOT NULL COMMENT 'log item end id',
   `start_id` INT(11) NOT NULL COMMENT 'log item start id',
   `update_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'zhihu log';

-- for faild qid
CREATE TABLE `tb_crawler_zhihu_failed_question` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `qid` INT(11) NOT NULL COMMENT 'q id',
   `update_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'zhihu question';