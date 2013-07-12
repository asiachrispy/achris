package com.chris.crawler.haitou.dao;

import com.chris.crawler.haitou.dao.impl.HTxjhDaoSlaveImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */
@ImplementedBy(HTxjhDaoSlaveImpl.class)
public interface HTxjhDaoSlave {
    public int getCount(String urlMd5);

    public boolean exist(String urlMd5);
}
