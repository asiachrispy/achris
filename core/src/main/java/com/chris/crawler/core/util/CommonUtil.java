package com.chris.crawler.core.util;

import com.dajie.common.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: zhong.huang
 * Date: 13-5-21
 */
public final class CommonUtil {
    /**
     * @param htmlContents
     */
    public static String outTag(String htmlContents) {
        return htmlContents.replaceAll("<[^<>]*?>", "");
    }

    public static String getYesterday(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    public static String getNowDate(String format) {
        return DateUtil.formatDate(new Date(), format);
    }

    public static List<String> getTagsByClass(String tag, String html, String regexp) {
        List<String> list = new LinkedList<String>();
        String regex = "<= [^>]*class=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</=>";
        regex = regex.replace("=", tag);
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    public static List<String> getTagsByAttribute(String tag, String attribute, String html, String regexp) {
        List<String> list = new LinkedList<String>();
        String regex = "<tag [^>]*attribute=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]>(.*?)</tag>";
        regex = regex.replace("tag", tag);
        regex = regex.replace("attribute", attribute);
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }
}
