package com.chris.crawler.yjs.pt.parser;

import com.chris.crawler.yjs.pt.PTConstant;
import com.chris.crawler.yjs.pt.filter.Filter;
import com.chris.crawler.yjs.pt.filter.PTFilter;
import com.chris.crawler.yjs.pt.model.PTJob;
import com.chris.crawler.yjs.pt.service.PTJobService;
import com.chris.crawler.yjs.pt.service.impl.PTJobServiceImpl;
import com.chris.crawler.yjs.pt.spider.PTSpider;
import com.chris.crawler.yjs.pt.util.Util;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.pt.spider.Spider;
import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PTParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTParser.class);
    private static final String YJS_PT_HOME = "ptjob.html";
    private static final String YJS_ENCODING = "gb2312";

    private Spider ptSpider = Guice.createInjector().getInstance(PTSpider.class);
    private Filter ptFilter = Guice.createInjector().getInstance(PTFilter.class);
    private PTJobService ptJobService = Guice.createInjector().getInstance(PTJobServiceImpl.class);

    public PTParser() {
    }

    // <a target="_blank" href="/job-001-567-250.html">[北京]托马斯国际(中国)招聘项目助理实习生 </a>
    // 这里不排重，入库前排除

    /**
     * 保留完整title
     *
     * @param links
     * @return
     */
    public Map<String, PTJob> parserJob(List<String> links) {
        Map<String, PTJob> ptJobs = new HashMap<String, PTJob>();
        String href = "";
        String title = "";
        String fTitle = "";
        String cityEN = "";
        String cityCN = "";
        String[] tmp = null;

        for (String link : links) {
            // for href
            href = PTConstant.YJS_HOME + link.split("/")[1].split("\"")[0].trim();
            title = Util.outTag(link).replace("&nbsp;", "").trim();
            fTitle = title;

            // for city
            if (title.contains("]")) {
                tmp = title.split("]");//[北京]托马斯
                fTitle = tmp[1];
                cityCN = tmp[0].substring(1).trim().replace("|", "/");
                if (cityCN.contains("/")) {
                    cityCN = cityCN.split("/")[0].trim(); // [北京] [北京|上海]
                }
            } else {
                cityCN = PTConstant.OTHER_CN;
            }

            if (!ptFilter.filter(fTitle)) {
                continue;
            }

            if (PTConstant.CITIES_DICT.containsKey(cityCN)) {
                cityEN = PTConstant.CITIES_DICT.get(cityCN);// pingying
            } else {
                cityEN = PTConstant.OTHER_EN;
                cityCN = PTConstant.OTHER_CN;
            }

            // for ptjob
            PTJob ptJob = new PTJob();
            ptJob.setCityCN(cityCN);
            ptJob.setCityEN(cityEN);
            ptJob.setTitle(title);
            ptJob.setUrl(href);
            ptJob.setMd5Url(StringUtil.md5(href));
            ptJobs.put(ptJob.getMd5Url(), ptJob);
        }
        return ptJobs;
    }

    /**
     * http://www.yingjiesheng.com/beijing-moreptjob-3.html
     * http://www.yingjiesheng.com/beijing/ptjob.html
     * 解析出所有 城市的link
     *
     * @return null or List
     */
    public List<String> getCityLinks(String city, int page) {
        // for home
        StringBuilder url = new StringBuilder();
        String content = "";
        List<String> links = null;
        List<String> pageLinks = null;
        url.append(PTConstant.YJS_HOME).append(city).append("/").append(YJS_PT_HOME);

        String proxy = Util.getRandomProxy();
        content = ptSpider.getHtmlContent(url.toString(), YJS_ENCODING, proxy);
        links = ptSpider.getWebLinks(content);

        // try again
        if (links.size() == 0) {
            content = ptSpider.getHtmlContent(url.toString(), YJS_ENCODING, proxy);
            links = ptSpider.getWebLinks(content);
        }
        LOGGER.info(" get home url {} links size {}.", url.toString(), links.size());

        // for paging
        for (int i = 1; i <= page; i++) {
            url.replace(0, url.toString().length(), "");
            url.append(PTConstant.YJS_HOME).append(city).append("-moreptjob-").append(i).append(".html");

            content = ptSpider.getHtmlContent(url.toString(), YJS_ENCODING, proxy);
            pageLinks = ptSpider.getWebLinks(content);

            // try again
            if (pageLinks.size() == 0) {
                content = ptSpider.getHtmlContent(url.toString(), YJS_ENCODING, proxy);
                pageLinks = ptSpider.getWebLinks(content);
            }
            LOGGER.info(" get paging url {} links size {}.", url.toString(), pageLinks.size());
            proxy = Util.getRandomProxy();
            links.addAll(pageLinks);
        }
        return links;
    }

    public int save2DB(Map<String, PTJob> ptJobMap) {
        PTJob ptJob = null;
        int size = 0;
        for (String md5Url : ptJobMap.keySet()) {
            ptJob = ptJobMap.get(md5Url);
            if (ptJobService.exist(md5Url)) {
                LOGGER.info("pt url {} has crawled.", ptJob.getUrl());
                continue;
            }
            ptJobService.insert(ptJob);
            size++;
        }
        return size;
    }

}