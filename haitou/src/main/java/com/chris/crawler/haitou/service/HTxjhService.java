package com.chris.crawler.haitou.service;

import com.chris.crawler.haitou.model.HTxjh;
import com.chris.crawler.haitou.service.impl.HTxjhServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */
@ImplementedBy(HTxjhServiceImpl.class)
public interface HTxjhService {

    public int insert(HTxjh ht);

    public boolean exist(String urlMd5);

    /**
     * 如果 count 和 db中的count相同，则表示是已经存在的数据，且没有更新
     * 如果 count 和 db中的count不同，则表示有更新，
     *
     * @param urlMd5
     * @param count
     * @return
     */
    public boolean isNew(String urlMd5, int count);

    public int getUpdateCount(String urlMd5);

    public int updateCount(String urlMd5, int count);

}
