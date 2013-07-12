package com.chris.crawler.haitou;

import com.dajie.common.config.AppConfigs;

/**
 * User: zhong.huang
 * Date: 13-5-21
 */
public class HTConstant {
    public static String CONFIG_KEY_CITY = "city";
    public static String CONFIG_KEY_COOKIE = "cookie";
    public static final String CONFIG_KEY_PROXY_IP = "proxy_ip";

    public static String getCookie() {
        return AppConfigs.getInstance().get(CONFIG_KEY_COOKIE);
    }

    public static String getProxy() {
        return AppConfigs.getInstance().get(CONFIG_KEY_PROXY_IP);
    }


}
