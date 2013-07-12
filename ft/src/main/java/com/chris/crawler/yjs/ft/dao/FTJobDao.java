package com.chris.crawler.yjs.ft.dao;

import com.chris.crawler.yjs.ft.dao.impl.FTJobDaoImpl;
import com.chris.crawler.yjs.ft.model.FTJob;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
@ImplementedBy(FTJobDaoImpl.class)
public interface FTJobDao {
    public int insert(FTJob ftJob);
}