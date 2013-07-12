package com.chris.crawler.zhihu.parser;

import com.chris.crawler.zhihu.model.ZHAnswer;
import com.chris.crawler.zhihu.util.Util;
import com.dajie.common.util.DateUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class ZHAParser implements Parser<List<ZHAnswer>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZHAParser.class);

    private ZHCParser commentParser;

    public ZHAParser(ZHCParser commentParser) {
        this.commentParser = commentParser;
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
     * <span class="answer-date-link-wrap"><a href="/question/21021314/answer/16915841" target="_blank" class="answer-date-link meta-item">2013-05-11</a></span>
     * </div>
     *
     * @return
     */
    public void parser(Document doc, List<ZHAnswer> answers) {
        if (doc != null) {
            Elements ansEles = doc.getElementsByClass("zm-item-answer");
            //.getElementsByAttributeValue("class","zm-item-answer");

            ZHAnswer zhAnswer = null;
            for (Element answer : ansEles) {
                zhAnswer = new ZHAnswer();
                zhAnswer.setContent(parserContent(answer));
                zhAnswer.setAid(parserAid(answer));
                zhAnswer.setCreateDate(parserDate(answer));
                answers.add(zhAnswer);
            }
        } else {
            LOGGER.warn("ZHAParser.parser document is null. ");
        }
    }

    @Override
    public void parser(String html, List<ZHAnswer> answers) {
    }

    private Integer parserAid(Element doc) {
        String aid = doc.attr("data-aid");
        return Integer.valueOf(aid);
    }

    private Date parserDate(Element doc) {
        Element element = doc.getElementsByAttributeValue("class", "answer-date-link meta-item").first();
        String vDate = element.text().replace("昨天", Util.getYesterday("yyyy-MM-dd"));
        if (vDate.length() < 10) {
            vDate = Util.getNowDate("yyyy-MM-dd ") + vDate;
        }
        if (vDate.contains(":")) {
            return DateUtil.parseDate(vDate, "yyyy-MM-dd HH:mm");// "昨天 15:45"
        } else {
            return DateUtil.parseDate(vDate, "yyyy-MM-dd");// "昨天 15:45"
        }

    }

    /**
     * 保留纯文本
     *
     * @param doc
     * @return
     */
    private String parserContent(Element doc) {
        Element element = doc.getElementsByAttributeValue("class", "zm-item-rich-text").first();
        //doc.getElementsByClass("zm-editable-content*").first();
        String v = element.text();//.text();
        v = Util.outTag(v);//v.substring(v.indexOf(">") + 1, v.length() - 6).trim();//</div>
        return v;
    }

}
