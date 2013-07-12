package com.chris.crawler.zhihu.dao.impl;

import com.chris.crawler.zhihu.dao.ZHQuestionSlaveDao;
import com.chris.crawler.zhihu.model.ZHQuestion;
import com.dajie.common.framework.ibatis.CacheDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHQuestionSlaveDaoImpl extends CacheDao implements ZHQuestionSlaveDao {
    private static final String STAT = "ZHQuestionDao.";

    public ZHQuestionSlaveDaoImpl() {
        super("dajie_crawler_zhihu", "crawler_read");
    }

    @Override
    public boolean exist(Integer qid) {
        Integer size = (Integer) selectOne(STAT + "exist", qid);
        if (size != null && size == 1) {
            return true;
        }
        return false;
    }

    @Override
    public ZHQuestion getById(int qid) {
        return (ZHQuestion) selectOne(STAT + "getById", qid);
    }

    @Override
    public List<ZHQuestion> getPaging(int startId, int pageSize) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("startId", startId);
        map.put("pageSize", pageSize);
        return selectList(STAT + "getPaging", map);
    }

}
