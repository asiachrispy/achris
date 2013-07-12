package com.chris.crawler.zhihu.util;

import com.chris.crawler.zhihu.ZHConstant;
import com.dajie.common.util.DateUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: zhong.huang
 * Date: 13-4-22
 */
public class Util {

    public static String getYesterday(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    public static String getNowDate(String format) {
        return DateUtil.formatDate(new Date(), format);
    }

    /**
     * @param htmlContents
     */
    public static String outTag(String htmlContents) {
        return htmlContents.replaceAll("<[^<>]*?>", "");
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

    public static List<String> getTagsById(String tag, String html, String regexp) {
        List<String> list = new LinkedList<String>();
        String regex = "<= [^>]*id=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</=>";
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
        String regex = "<tag [^>]*attribute=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</tag>";
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

    public static String getRandomProxy() {
        Random random = new Random();
        String proxy = ZHConstant.PROXY_IP[random.nextInt(ZHConstant.PROXY_IP.length)];
        return proxy;
    }

    @Test
    public void testB() {
        String v = "a<br/>b";
        System.out.println(outTag(v));
    }

}
