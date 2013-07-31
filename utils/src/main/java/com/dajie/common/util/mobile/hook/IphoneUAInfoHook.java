package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:45
 */
public class IphoneUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //iPhone; CPU iPhone OS 5_0_1 like Mac OS X
        Pattern p = Pattern.compile("iphone os \\d+(_\\d+|)(_\\d+|)");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split(" ");
            return info[2];
        }
        return null;
    }
}
