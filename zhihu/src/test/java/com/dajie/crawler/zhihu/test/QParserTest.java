package com.dajie.crawler.zhihu.test;

import com.chris.crawler.zhihu.model.ZHComment;
import com.chris.crawler.zhihu.model.ZHQuestion;
import com.chris.crawler.zhihu.parser.*;
import com.chris.crawler.zhihu.spider.ZHSpider;
import com.chris.crawler.zhihu.parser.ZHAParser;
import com.chris.crawler.zhihu.parser.ZHCParser;
import com.chris.crawler.zhihu.parser.ZHQParser;
import com.chris.crawler.zhihu.service.ZHQuestionService;
import com.chris.crawler.zhihu.service.impl.ZHQuestionServiceImpl;
import com.chris.crawler.zhihu.spider.Spider;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class QParserTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(QParserTest.class);

    Spider spider = new ZHSpider();

    int qid = 19629968;
    String url = "http://www.zhihu.com/question/#";//http://www.zhihu.com/question/21021314";//
    String content = null;
    private Parser<List<ZHComment>> cParser = new ZHCParser();
    private ZHAParser aParser = new ZHAParser((ZHCParser) cParser);
    ZHQuestionService questionService = Guice.createInjector().getInstance(ZHQuestionServiceImpl.class);

    @Before
    public void before() {
        try {
            content = spider.getHtmlWithLogin(url.replace("#", qid + ""), "utf-8");
        } catch (Exception e) {
        }
    }

    @Test
    public void testA() {
        ZHQParser parser = new ZHQParser(aParser);
        ZHQuestion zhQuestion = new ZHQuestion();
        zhQuestion.setUid(64928);
        zhQuestion.setQid(qid);
        zhQuestion.setPubDate(new Date());

        parser.parser(content, zhQuestion);
//        LOGGER.info(zhQuestion.toString());
        System.out.println(zhQuestion.toString());
        //questionService.insert(zhQuestion);
    }

    @Test
    public void testB() {
        LOGGER.info(questionService.exist(qid) + "");
    }

    @Test
    public void testC() {
        System.out.println(questionService.getById(1).toString());
    }

    @Test
    public void testE() {

    }

    @Test
    public void testD() {
        List<ZHQuestion> list = questionService.getPaging(1, 2);
        for (ZHQuestion q : list)
            System.out.println(q.toString());
    }
}
