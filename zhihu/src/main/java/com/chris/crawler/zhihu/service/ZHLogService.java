package com.chris.crawler.zhihu.service;

import com.chris.crawler.zhihu.model.ZHLog;
import com.chris.crawler.zhihu.service.impl.ZHLogServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHLogServiceImpl.class)
public interface ZHLogService {
    public int insert(ZHLog log);
    public int update(ZHLog log);
    public int getEnd(int id);
    public int getStart(int id);
}
