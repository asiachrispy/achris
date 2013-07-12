package com.chris.crawler.yjs.pt.service.impl;

import com.chris.crawler.yjs.pt.dao.PTJobDao;
import com.chris.crawler.yjs.pt.dao.impl.PTJobDaoImpl;
import com.chris.crawler.yjs.pt.model.PTJob;
import com.chris.crawler.yjs.pt.service.PTJobService;
import com.google.inject.Guice;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-25
 * Time: 上午10:26
 */
public class PTJobServiceImpl implements PTJobService {
    private PTJobDao ptJobDao = Guice.createInjector().getInstance(PTJobDaoImpl.class);

    @Override
    public int insert(PTJob ptJob) {
        return ptJobDao.insert(ptJob);
    }

    @Override
    public boolean exist(String md5Url) {
        return ptJobDao.exist(md5Url);
    }

    @Override
    public int deleteByMd5Url(String md5Url) {
        return ptJobDao.deleteByMd5Url(md5Url);
    }

    @Override
    public List<PTJob> getByDate(String date) {
        return ptJobDao.getByDate(date);
    }
}
