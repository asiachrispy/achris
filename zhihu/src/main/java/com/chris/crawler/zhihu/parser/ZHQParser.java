package com.chris.crawler.zhihu.parser;

import com.chris.crawler.zhihu.model.ZHQuestion;
import com.chris.crawler.zhihu.util.Util;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHQParser implements Parser<ZHQuestion> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZHQParser.class);

    private ZHAParser answerParser;

    public ZHQParser(ZHAParser answerParser) {
        this.answerParser = answerParser;
    }

    /**
     * <div id="zh-question-title" class="zm-editable-status-normal">
     * <h2 class="zm-item-title zm-editable-content">《特别关注》杂志是怎么火起来的？
     * <a name="edit" class="zu-edit-button" href="javascript:;">
     * <i class="zu-edit-button-icon"></i>修改</a></h2>
     * </div>
     * <p/>
     * <div class="zm-editable-content">比如说史特拉第瓦利，阿瑪悌，瓜納里，瓜達尼尼~<br>为什么说他们是很好的琴呢~？
     * <a name="edit" class="zu-edit-button" href="javascript:;">
     * <i class="zu-edit-button-icon"></i>修改</a>
     * </div>
     * <p/>
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

    @Override
    public void parser(Document doc, ZHQuestion question) {
        if (doc != null) {
            String title = doc.getElementById("zh-question-title").text();
            String content = doc.getElementById("zh-question-detail").html();
            //.select("h2[class]").first().text();//.getElementsByClass("zm-item-title zm-editable-content").
            content = Util.outTag(content);//content.substring(content.indexOf(">") + 1, content.length() - 6).trim();//</div>
            String tags = parserTags(doc);

            question.setTitle(title);
            question.setDetail(content);
            question.setTags(tags);
        } else {
            LOGGER.warn("ZHQParser.parser doc is null.");
        }
    }

    @Override
    public void parser(String html, ZHQuestion question) {
    }

    /**
     * @return
     */
    public String parserTags(Document doc) {
        StringBuffer sb = new StringBuffer();
        Element element = doc.getElementsByAttributeValue("class", "zm-tag-editor-labels zg-clear").first();
        if (element != null) {
            Elements tags = element.select("a[href]");
            for (Element tag : tags) {
                sb.append(tag.text()).append(",");
            }
        }
        return sb.toString();
    }
}
