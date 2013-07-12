package com.chris.crawler.yjs.pt.spider;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-24
 * Time: 上午10:24
 */
public abstract class Spider {

    abstract public String getHtmlContent(String url, String encoding, String proxyKV);

    abstract public List<String> getWebLinks(String htmlContent);
}
