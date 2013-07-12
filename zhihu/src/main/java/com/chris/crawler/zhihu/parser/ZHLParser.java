package com.chris.crawler.zhihu.parser;

import com.chris.crawler.zhihu.model.LogItem;
import com.chris.crawler.zhihu.model.ZHLog;
import com.dajie.common.util.DateUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class ZHLParser implements Parser<ZHLog> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZHLParser.class);
    /**
     * <div class="zm-item" id="logitem-59071203">
     * <h2 class="zm-item-title">
     * <a target="_blank" href="/question/21041911">喜欢一个人你先打量哪里？</a>
     * </h2>
     * <div class="zm-item-meta">
     * <span class="zg-bull">&bull;</span><time datetime="2013-05-10 11:48:52">2013-05-10 11:48:52</time>
     * </div>
     * </div>
     *
     * @return
     */
    @Override
    public void parser(Document doc, ZHLog zhLog) {
        if (doc != null) {
            Elements elements = doc.getElementsByClass("zm-item");
            LinkedList<LogItem> logs = new LinkedList<LogItem>();
            zhLog.setLogItems(logs);

            Integer id = null;
            Integer qid = null;
            Date date = null;
            LogItem item = null;
            for (Element e : elements) {
                item = new LogItem();
                qid = Integer.valueOf(e.select("a[href]").first().attr("href").split("/")[2].split("\"")[0]);
                if (qid == 19581624) {// for filter
                    elements.remove(e);
                    continue;
                }
                date = DateUtil.parseDate(e.select("time[datetime]").first().text(), "yyyy-MM-dd");
                id = Integer.valueOf(e.attr("id").split("-")[1]);

                item.setQid(qid);
                item.setCreateDate(date);
                item.setId(id);

                logs.add(item);
            }
            if (logs.size() > 0) {
                zhLog.setStartId(logs.getFirst().getId());
                zhLog.setEndId(logs.getLast().getId());
            }
        } else {
           LOGGER.warn("ZHLParser.parser document is null. ");
        }
    }

    @Override
    public void parser(String html, ZHLog zhLog) {
    }


}
