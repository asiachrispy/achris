package com.chris.crawler.haitou.dao.impl;

import com.chris.crawler.haitou.dao.HTxjhDao;
import com.chris.crawler.haitou.model.HTxjh;
import com.dajie.common.framework.ibatis.CacheDao;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */

public class HTxjhDaoImpl extends CacheDao implements HTxjhDao {
    private static final String STAT = "HTxjhDao.";

    @Inject
    public HTxjhDaoImpl(String componentName, String environment) {
        super("dajie_crawler_haitou", "crawler");
    }

    @Override
    public int insert(HTxjh ht) {
        return insert(STAT + "insert", ht);
    }

    @Override
    public int updateCount(String urlMd5, int count) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("urlMd5", urlMd5);
        map.put("updateCount", count + "");
        return update(STAT + "updateCount", map);
    }
}
