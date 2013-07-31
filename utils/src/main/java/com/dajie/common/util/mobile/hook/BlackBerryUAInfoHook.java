package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:51
 */
public class BlackBerryUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //BlackBerry; U; BlackBerry 9810; en-US
        Pattern p = Pattern.compile("blackberry \\d+");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split(" ");
            return info[1];
        }
        return null;
    }
}
