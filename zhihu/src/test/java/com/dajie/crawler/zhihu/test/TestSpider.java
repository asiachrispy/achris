package com.dajie.crawler.zhihu.test;

import com.chris.crawler.zhihu.spider.Spider;
import com.dajie.crawler.zhihu.demo.Parser;
import com.chris.crawler.zhihu.spider.ZHSpider;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class TestSpider {
    Spider spider = new ZHSpider();
    String content = null;
    String qcontent = null;

    Parser parser = new Parser(qcontent);

    @Before
    public void before() {
        try {
            spider.getHtmlWithLogin("http://www.zhihu.com/log/questions?start=59146189&offset=30&_xsrf=7547a995c9c840db8bf28202107a5fe6", "utf-8");
            qcontent = spider.getHtml("http://www.zhihu.com/question/19635744", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAA() {
        System.out.println(content);
    }


    @Test
    public void testA() {
        System.out.println(content);
    }

    @Test
    public void testB() { //<a href="/question/21042796" target="_blank">求大神帮助找个姑娘...?</a>
        List<String> list = parser.getLinks("/question/[0-9]");
        for (String q : list) {
            System.out.println(">>>" + q);
        }
        System.out.println("size>>>" + list.size());
    }


    @Test
    public void testC() {//<time datetime="2013-05-10 15:23:40">2013-05-10 15:23:40</time>
        List<String> list = parser.getTimes("[0-9 :-]");
        for (String q : list) {
            System.out.println(">>>" + q);
        }
        System.out.println("size>>>" + list.size());
    }

    @Test
    public void testD() {
        List<String> list = parser.getLinks("/question/[0-9]");
        List<Integer> qids = parser.parserQids(list);
        for (Integer i : qids) {
            System.out.println(">>>" + i);
        }

        list = parser.getTimes("[0-9 :-]");
        List<Date> dates = parser.parserTimes(list);
        for (Date i : dates) {
            System.out.println(">>>" + i.toString());
        }
    }

    @Test
    public void testE() {
        String title = parser.getTitle("zm-item-title");
        System.out.println(title);//zh-question-title
        System.out.println(parser.parserTitle(title));//zh-question-title
    }

    @Test
    public void testF() {
        String tag = parser.getTag("zm-tag-editor-labels");
        System.out.println(tag);//zh-question-title

        List<String> tags = parser.parserTags(tag);
        for (String t : tags) {
            System.out.println(">>>tag=" + t);//zh-question-title
        }
    }

    @Test
    public void testG() {
        String detail = parser.getDetail("zm-editable-content");
        System.out.println(detail);//zh-question-title
        System.out.println(parser.parserDetail(">>>detail=" + detail));//zh-question-title
    }

}
