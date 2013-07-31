package com.dajie.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: haluo
 * Date: 2010-3-9
 * Time: 20:24:55
 */
public class FeedUtil {
    private FeedUtil() {
    }

    private static Logger log = LoggerFactory.getLogger(FeedUtil.class);

    public static boolean addFeed(HashMap<String, String> feedData, String postUrl) {
        String url = "http://app.dajie.com/api/feed/create_feed.jsp";
        boolean result = false;
        String html;
        try {
            html = HttpClientUtil.postResponse(url, feedData, null);
            if (html.indexOf("\"errorid\":\"0\"") > -1) {
                result = true;
            } else {
                log.error("add feed error:" + html);
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("timed out") > -1) {
                log.error("add feed error,try....");
                try {
                    html = HttpClientUtil.postResponse(url, feedData, null);
                    if (html.indexOf("\"errorid\":\"0\"") > -1) {
                        result = true;
                    } else {
                        log.error("add feed error2:" + html);
                    }
                } catch (Exception e2) {
                    log.error("addFeed error2:", e);
                    result = false;
                }
            } else {
                String value = "";
                for (Map.Entry<String, String> entry : feedData.entrySet()) {
                    value += entry.getKey() + "=" + entry.getValue() + "\n";
                }
                log.error("addFeed error1:" + value, e);
                result = false;
            }
        }
        return result;
    }
}
