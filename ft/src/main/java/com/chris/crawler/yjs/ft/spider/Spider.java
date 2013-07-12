package com.chris.crawler.yjs.ft.spider;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-24
 * Time: 上午10:24
 */
public abstract class Spider {

    abstract public String getHtmlContent(String url, String encoding, String proxyKV);

    abstract public List<String> getLinks(String htmlContent, String prefix);

    abstract public List<String> getBasicInfo(String htmlContent);

    abstract public List<String> getMajor(String htmlContent);

    abstract public String getContent(String htmlContent);

    abstract public String getTitle(String htmlContent);

    abstract public String getApply(String htmlContent);

}

