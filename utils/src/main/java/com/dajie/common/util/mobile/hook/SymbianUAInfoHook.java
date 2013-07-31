package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:49
 */
public class SymbianUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //NokiaC5-05;SymbianOS/9.1 Series60/3.0
        Pattern p = Pattern.compile("symbianos/\\d+(\\.\\d+|);? \\w+/\\d+(\\.\\d+|)");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split(" ");
            return info[1];
        }
        return null;
    }
}
