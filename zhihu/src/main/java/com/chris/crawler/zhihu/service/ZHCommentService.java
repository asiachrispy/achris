package com.chris.crawler.zhihu.service;

import com.chris.crawler.zhihu.model.ZHComment;
import com.chris.crawler.zhihu.service.impl.ZHCommentServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHCommentServiceImpl.class)
public interface ZHCommentService {

    public int insert(ZHComment comment);

}
