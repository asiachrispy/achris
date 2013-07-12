package com.chris.crawler.zhihu.service.impl;

import com.chris.crawler.zhihu.dao.ZHQuestionDao;
import com.chris.crawler.zhihu.dao.ZHQuestionSlaveDao;
import com.chris.crawler.zhihu.model.ZHQuestion;
import com.chris.crawler.zhihu.service.ZHQuestionService;
import com.google.inject.Inject;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHQuestionServiceImpl implements ZHQuestionService {
    @Inject
    private ZHQuestionDao questionDao;

    @Inject
    private ZHQuestionSlaveDao questionSlaveDao;

    @Override
    public int insert(ZHQuestion question) {
        try {
            return questionDao.insert(question);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean exist(Integer qid) {
        return questionSlaveDao.exist(qid);
    }

    @Override
    public ZHQuestion getById(int qid) {
        return questionSlaveDao.getById(qid);
    }

    @Override
    public List<ZHQuestion> getPaging(int startId, int pageSize) {
        return questionSlaveDao.getPaging(startId, pageSize);
    }
}
