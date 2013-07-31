package com.dajie.common.util;

import com.dajie.common.util.constants.UserConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * User: haluo
 * Date: 13-1-9
 * Time: 下午3:04
 */
public final class LoginAuthParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthParser.class);

    private LoginAuthParser() {
    }

    public static int getLoginUid(HttpServletRequest request) {
        int uid = getUidFromV3LoginCookie(request);
        if (uid <= 0 && !hasV3LoginCookie(request)) {
            uid = getUidFromV2LoginCookie(request);
        }
        return uid;
    }

    private static int getUidFromV2LoginCookie(HttpServletRequest request) {
        Cookie cookie = getCookie(request, UserConstant.AUTH_COOKIE_KEY_V2);
        if (cookie == null || StringUtil.isEmpty(cookie.getValue())) {
            return 0;
        }
        String decodeValue = AesCryptUtil.decrypt(cookie.getValue(), UserConstant.AUTH_ENCODE_KEY_V2);
        if (StringUtil.isEmpty(decodeValue) || !StringUtil.isInteger(decodeValue)) {
            LOGGER.warn(String.format("======>>>>Get uid from V2 cookie failed.cookie value is %s", cookie.getValue()));
            return 0;
        }
        int uid = Integer.parseInt(decodeValue);
        LOGGER.debug("====>>>>Get login user {} from v2 cookie {}", uid, cookie.getValue());
        return uid;
    }

    private static int getUidFromV3LoginCookie(HttpServletRequest request) {
        Cookie c = getCookie(request, UserConstant.AUTH_COOKIE_KEY_V3);
        String decode;
        if (c == null
                || StringUtil.isEmpty(c.getValue())
                || StringUtil.isEmpty(decode = AesCryptUtil.decrypt(c.getValue(), UserConstant.AUTH_ENCODE_KEY_V3))) {
            return 0;
        }
        String[] info = decode.split("\\|");
        if (info.length != 2 || !StringUtil.isInteger(info[0])) {//info[0]:uid,info[1]:密码
            return 0;
        }
        int uid = Integer.parseInt(info[0]);
        LOGGER.warn(String.format("======>>>>Get uid from V3 login cookie failed.cookie value is %s", c.getValue()));
        return uid;
    }

    private static boolean hasV3LoginCookie(HttpServletRequest request) {
        Cookie c = getCookie(request, UserConstant.AUTH_COOKIE_KEY_V3);
        return c != null && !StringUtil.isEmpty(c.getValue());
    }

    private static Cookie getCookie(HttpServletRequest request, String key) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(key)) {
                return c;
            }
        }
        return null;
    }

}
