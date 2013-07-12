package com.dajie.crawler.zhihu.test;

import com.chris.crawler.zhihu.model.LogItem;
import com.chris.crawler.zhihu.model.ZHLog;
import com.chris.crawler.zhihu.parser.ZHLParser;
import com.chris.crawler.zhihu.service.ZHLogService;
import com.chris.crawler.zhihu.service.impl.ZHLogServiceImpl;
import com.chris.crawler.zhihu.spider.Spider;
import com.chris.crawler.zhihu.spider.ZHSpider;
import com.google.inject.Guice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class LParserTest {

    Spider spider = new ZHSpider();
    String url = "http://www.zhihu.com/question/#";
    String content = null;
    ZHLogService logService = Guice.createInjector().getInstance(ZHLogServiceImpl.class);

    @Before
    public void before() {
        try {
            content = spider.getHtmlWithLogin("http://www.zhihu.com/log/que" +
                "stions?start=58627850&offset=20&_xsrf=7547a995c9c840db8bf28202107a5fe6", "utf-8");

        } catch (Exception e) {

        }
    }

    @Test
    public void testA() throws Exception {
        ZHLParser parser = new ZHLParser();
        ZHLog zhLog = new ZHLog();
        Document doc = Jsoup.parse(content);
        parser.parser(doc, zhLog);
        long start = 0l;
        long times = System.currentTimeMillis();
        int counts = 0;
        while (zhLog.getLogItems().size() > 0) {
            for (LogItem log : zhLog.getLogItems()) {
                counts++;
                start = System.currentTimeMillis();
                spider.getHtml(url.replace("#", log.getQid() + ""), "");
                System.out.println(">>>" + (System.currentTimeMillis() - start));
            }

            if (counts > 1000) {
                break;
            }
        }
        System.out.println(">>> " + (System.currentTimeMillis() - times) / counts);
    }

    @Test
    public void testB() {
        ZHLog zhLog = new ZHLog();
        zhLog.setId(1);
        zhLog.setEndId(63748849);
        zhLog.setStartId(60708622);
        logService.update(zhLog);
    }

    @Test
    public void testE() {
        ZHLog zhLog = new ZHLog();
        zhLog.setId(1);
        zhLog.setEndId(63748849);
        zhLog.setStartId(60708622);
        logService.insert(zhLog);
    }

    @Test
    public void testC() {
        System.out.println(logService.getEnd(1));
        System.out.println(logService.getStart(1));
    }
}
