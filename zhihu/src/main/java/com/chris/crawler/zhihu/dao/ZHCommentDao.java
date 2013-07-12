package com.chris.crawler.zhihu.dao;

import com.chris.crawler.zhihu.dao.impl.ZHCommentDaoImpl;
import com.chris.crawler.zhihu.model.ZHComment;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHCommentDaoImpl.class)
public interface ZHCommentDao {
    public int insert(ZHComment comment);
}
