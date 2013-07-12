package com.chris.crawler.zhihu.dao.impl;

import com.chris.crawler.zhihu.dao.ZHLogDao;
import com.chris.crawler.zhihu.model.ZHLog;
import com.dajie.common.framework.ibatis.CacheDao;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHLogDaoImpl extends CacheDao implements ZHLogDao {
    private static final String STAT = "ZHLogDao.";

    public ZHLogDaoImpl() {
        super("dajie_crawler_zhihu", "crawler");
    }

    @Override
    public int insert(ZHLog log) {
        return insert(STAT + "insert", log);
    }

    public int update(ZHLog log) {
        return update(STAT + "update", log);
    }

}
