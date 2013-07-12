package com.dajie.crawler.yjs.ft.demo;

import com.chris.crawler.yjs.ft.filter.FTFilter;
import com.chris.crawler.yjs.ft.filter.Filter;
import com.chris.crawler.yjs.ft.spider.FTSpider;
import com.chris.crawler.yjs.ft.spider.Spider;
import org.junit.Test;

/**
 * User: zhong.huang
 * Date: 13-5-2
 */
public class TestFTFileter {
    Spider ftSpider = new FTSpider();
    Filter filter = new FTFilter();
    String content = ftSpider.getHtmlContent("http://www.yingjiesheng.com/job-001-566-429.html", "gb2312", ""); //

    // has apply  http://www.yingjiesheng.com/job-001-573-330.html
    //http://www.yingjiesheng.com/job-001-566-429.html  http://www.yingjiesheng.com/job-001-573-549.html
    //http://www.yingjiesheng.com/job-001-565-932.html
    // http://www.yingjiesheng.com/job-001-566-429.html
    @Test
    public void testGetBasicInfo() {
        String jobContent = ftSpider.getContent(content);
        jobContent = filter.filter(jobContent);
        System.out.println(jobContent);


//        String value = "a<table>f</table>b";
//        System.out.println(value.replaceAll("<table[^>]*>(.*?)</table>", ""));
//         System.out.println(value.contains("<table>*"));
//         System.out.println(value.replaceAll("<table[^>]*>((.*?)(\r\n)*(.*?)((\n)*)(.*?))((.*?)((\n)*)(.*?)((\r\n)*)(.*?))</table>",""));
    }
}

