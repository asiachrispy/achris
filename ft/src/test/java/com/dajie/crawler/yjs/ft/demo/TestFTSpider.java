package com.dajie.crawler.yjs.ft.demo;

import com.chris.crawler.yjs.ft.spider.FTSpider;
import com.chris.crawler.yjs.ft.spider.Spider;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-2
 */
public class TestFTSpider {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TestFTSpider.class);

    Spider ftSpider = new FTSpider();
    String content = ftSpider.getHtmlContent("http://www.yingjiesheng.com/job-001-576-358.html", "gb2312", "");
    //http://www.yingjiesheng.com/job-001-574-236.htmlhttp://www.yingjiesheng.com/job-001-574-236.html
    //http://www.yingjiesheng.com/job-001-572-279.htmlhttp://www.yingjiesheng.com/job-001-575-031.html

    @Test
    public void testGetBasicInfo() {
        List<String> links = ftSpider.getBasicInfo(content);
        for (String link : links) {
            LOGGER.info(">>>" + link);
        }
    }

    @Test
    public void testB() {
        List<String> links = ftSpider.getMajor(content);
        for (String link : links) {
            System.out.println(">>>" + link);
        }
    }

    @Test
    public void testC() {
        String contents = ftSpider.getContent(content);
        System.out.println(">>>" + contents);
    }

    @Test
    public void testD() {
        String contents = ftSpider.getContent(content);
//        List<String> pros = ftSpider.getFilterProgram(contents);
//        for (String pro : pros) {
//            System.out.println(">>>" + pro);
//        }
    }

    @Test
    public void testE() {
        String title = ftSpider.getTitle(content);
        System.out.println(">>>" + title);
    }
}
