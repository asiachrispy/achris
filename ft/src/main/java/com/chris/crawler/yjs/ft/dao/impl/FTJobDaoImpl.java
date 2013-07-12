package com.chris.crawler.yjs.ft.dao.impl;

import com.chris.crawler.yjs.ft.model.FTJob;
import com.dajie.common.framework.ibatis.CacheDao;
import com.chris.crawler.yjs.ft.dao.FTJobDao;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
public class FTJobDaoImpl extends CacheDao implements FTJobDao {

    private static final String namespace = "FTJobDao";

    public FTJobDaoImpl() {
        super("dajie_crawler", "search");
    }

    @Override
    public int insert(FTJob ftJob) {
        return insert(namespace + ".insertFTJob", ftJob);
    }


}
