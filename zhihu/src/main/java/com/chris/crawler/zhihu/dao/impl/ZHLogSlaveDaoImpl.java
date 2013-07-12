package com.chris.crawler.zhihu.dao.impl;

import com.dajie.common.framework.ibatis.CacheDao;
import com.chris.crawler.zhihu.dao.ZHLogSlaveDao;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHLogSlaveDaoImpl extends CacheDao implements ZHLogSlaveDao {
    private static final String STAT = "ZHLogDao.";

    public ZHLogSlaveDaoImpl() {
        super("dajie_crawler_zhihu", "crawler_read");
    }

    public int getEnd(int id) {
        Object obj = selectOne(STAT + "getEnd", id);
        if (obj != null) {
            return (Integer) obj;
        }
        return -1;
    }

    @Override
    public int getStart(int id) {
        Object obj = selectOne(STAT + "getStart", id);
               if (obj != null) {
                   return (Integer) obj;
               }
               return -1;
    }
}
