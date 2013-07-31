package com.dajie.common.util.mobile.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:44
 */
public class AndroidUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        //Linux; U; Android 4.0.4; zh-cn; MOT-XT788 Build/IRPMCT_B_02.60.00RPD
        Pattern p = Pattern.compile("android \\d+(\\.\\d+|)(\\.\\d+|)");
        Matcher m = p.matcher(systemInfo);
        if (m.find()) {
            String[] info = m.group().split(" ");
            return info[1];
        } else {
            p = Pattern.compile("android/\\d+(\\.\\d+|)(\\.\\d+|)");
            m = p.matcher(systemInfo);
            if (m.find()) {
                String[] info = m.group().split("/");
                return info[1];
            }
        }
        return null;
    }
}
