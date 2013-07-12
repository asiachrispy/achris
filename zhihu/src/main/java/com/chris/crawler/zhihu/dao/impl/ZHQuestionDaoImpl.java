package com.chris.crawler.zhihu.dao.impl;

import com.chris.crawler.zhihu.model.ZHQuestion;
import com.dajie.common.framework.ibatis.CacheDao;
import com.chris.crawler.zhihu.dao.ZHQuestionDao;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHQuestionDaoImpl extends CacheDao implements ZHQuestionDao {
    private static final String STAT = "ZHQuestionDao.";

    public ZHQuestionDaoImpl() {
        super("dajie_crawler_zhihu", "crawler");
    }

    @Override
    public int insert(ZHQuestion question) {
        return insert(STAT + "insert", question);
    }

}
