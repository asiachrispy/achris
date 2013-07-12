package com.chris.crawler.yjs.ft.service.impl;

import com.chris.crawler.yjs.ft.dao.FTJobURLDao;
import com.chris.crawler.yjs.ft.dao.impl.FTJobURLDaoImpl;
import com.chris.crawler.yjs.ft.model.FTJobURL;
import com.chris.crawler.yjs.ft.service.FTJobURLService;
import com.google.inject.Guice;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
public class FTJobURLServiceImpl implements FTJobURLService {
     private FTJobURLDao ftJobURLDao = Guice.createInjector().getInstance(FTJobURLDaoImpl.class);
    @Override
    public int insert(FTJobURL ftJobURL) {
        return ftJobURLDao.insert(ftJobURL);
    }

    @Override
    public boolean exist(String md5Url) {
        return ftJobURLDao.exist(md5Url);
    }
}
