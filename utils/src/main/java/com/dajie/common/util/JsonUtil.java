package com.dajie.common.util;

/**
 * User: jiangxu.qiu
 * Date: 13-3-26
 * Time: 下午1:34
 */
public class JsonUtil {

    private static final String JSON_RESULT_FORMAT1 = "{\"result\":%s}";
    private static final String JSON_RESULT_FORMAT2 = "{\"result\":%s, \"message\":\"%s\"}";
    private static final String JSON_RESULT_FORMAT3 = "{\"result\":\"%s\"}";
    private static final String JSON_RESULT_FORMAT4 = "{\"result\":\"%s\", \"message\":\"%s\"}";


    public static String jsonResult(boolean  result) {
        return String.format(JSON_RESULT_FORMAT1, result);
    }

    public static String jsonResult(boolean result, String message) {
        return String.format(JSON_RESULT_FORMAT2, result, message == null ? "" : message);
    }

    public static String jsonResult(int  result) {
        return String.format(JSON_RESULT_FORMAT1, result);
    }

    public static String jsonResult(int result, String message) {
        return String.format(JSON_RESULT_FORMAT2, result, message == null ? "" : message);
    }

    public static String jsonResult(String  result) {
        return String.format(JSON_RESULT_FORMAT3, result);
    }

    public static String jsonResult(String result, String message) {
        return String.format(JSON_RESULT_FORMAT4, result, message == null ? "" : message);
    }

}
