package com.chris.crawler.zhihu.parser;

import com.chris.crawler.zhihu.ZHConstant;
import com.chris.crawler.zhihu.model.ZHComment;
import com.chris.crawler.zhihu.spider.ZHSpider;
import com.chris.crawler.zhihu.util.Util;
import com.dajie.common.util.DateUtil;
import com.google.inject.Guice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class ZHCParser implements Parser<List<ZHComment>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZHCParser.class);

    //http://www.zhihu.com/node/AnswerCommentBoxV2?params=%22answer_id%22:%221899714%22,%22load_all%22:false
    //==params	{"answer_id":"1899714","load_all":false}
    private static final String ANSWER_ACTION = "http://www.zhihu.com/node/AnswerCommentBoxV2?params={%22answer_id%22:%22#%22,%22load_all%22:false}";

    @Override
    public void parser(Document doc, List<ZHComment> comments) {

    }

    /**
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
     *
     * @return
     */
    @Override
    public void parser(String aid, List<ZHComment> comments) {
        String url = ANSWER_ACTION.replace("#", aid.toString());
        try {
            String html = Guice.createInjector().getInstance(ZHSpider.class).getHtml(url, ZHConstant.ENCODING_UTF8);

            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByClass("zm-comment-content");
            // <span class="date">2013-05-06</span>
            Elements dates = doc.getElementsByClass("date");
            ZHComment zhComment = null;
            String vDate = null;
            for (int i = 0; i < elements.size(); i++) {
                zhComment = new ZHComment();
                zhComment.setContent(elements.get(i).text());
                zhComment.setAid(Integer.valueOf(aid));
//            zhComment.setCid(Integer.valueOf(elements.get(i).attr("data-id")));
                vDate = dates.get(i).text().replace("昨天", Util.getYesterday("yyyy-MM-dd"));
                if (vDate.length() < 10) {
                    vDate = Util.getNowDate("yyyy-MM-dd ") + vDate;
                }

                if (vDate.contains(":")) {
                    zhComment.setCreateDate(DateUtil.parseDate(vDate, "yyyy-MM-dd HH:mm")); //"17:21"
                } else {
                    zhComment.setCreateDate(DateUtil.parseDate(vDate, "yyyy-MM-dd")); //"17:21"
                }
                comments.add(zhComment);
            }
        } catch (Exception e) {
            LOGGER.warn("ZHCParser.parser exception ", e);
        }
    }
}
