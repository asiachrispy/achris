package com.chris.crawler.yjs.ft.util;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-22
 */
public final class Util {

    /**
     * @param htmlContents
     */
    public static String outTag(String htmlContents) {
        return htmlContents.replaceAll("<.*?>", "");
    }

    /**
     * @param htmlContents
     */
    public static String outByTag(String htmlContents, String tag) {
        String ptag = "<#.*?>".replace("#", tag);
        htmlContents = htmlContents.replaceAll(ptag, "");
        ptag = "</#.*?>".replace("#", tag);
        return htmlContents.replaceAll(ptag, "");
    }

    /**
     * 找出标签
     *
     * @param content
     * @param startTag
     * @param endTag
     * @return
     */
    public static List<String> getTags(String content, String startTag, String endTag) {
        List<String> list = new ArrayList<String>();
        String tag = "";
        while (content.contains(startTag) && content.contains(endTag)) {//
            int start = content.indexOf(startTag);
            int end = content.indexOf(endTag) + endTag.length();
            tag = content.substring(start, end);
            list.add(tag);
            content = content.substring(end);
        }
        return list;
    }


}
