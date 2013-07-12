package com.chris.crawler.zhihu.dao;

import com.chris.crawler.zhihu.dao.impl.ZHLogSlaveDaoImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHLogSlaveDaoImpl.class)
public interface ZHLogSlaveDao {
    public int getEnd(int id);
    public int getStart(int id);
}
