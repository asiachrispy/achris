package com.chris.crawler.zhihu.dao;

import com.chris.crawler.zhihu.dao.impl.ZHQuestionSlaveDaoImpl;
import com.chris.crawler.zhihu.model.ZHQuestion;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHQuestionSlaveDaoImpl.class)
public interface ZHQuestionSlaveDao {

    public boolean exist(Integer qid);

    public ZHQuestion getById(int qid);

    public List<ZHQuestion> getPaging(int startId, int pageSize);
}
