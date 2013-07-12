package com.chris.crawler.zhihu.dao.impl;

import com.chris.crawler.zhihu.dao.ZHAnswerDao;
import com.chris.crawler.zhihu.model.ZHAnswer;
import com.dajie.common.framework.ibatis.CacheDao;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHAnswerDaoImpl extends CacheDao implements ZHAnswerDao {
    private static final String STAT = "ZHAnswerDao.";

    public ZHAnswerDaoImpl() {
        super("dajie_crawler_zhihu", "crawler");
    }

    @Override
    public int insert(ZHAnswer answer) {
        return insert(STAT + "insert", answer);
    }
}
