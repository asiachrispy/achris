package com.chris.crawler.yjs.pt.dao.impl;

import com.chris.crawler.yjs.pt.dao.PTJobDao;
import com.chris.crawler.yjs.pt.model.PTJob;
import com.dajie.common.framework.ibatis.CacheDao;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */

public class PTJobDaoImpl extends CacheDao implements PTJobDao {
    private static final String namespace = "PTJobDao";

    public PTJobDaoImpl() {
        super("dajie_crawler", "search");
    }

    @Override
    public int insert(PTJob ptJob) {
        return insert(namespace + ".insertPTJob", ptJob);
    }

    @Override
    public boolean exist(String md5Url) {
        Integer size = (Integer) selectOne(namespace + ".exist", md5Url);
        if (size != null && size == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int deleteByMd5Url(String md5Url) {
        return delete(namespace + ".deleteByMd5Url", md5Url);
    }

    @Override
    public List<PTJob> getByDate(String date) {
        return (List<PTJob>)selectList(namespace + ".getByDate", date);
    }


}
