package com.chris.crawler.yjs.pt.service;

import com.chris.crawler.yjs.pt.model.PTJob;
import com.chris.crawler.yjs.pt.service.impl.PTJobServiceImpl;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
@ImplementedBy(PTJobServiceImpl.class)
public interface PTJobService {

    public int insert(PTJob ptJob);
    public boolean exist(String md5Url);
    public int deleteByMd5Url(String md5Url);

    /**
     * get by date and group by citycn
     * @param date format('YYYY-mm-DD')
     * @return List<PTJob> [url,citycn,title]
     */
    public List<PTJob> getByDate(String date);
}
