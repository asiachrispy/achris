package com.chris.crawler.yjs.ft.service.impl;

import com.chris.crawler.yjs.ft.dao.FTJobDao;
import com.chris.crawler.yjs.ft.dao.impl.FTJobDaoImpl;
import com.chris.crawler.yjs.ft.model.FTJob;
import com.chris.crawler.yjs.ft.service.FTJobService;
import com.google.inject.Guice;

/**
 * User: zhong.huang
 * Date: 13-4-25
 * Time: 上午10:26
 */
public class FTJobServiceImpl implements FTJobService {
    private FTJobDao ptJobDao = Guice.createInjector().getInstance(FTJobDaoImpl.class);

    @Override
    public int insert(FTJob ftJob) {
        return ptJobDao.insert(ftJob);
    }
}
