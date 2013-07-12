package com.dajie.crawler.yjs.ft.demo;

import com.chris.crawler.yjs.ft.model.FTJob;
import com.chris.crawler.yjs.ft.parser.FTParser;
import com.chris.crawler.yjs.ft.spider.Spider;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.ft.spider.FTSpider;
import org.junit.Test;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-2
 */
public class TestFTParser {

    FTParser ftParser = new FTParser();
    Spider ftSpider = new FTSpider();
    String content = ftSpider.getHtmlContent("http://www.yingjiesheng.com/job-001-575-031.html", "gb2312", ""); //http://www.yingjiesheng.com/job-001-571-488.html
    //// http://www.yingjiesheng.com/job-001-576-224.html
    //http://www.yingjiesheng.com/job-001-566-429.html
    //http://www.yingjiesheng.com/job-001-565-932.html
    // has apply

    @Test
    public void testGetBasicInfo() {
        FTJob ftJob = new FTJob();

        List<String> links = ftSpider.getBasicInfo(content);
        ftParser.parserBasicInfo(links, ftJob);

        String title = ftSpider.getTitle(content);
        ftParser.parserTitle(title, ftJob);

        if (StringUtil.isEmpty(ftJob.getPlace()) || ftJob.getPlace().equals("999999,")) {
            ftParser.parserPlace(title, ftJob);
        }

        links = ftSpider.getMajor(content);
        ftParser.parserMajor(links, ftJob);

        String jobContent = ftSpider.getContent(content);
        ftParser.parserDegree(jobContent, ftJob);

        String apply = ftSpider.getApply(jobContent);
        ftParser.parserApply(apply, ftJob);

        ftParser.parserContent(jobContent, ftJob);
        System.out.println(ftJob.toString());

    }
}
