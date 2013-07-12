package com.dajie.crawler.zhihu.demo;

import com.chris.crawler.zhihu.model.ZHComment;
import com.chris.crawler.zhihu.util.Util;
import com.dajie.common.util.DateUtil;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.zhihu.model.ZHAnswer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class TZHQParser {
    private String html;

    public TZHQParser() {
    }

    public TZHQParser(String html) {
        this.html = html;
    }


    /**
     * parser <a href="/question/123123">如何删除百度知道中别人发布的涉及自己的内容？</a>
     *
     * @return
     */
    public List<Integer> parserQids() {
        List<String> links = Util.getTagsByClass("a", html, "/question/[0-9]");
        List<Integer> list = new LinkedList<Integer>();
        Integer qid = -1;
        for (String tag : links) {
            qid = Integer.valueOf(tag.split("/")[2].split("\"")[0]);
            if (qid == 19581624) {// for filter
                continue;
            }
            list.add(qid);
        }
        return list;
    }

    /**
     * <div data-score="40.12722" data-isowner="0" data-helpful="1" data-isfriend="0"
     * data-deleted="0" data-created="1364958032" data-collapsed="0" data-atoken="16578599"
     * data-aid="1762965" class="zm-item-answer " tabindex="-1">
     * <div class="zm-editable-content clearfix">
     * 受黄老板邀请。回答。必须不匿名。<br><br>解题：1，京品会和唯品会对比，竞争如何？<br>
     * </div>
     * <p/>
     * <div class="zm-comment-box" data-count="3">
     * <div class="zm-comment-content">
     * 非常！
     * </div>
     * <div class="zm-comment-content">
     * 最赞还是PVP。。
     * </div>
     * <div class="zm-comment-content">
     * 尤其是现在这版本，一个奶妈就能拖半个小时不认输。。
     * </div>
     * </div>
     * </div>
     *
     * @return
     */
    public List<ZHAnswer> parserAnswers() {
        List<String> answers = Util.getTagsByClass("div", html, "zm-item-answer*");
        List<ZHAnswer> list = new LinkedList<ZHAnswer>();
        List<ZHComment> cList = new LinkedList<ZHComment>();
        String content = "";
        Integer aid = 0;
        ZHAnswer zhAnswer = new ZHAnswer();
        Document doc = null;
        for (String answer : answers) {
            doc = Jsoup.parse(answer);
            content = parserAnswerContent(doc);
            aid = parserAid(doc);
            list.add(zhAnswer);
        }
        return list;
    }

    public Integer parserAid(Document doc) {
        String aid = doc.attr("data-aid");
        return Integer.valueOf(aid);
    }

    public String parserAnswerContent(Document doc) {
        //List<String> answers = Util.getTagsByClass("div", answerNode, "zm-editable-content");
        //if (answers.size() > 0) {
        //    return answers.get(0);
        //}

        Element element = doc.getElementsByClass("zm-editable-content*").first();
        String content = element.text();
        return content;
    }


    /**
     * parser <time datetime="2013-05-10 16:44:40">2013-05-10 16:44:40</time>
     *
     * @return
     */
    public List<Date> parserTimes() {
        List<String> times = Util.getTagsByAttribute("time", "datetime", html, "[0-9 :-]");
        List<Date> list = new LinkedList<Date>();
        Date createDate = new Date();
        for (String tag : times) {
            createDate = DateUtil.parseDate(tag.split("\"")[1], "yyyy-MM-dd HH:mm:SS");
            list.add(createDate);
        }
        return list;
    }

    /**
     * <div id="zh-question-title" class="zm-editable-status-normal">
     * <h2 class="zm-item-title zm-editable-content">《特别关注》杂志是怎么火起来的？<a name="edit" class="zu-edit-button" href="javascript:;">
     * <i class="zu-edit-button-icon"></i>修改</a></h2>
     * </div>
     *
     * @return
     */
    public String parserTitle() {
        List<String> tags = Util.getTagsByAttribute("h2", "class", html, "zm-item-title*");
        String tagDiv = "";
        if (tags.size() > 0) {
            tagDiv = tags.get(0);
        }
        return Util.outTag(tagDiv);
    }

    /**
     * <div class="zm-editable-content">比如说史特拉第瓦利，阿瑪悌，瓜納里，瓜達尼尼~<br>为什么说他们是很好的琴呢~？
     * <a name="edit" class="zu-edit-button" href="javascript:;">
     * <i class="zu-edit-button-icon"></i>修改</a>
     * </div>
     *
     * @return
     */
    public String parserDetail() {
        List<String> tags = Util.getTagsByClass("div", html, "zm-editable-content");
        String tagDiv = "";
        if (tags.size() > 0) {
            tagDiv = tags.get(0);
        }

        return Util.outTag(tagDiv);
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
    public List<String> parserTags() {
        List<String> tags = Util.getTagsByClass("div", html, "zm-tag-editor-labels*");
        String tagDiv = "";
        if (tags.size() > 0) {
            tagDiv = tags.get(0);
        }

        List<String> list = new ArrayList<String>();
        String div = Util.outTag(tagDiv).trim();
        String[] tagv = div.split("\r\n");
        for (String tag : tagv) {
            if (StringUtil.isNotEmpty(tag.trim())) {
                list.add(tag);
            }
        }
        return list;
    }
}
