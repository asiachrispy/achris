package com.chris.crawler.haitou.dao.impl;

import com.chris.crawler.haitou.dao.HTxjhDaoSlave;
import com.dajie.common.framework.ibatis.CacheDao;
import com.google.inject.Inject;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */

public class HTxjhDaoSlaveImpl extends CacheDao implements HTxjhDaoSlave {

    private static final String STAT = "HTxjhDao.";

    @Inject
    public HTxjhDaoSlaveImpl(String componentName, String environment) {
        super("dajie_crawler_haitou", "crawler_read");
    }

    @Override
    public boolean exist(String urlMd5) {
        Integer size = (Integer) selectOne(STAT + "exist", urlMd5);
        if (size != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getCount(String urlMd5) {
        Object ucount = selectOne(STAT + "getCount", urlMd5);
        if (ucount != null) {
            return (Integer) ucount;
        }
        return -1;
    }
}
