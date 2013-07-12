package com.chris.crawler.core.spider;

import com.chris.crawler.core.CrawlerConstant;
import com.chris.crawler.core.exception.SpiderException;
import com.dajie.common.util.StringUtil;

import java.io.IOException;

/**
 * Spider just handler html and return with tag.
 * User: zhong.huang
 * Date: 13-4-24
 * Time: 上午10:24
 */
public abstract class Spider {
    private String proxyKV;

    protected Spider() {
    }

    protected Spider(String proxyKV) {
        this.proxyKV = proxyKV;
    }

    /**
     * get url content.
     *
     * @param url
     * @param encoding html content-encoding
     * @return
     */
    abstract public String download(String url, String encoding, String cookies) throws IOException, SpiderException;

    /**
     * get url content.
     *
     * @param url
     * @param encoding html content-encoding
     * @return
     */
    abstract public String download(String url, String encoding) throws IOException, SpiderException;

    public String getProxyKV() {
        return proxyKV;
    }

    public Spider useProxy(boolean use) {
        if (!use) {
            System.setProperty("http.proxyHost", "");
            System.setProperty("http.proxyPort", "");
            return this;
        }

        if (StringUtil.isNotEmpty(proxyKV) && proxyKV.contains(":")) {
            String[] kv = proxyKV.split(":");
            System.setProperty("http.proxyHost", kv[0]);
            System.setProperty("http.proxyPort", kv[1]);
        } else { // for default proxy
            String[] kv = CrawlerConstant.DEFAULT_PROXY_IP.split(":");
            System.setProperty("http.proxyHost", kv[0]);
            System.setProperty("http.proxyPort", kv[1]);
        }
        return this;
    }

}


