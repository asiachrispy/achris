package com.chris.crawler.zhihu.service;

import com.chris.crawler.zhihu.model.ZHAnswer;
import com.chris.crawler.zhihu.service.impl.ZHAnswerServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHAnswerServiceImpl.class)
public interface ZHAnswerService {

    public int insert(ZHAnswer answer);

}
