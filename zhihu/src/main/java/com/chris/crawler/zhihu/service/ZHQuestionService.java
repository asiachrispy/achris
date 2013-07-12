package com.chris.crawler.zhihu.service;

import com.chris.crawler.zhihu.model.ZHQuestion;
import com.chris.crawler.zhihu.service.impl.ZHQuestionServiceImpl;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHQuestionServiceImpl.class)
public interface ZHQuestionService {

    public int insert(ZHQuestion question);

    public boolean exist(Integer qid);

    public ZHQuestion getById(int qid);

    public List<ZHQuestion> getPaging(int startId, int pageSize);
}
