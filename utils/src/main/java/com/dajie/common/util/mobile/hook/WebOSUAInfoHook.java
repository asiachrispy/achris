package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:52
 */
public class WebOSUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //webOS/1.4.5; U; zh-CN) AppleWebKit/532.2
        Pattern p = Pattern.compile("webos/\\d+(\\.\\d+|)(\\.\\d+|)(\\.\\d+|)");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split("/");
            return info[1];
        }
        return null;
    }
}
