package com.dajie.crawler.zhihu.demo;

import com.chris.crawler.zhihu.util.Util;
import com.dajie.common.util.DateUtil;
import com.dajie.common.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class Parser {

    private String html;

    public Parser(String html) {
        this.html = html;
    }

    /**
     * parser <a href="/question/123123">如何删除百度知道中别人发布的涉及自己的内容？</a>
     *
     * @param links
     * @return
     */
    public List<Integer> parserQids(List<String> links) {
        List<Integer> list = new LinkedList<Integer>();
        Integer qid = -1;
        for (String tag : links) {
            qid = Integer.valueOf(tag.split("/")[2].split("\"")[0]);
            list.add(qid);
        }
        return list;
    }

    public List<String> parserAnswers(List<String> answers) {
        List<String> list = new LinkedList<String>();
        for (String answer : answers) {
            list.add(answer);
        }
        return list;
    }

    /**
     * <div class="zm-editable-content clearfix">声音是否集中 有穿透力 柔美 敏感度 爆发力。。。还有你是否喜欢</div>
     *
     * @param regexp
     * @return
     */
    public List<String> getAnswers(String regexp) {
        return Util.getTagsByClass("div", html, regexp);
    }

    /**
     * parser <time datetime="2013-05-10 16:44:40">2013-05-10 16:44:40</time>
     *
     * @param times
     * @return
     */
    public List<Date> parserTimes(List<String> times) {
        List<Date> list = new LinkedList<Date>();
        Date createDate = new Date();
        for (String tag : times) {
            createDate = DateUtil.parseDate(tag.split("\"")[1], "yyyy-MM-dd HH:mm:SS");
            list.add(createDate);
        }
        return list;
    }

    public String parserTitle(String title) {
        return Util.outTag(getTitle("zm-item-title*"));
    }

    /**
     * <div id="zh-question-title" class="zm-editable-status-normal">
     * <h2 class="zm-item-title zm-editable-content">《特别关注》杂志是怎么火起来的？<a name="edit" class="zu-edit-button" href="javascript:;">
     * <i class="zu-edit-button-icon"></i>修改</a></h2>
     * </div>
     *
     * @return
     */
    public String getTitle(String regexp) {
        List<String> list = new ArrayList<String>();
        String regex = "<h2 [^>]*class=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</h2>";
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }
        if (list.size() > 0) {
            return list.get(0);
        }
        return "";
    }

    /**
     * <div class="zm-editable-content">比如说史特拉第瓦利，阿瑪悌，瓜納里，瓜達尼尼~<br>为什么说他们是很好的琴呢~？
     * <a name="edit" class="zu-edit-button" href="javascript:;">
     * <i class="zu-edit-button-icon"></i>修改</a>
     * </div>
     *
     * @param regexp
     * @return
     */
    public String getDetail(String regexp) {
        List<String> list = new ArrayList<String>();
        String regex = "<div [^>]*class=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</div>";
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }

        if (list.size() > 0) {
            return list.get(0);
        }
        return "";
    }

    public String parserDetail(String content) {
        return Util.outTag(content);
    }

    /**
     * <div class="zm-tag-editor-labels zg-clear">
     * <a href="/topic/19662168" class="zm-item-tag" data-tip="t$b$19662168">
     * X 是怎么火起来的
     * </a>
     * <a href="/topic/19840098" class="zm-item-tag" data-tip="t$b$19840098">
     * 特别关注（杂志）
     * </a>
     * <a name="edit" class="zu-edit-button" href="javascript:;"><i class="zu-edit-button-icon"></i>修改</a>
     * </div>
     *
     * @return
     */
    public String getTag(String regexp) {
        List<String> list = new ArrayList<String>();
        String regex = "<div [^>]*class=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</div>";
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }

        if (list.size() > 0) {
            return list.get(0);
        }
        return "";
    }

    public List<String> parserTags(String tagDiv) {
        if (StringUtil.isEmpty(tagDiv)) {
            return Collections.emptyList();
        }

        List<String> list = new ArrayList<String>();
        String div = Util.outTag(tagDiv).trim();
        String[] tags = div.split("\r\n");
        for (String tag : tags) {
            if (StringUtil.isNotEmpty(tag.trim())) {
                list.add(tag);
            }
        }
        return list;
    }

    public List<String> getLinks(String regexp) {
        List<String> list = new LinkedList<String>();
        String regex = "<a[^>]*href=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</a>";
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }

        // for filter
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("href=\"/question/19581624\"")) {
                list.remove(i);
            }
        }
        return list;
    }

    public List<String> getTimes(String regexp) {
        List<String> list = new LinkedList<String>();
        String regex = "<time [^>]*datetime=(\"([^\"]#)\"|\'([^\']#)\'|([^\\s>]#))[^>]*>(.*?)</time>";
        regex = regex.replace("#", regexp);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }


}
