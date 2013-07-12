package com.chris.crawler.zhihu.dao;

import com.chris.crawler.zhihu.dao.impl.ZHLogDaoImpl;
import com.chris.crawler.zhihu.model.ZHLog;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHLogDaoImpl.class)
public interface ZHLogDao {
    public int insert(ZHLog log);
    public int update(ZHLog log);
}
