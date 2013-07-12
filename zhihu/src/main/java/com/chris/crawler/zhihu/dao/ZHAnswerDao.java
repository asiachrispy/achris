package com.chris.crawler.zhihu.dao;

import com.chris.crawler.zhihu.dao.impl.ZHAnswerDaoImpl;
import com.chris.crawler.zhihu.model.ZHAnswer;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHAnswerDaoImpl.class)
public interface ZHAnswerDao {
    public int insert(ZHAnswer answer);
}
