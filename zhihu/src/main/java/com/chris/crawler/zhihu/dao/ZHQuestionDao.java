package com.chris.crawler.zhihu.dao;

import com.chris.crawler.zhihu.dao.impl.ZHQuestionDaoImpl;
import com.chris.crawler.zhihu.model.ZHQuestion;
import com.google.inject.ImplementedBy;

/**
 * User: zhong.huang
 * Date: 13-5-14
 */
@ImplementedBy(ZHQuestionDaoImpl.class)
public interface ZHQuestionDao {

    public int insert(ZHQuestion question);
}
