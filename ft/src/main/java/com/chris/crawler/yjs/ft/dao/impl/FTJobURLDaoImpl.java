package com.chris.crawler.yjs.ft.dao.impl;

import com.chris.crawler.yjs.ft.dao.FTJobURLDao;
import com.dajie.common.framework.ibatis.CacheDao;
import com.chris.crawler.yjs.ft.model.FTJobURL;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
public class FTJobURLDaoImpl extends CacheDao implements FTJobURLDao {
    private static final String namespace = "FTJobURLDao";

    public FTJobURLDaoImpl() {
        super("dajie_crawler", "search");
    }

    public int insert(FTJobURL ftJob) {
        return insert(namespace + ".insertFTJobURL", ftJob);
    }

    public boolean exist(String md5Url) {
        Integer size = (Integer) selectOne(namespace + ".exist", md5Url);
        if (size != null && size == 1) {
            return true;
        }
        return false;
    }
}
