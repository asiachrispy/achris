package com.chris.crawler.yjs.pt;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public final class PTConstant {
    public static final String YJS_HOME = "http://www.yingjiesheng.com/";
    public static final String OTHER_EN = "other";
    public static final String OTHER_CN = "其它";

    // for filters
    public static final String FILTER_STR_PT = "兼职";
    public static final String FILTER_STR_SOME = "某";

    public static final Map<String, String> CITIES_DICT = new HashMap<String, String>();

    static {
        CITIES_DICT.put("北京", "beijing");
        CITIES_DICT.put("上海", "shanghai");
        CITIES_DICT.put("广州", "guangzhou");
        CITIES_DICT.put("深圳", "shenzhen");
        CITIES_DICT.put("南京", "nanjing");
        CITIES_DICT.put("武汉", "wuhan");
        CITIES_DICT.put("成都", "chengdu");
        CITIES_DICT.put("天津", "tianjin");
        CITIES_DICT.put("其它", "other");
    }

    public static final String CITIES = "cities";
    // 报表文件路径，LINUX下需要以/结束，wind下需要\\结束
    public static final String REPORT_PATH = "report_path";
    //报表接受人邮箱
    public static final String REPORT_RECEIVER = "report_receiver";

    public static final String CONFIG_KEY_PROXY_IP ="proxy_ip";

    public static final String DEFAULT_PROXY_IP = "210.101.131.231:8080";
    public static final String[] PROXY_IP = {
        "218.241.153.43:8080",
        "122.72.12.52:8118", "124.81.113.183:8080", "211.154.83.38:80",
        "219.159.105.180:8080", "173.213.108.113:8080", "202.98.123.126:8080"};
}
