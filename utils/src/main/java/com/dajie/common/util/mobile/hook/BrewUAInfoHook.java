package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:50
 */
public class BrewUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //BREW/3.1.5.20; DeviceId: 40144; Lang: zhcn
        Pattern p = Pattern.compile("brew/\\d+(\\.\\d+|)(\\.\\d+|)(\\.\\d+|)");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split("/");
            return info[1];
        }
        return null;
    }
}
