
CREATE TABLE `tb_yjs_pt_crawler` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `md5_url` VARCHAR(32) NOT NULL COMMENT 'URL MD5SUM',
   `url` VARCHAR(500) NOT NULL COMMENT 'pt url',
   `title` VARCHAR(500) NOT NULL COMMENT 'pt title',
   `citycn` VARCHAR(50) NOT NULL COMMENT 'pt chinese city',
   `cityen` VARCHAR(50) NOT NULL COMMENT 'pt english city',
   `create_date` TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (`id`),
   UNIQUE KEY `md5_url` (`md5_url`),
   key `idx_createdate_cityen` (`create_date`,`cityen`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'YJS 实习职位抓取';