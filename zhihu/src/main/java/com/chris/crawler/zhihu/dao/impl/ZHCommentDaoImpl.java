package com.chris.crawler.zhihu.dao.impl;

import com.chris.crawler.zhihu.dao.ZHCommentDao;
import com.dajie.common.framework.ibatis.CacheDao;
import com.chris.crawler.zhihu.model.ZHComment;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHCommentDaoImpl extends CacheDao implements ZHCommentDao {
    private static final String STAT = "ZHCommentDao.";

    public ZHCommentDaoImpl() {
        super("dajie_crawler_zhihu", "crawler");
    }

    @Override
    public int insert(ZHComment comment) {
        return insert(STAT + "insert", comment);
    }
}
