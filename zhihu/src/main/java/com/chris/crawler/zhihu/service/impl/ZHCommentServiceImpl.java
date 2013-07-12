package com.chris.crawler.zhihu.service.impl;

import com.chris.crawler.zhihu.dao.ZHCommentDao;
import com.chris.crawler.zhihu.model.ZHComment;
import com.chris.crawler.zhihu.service.ZHCommentService;
import com.google.inject.Inject;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
public class ZHCommentServiceImpl implements ZHCommentService {
    @Inject
    private ZHCommentDao commentDao;

    @Override
    public int insert(ZHComment comment) {
        try {
            return commentDao.insert(comment);
        } catch (Exception e) {
            return 0;
        }
    }
}
