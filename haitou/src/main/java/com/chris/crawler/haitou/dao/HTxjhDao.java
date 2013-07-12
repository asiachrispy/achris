package com.chris.crawler.haitou.dao;

import com.chris.crawler.haitou.model.HTxjh;
import com.chris.crawler.haitou.dao.impl.HTxjhDaoImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */
@ImplementedBy(HTxjhDaoImpl.class)
public interface HTxjhDao {

    public int insert(HTxjh ht);

    public int updateCount(String urlMd5, int count);
}
