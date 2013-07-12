package com.dajie.crawler.yjs.pt.test;

import com.chris.crawler.yjs.pt.spider.PTSpider;
import com.chris.crawler.yjs.pt.util.Util;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.pt.PTConstant;
import com.chris.crawler.yjs.pt.PTCrawler;
import com.chris.crawler.yjs.pt.parser.PTParser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class TestCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCrawler.class);
    private static final PTSpider crawler = new PTSpider();

    @Test
    public void testGetHtmlContent() {
        try {
            String url = "http://www.yingjiesheng.com/beijing/ptjob.html";
            url = "http://www.yingjiesheng.com/beijing-moreptjob-2.html";

            String html = crawler.getHtmlContent(url, "gb2312", "");
            System.out.println(html);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void testGet() {
        try {
            String url = "http://www.yingjiesheng.com/beijing/ptjob.html";
            url = "http://www.yingjiesheng.com/beijing-moreptjob-2.html";
            long start = System.currentTimeMillis();
            String html = crawler.getHtmlContent(url, "gb2312", "122.72.12.52:8118"); //210.101.131.231:8080
            System.out.println((System.currentTimeMillis() - start) + "proxy---------" + html.substring(0, 30));
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void testProxy() {
        try {
            String url = "http://www.yingjiesheng.com/beijing/ptjob.html";
            url = "http://www.yingjiesheng.com/others-moreptjob-2.html";

            String html = "";
            long start = 0;
            for (String proxy : PTConstant.PROXY_IP) {
                start = System.currentTimeMillis();
                html = crawler.getHtmlContent(url, "gb2312", proxy);
                if (StringUtil.isNotEmpty(html))
                    System.out.println((System.currentTimeMillis() - start) + "-----" + proxy + "-----" + html.substring(0, 30));
            }
            /**
             42198-----210.14.148.4:80-----<!DOCTYPE html PUBLIC "-//W3C/
             1145-----218.241.153.43:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             24745-----112.25.12.39:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21211-----112.25.12.38:80-----<!DOCTYPE html PUBLIC "-//W3C/
             331-----210.101.131.231:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             21435-----210.75.120.3:80-----<!DOCTYPE html PUBLIC "-//W3C/
             22505-----61.30.127.2:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21603-----122.72.12.52:8118-----<!DOCTYPE html PUBLIC "-//W3C/
             43278-----122.72.28.22:80-----<!DOCTYPE html PUBLIC "-//W3C/
             43634-----219.83.100.205:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             2576-----124.81.113.183:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             22277-----101.44.1.23:80-----<!DOCTYPE html PUBLIC "-//W3C/
             1334-----211.154.83.38:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21123-----122.72.33.139:80-----<!DOCTYPE html PUBLIC "-//W3C/
             2409-----219.159.105.180:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             1766-----173.213.108.113:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             21170-----122.72.20.127:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21764-----122.72.28.19:80-----<!DOCTYPE html PUBLIC "-//W3C/
             541-----202.98.123.126:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             21274-----218.247.129.5:80-----<!DOCTYPE html PUBLIC "-//W3C/
             5990-----119.254.90.18:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             25065-----218.247.129.35:80-----<!DOCTYPE html PUBLIC "-//W3C/
             24356-----122.72.28.10:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21134-----124.205.178.62:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21176-----122.72.28.20:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21816-----122.72.28.21:80-----<!DOCTYPE html PUBLIC "-//W3C/
             4445-----118.98.75.50:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             21193-----218.247.129.6:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21132-----222.240.224.131:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21122-----122.72.20.124:80-----<!DOCTYPE html PUBLIC "-//W3C/
             51616-----91.144.44.65:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             42165-----218.247.129.7:80-----<!DOCTYPE html PUBLIC "-//W3C/
             21325-----173.213.108.112:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             21133-----62.121.64.19:8080-----<!DOCTYPE html PUBLIC "-//W3C/
             42155-----101.44.1.22:80-----<!DOCTYPE html PUBLIC "-//W3C/
             22124-----211.138.123.60:80-----<!DOCTYPE html PUBLIC "-//W3C/
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPageLink() {
        try {
            String url = "http://www.yingjiesheng.com/beijing-moreptjob-3.html";
            String html = crawler.getHtmlContent(url, "gb2312", "");
            List<String> links = crawler.getWebLinks(html);
            for (String link : links) {
                System.out.println(link);
            }
            System.out.println(links.size());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void testGetHomeLink() {
        try {
            String url = "http://www.yingjiesheng.com/beijing-moreptjob-1.html";
            String html = crawler.getHtmlContent(url, "GBK", "");
            html = new String(html.getBytes(), "utf-8");
            List<String> links = crawler.getWebLinks(html);
            for (String link : links) {
                System.out.println(link);
            }
            System.out.println(links.size());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    // <a target="_blank" href="/job-001-567-250.html">[北京]托马斯国际(中国)招聘项目助理实习生 </a>
    @Test
    public void testOutTag() {
        String a = "<a target=\"_blank\" href=\"/job-001-567-250.html\">[北京]托马斯国际(中国)招聘项目助理实习生 </a>";
        System.out.println(Util.outTag(a));
    }

    public static void main(String[] args) {
        PTParser ptParser = new PTParser();
        PTCrawler ptCrawler = new PTCrawler();
        ptCrawler.run(ptParser);
    }

}