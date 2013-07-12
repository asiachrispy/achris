package com.chris.crawler.zhihu.service.impl;

import com.chris.crawler.zhihu.dao.ZHLogDao;
import com.chris.crawler.zhihu.dao.ZHLogSlaveDao;
import com.chris.crawler.zhihu.model.ZHLog;
import com.chris.crawler.zhihu.service.ZHLogService;
import com.google.inject.Inject;

/**
 * User: zhong.huang
 * Date: 13-5-15
 * Time: 下午1:55
 */
public class ZHLogServiceImpl implements ZHLogService {
    @Inject
    private ZHLogDao logDao;//= Guice.createInjector().getInstance(ZHLogDaoImpl.class);

    @Inject
    private ZHLogSlaveDao logSlaveDao;//= Guice.createInjector().getInstance(ZHLogDaoImpl.class);


    @Override
    public int insert(ZHLog log) {
        return logDao.insert(log);
    }

    @Override
    public int update(ZHLog log) {
        return logDao.update(log);
    }

    @Override
    public int getEnd(int id) {
        return logSlaveDao.getEnd(id);
    }

    @Override
    public int getStart(int id) {
        return logSlaveDao.getStart(id);
    }

    public static void main(String[] args) {
        ZHLogServiceImpl dao = new ZHLogServiceImpl();
        ZHLog zhLog = new ZHLog();
        zhLog.setEndId(11);
        zhLog.setStartId(1);
        dao.update(zhLog);
    }
}
