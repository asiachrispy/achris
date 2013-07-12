package com.dajie.crawler.zhihu.test;

import com.chris.crawler.zhihu.model.ZHAnswer;
import com.chris.crawler.zhihu.parser.ZHAParser;
import com.chris.crawler.zhihu.parser.ZHCParser;
import com.chris.crawler.zhihu.service.impl.ZHAnswerServiceImpl;
import com.chris.crawler.zhihu.spider.Spider;
import com.chris.crawler.zhihu.service.ZHAnswerService;
import com.chris.crawler.zhihu.spider.ZHSpider;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class TestAParser {

    Spider spider = new ZHSpider();
    int qid = 21062948;
    String url = "http://www.zhihu.com/question/#";//http://www.zhihu.com/question/19635744";
    String content = null;

    ZHCParser cparser = Guice.createInjector().getInstance(ZHCParser.class);
    ZHAnswerService service = Guice.createInjector().getInstance(ZHAnswerServiceImpl.class);

    @Before
    public void before() {
        try {
            content = spider.getHtmlWithLogin(url.replace("#", qid + ""), "utf-8");
        } catch (Exception e) {

        }
    }

    @Test
    public void testA() {
        ZHAParser parser = new ZHAParser(cparser);
        List<ZHAnswer> zhAnswers = new LinkedList<ZHAnswer>();
        parser.parser(content, zhAnswers);
        for (ZHAnswer a : zhAnswers) {
            System.out.println(a.toString());
            a.setQid(qid);
//            service.insert(a);
        }

    }
}
