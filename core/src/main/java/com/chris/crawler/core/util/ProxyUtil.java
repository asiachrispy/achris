package com.chris.crawler.core.util;

import com.chris.crawler.core.CrawlerConstant;

import java.util.Random;

/**
 * User: zhong.huang
 * Date: 13-5-21
 */
public final class ProxyUtil {

    /**
     * return host:port String
     * @return
     */
    public static String getRandomProxy() {
        Random random = new Random();
        String proxy = CrawlerConstant.PROXY_IP_LIST[random.nextInt(CrawlerConstant.PROXY_IP_LIST.length)];
        return proxy;
    }
}
