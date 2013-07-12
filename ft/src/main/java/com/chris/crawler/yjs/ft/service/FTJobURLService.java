package com.chris.crawler.yjs.ft.service;

import com.chris.crawler.yjs.ft.model.FTJobURL;
import com.chris.crawler.yjs.ft.service.impl.FTJobURLServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
@ImplementedBy(FTJobURLServiceImpl.class)
public interface FTJobURLService {
     public int insert(FTJobURL ftJobURL);
    public boolean exist(String md5Url);
}
