package com.chris.crawler.yjs.ft.dao;

import com.chris.crawler.yjs.ft.dao.impl.FTJobURLDaoImpl;
import com.chris.crawler.yjs.ft.model.FTJobURL;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
@ImplementedBy(FTJobURLDaoImpl.class)
public interface FTJobURLDao {
    public int insert(FTJobURL ftJobURL);
    public boolean exist(String md5Url);
}
