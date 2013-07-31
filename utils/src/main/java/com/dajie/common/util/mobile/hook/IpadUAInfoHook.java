package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:50
 */
public class IpadUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //iPad; U; CPU OS 3_2 like Mac OS X; en-us
        Pattern p = Pattern.compile("cpu os \\d+(_\\d+|)(_\\d+|)");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split(" ");
            return info[2];
        }
        return null;
    }
}
