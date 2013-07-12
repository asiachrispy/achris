package com.chris.crawler.zhihu.parser;

import org.jsoup.nodes.Document;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public interface Parser<T> {
    public void parser(Document doc,T t);
    public void parser(String html,T t);
}
