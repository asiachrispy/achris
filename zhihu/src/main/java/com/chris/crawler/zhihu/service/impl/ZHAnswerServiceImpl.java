package com.chris.crawler.zhihu.service.impl;

import com.chris.crawler.zhihu.service.ZHAnswerService;
import com.chris.crawler.zhihu.dao.ZHAnswerDao;
import com.chris.crawler.zhihu.model.ZHAnswer;
import com.google.inject.Inject;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHAnswerServiceImpl implements ZHAnswerService {

    @Inject
    private ZHAnswerDao answerDao;

    @Override
    public int insert(ZHAnswer answer) {
        try {
            return answerDao.insert(answer);
        } catch (Exception e) {
            return 0;
        }
    }

}
