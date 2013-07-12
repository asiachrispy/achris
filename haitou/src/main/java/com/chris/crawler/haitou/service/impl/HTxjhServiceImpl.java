package com.chris.crawler.haitou.service.impl;

import com.chris.crawler.haitou.dao.HTxjhDao;
import com.chris.crawler.haitou.dao.HTxjhDaoSlave;
import com.chris.crawler.haitou.dao.impl.HTxjhDaoSlaveImpl;
import com.chris.crawler.haitou.model.HTxjh;
import com.chris.crawler.haitou.service.HTxjhService;
import com.chris.crawler.haitou.dao.impl.HTxjhDaoImpl;
import com.google.inject.Guice;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */
public class HTxjhServiceImpl implements HTxjhService {

    private HTxjhDao master = Guice.createInjector().getInstance(HTxjhDaoImpl.class);
    private HTxjhDaoSlave slave = Guice.createInjector().getInstance(HTxjhDaoSlaveImpl.class);

    public HTxjhServiceImpl() {
    }

    @Override
    public int insert(HTxjh ht) {
        return master.insert(ht);
    }

    @Override
    public boolean exist(String urlMd5) {
        return slave.exist(urlMd5);
    }

    @Override
    public boolean isNew(String urlMd5, int count) {
        int size = slave.getCount(urlMd5);
        if (size == count) {
            return false;
        }
        return true;
    }

    @Override
    public int getUpdateCount(String urlMd5) {
        return slave.getCount(urlMd5);
    }

    @Override
    public int updateCount(String urlMd5, int count) {
        return master.updateCount(urlMd5, count);
    }
}
