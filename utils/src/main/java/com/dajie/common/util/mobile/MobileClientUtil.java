package com.dajie.common.util.mobile;

import com.dajie.common.util.mobile.hook.UABaseInfoHook;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dajie.common.util.StringUtil.isNotEmpty;

/**
 * User: huihui.wang
 * Date: 13-1-30
 * Time: 下午6:50
 */
public class MobileClientUtil {

    private static Pattern p = Pattern.compile("\\(.+;.+\\)");

    public static UAInfo getPhoneOS(HttpServletRequest request) {
        return null == request ? null : getPhoneOS(request.getHeader("user-agent"));
    }

    public static UAInfo getPhoneOS(String requestInfo) {
        if (null == requestInfo) {
            return null;
        }
        Matcher m = p.matcher(requestInfo);
        String systemInfo = requestInfo;
        if (m.find()) {
            systemInfo = m.group().replace("(", "").replace(")", "").toLowerCase();
        }
        if (com.dajie.common.util.StringUtil.isEmpty(systemInfo)) {
            return null;
        }
        for (MobileOSEnum osEnum : MobileOSEnum.values()) {
            if (systemInfo.contains(osEnum.getShortName())) {
                UABaseInfoHook uaBaseInfoHook = osEnum.getHookInstance();
                String osVersion = uaBaseInfoHook.getVersion(systemInfo);
                if (isNotEmpty(osVersion)) {
                    UAInfo uaInfo = new UAInfo(osEnum, osVersion, requestInfo);
                    uaInfo.setMobileOs(true);
                    return uaInfo;
                }
            }
        }
        return null;
    }

}
