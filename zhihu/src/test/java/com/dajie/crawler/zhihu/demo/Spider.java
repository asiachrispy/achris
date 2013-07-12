package com.dajie.crawler.zhihu.demo;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-24
 * Time: 上午10:24
 */
public abstract class Spider {

    abstract public String getHtmlContent(String url, String encoding, String proxyKV) throws UnsupportedEncodingException;

    abstract public List<String> getLinks(String htmlContent,String key);
}
