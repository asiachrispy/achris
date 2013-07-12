package com.chris.crawler.yjs.ft.service;

import com.chris.crawler.yjs.ft.model.FTJob;
import com.chris.crawler.yjs.ft.service.impl.FTJobServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
@ImplementedBy(FTJobServiceImpl.class)
public interface FTJobService {

    public int insert(FTJob ptJob);
}
