package com.chris.crawler.yjs.ft;

import com.chris.crawler.yjs.ft.model.FTJob;
import com.chris.crawler.yjs.ft.model.FTJobURL;
import com.chris.crawler.yjs.ft.service.FTJobService;
import com.chris.crawler.yjs.ft.service.FTJobURLService;
import com.chris.crawler.yjs.ft.service.impl.FTJobServiceImpl;
import com.chris.crawler.yjs.ft.service.impl.FTJobURLServiceImpl;
import com.chris.crawler.yjs.ft.spider.FTSpider;
import com.chris.crawler.yjs.ft.spider.Spider;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.dubbo.util.StringUtil;
import com.chris.crawler.yjs.ft.parser.FTParser;
import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * User: zhong.huang
 * Date: 13-4-26
 */
public class FTCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTCrawler.class);
    private static final Integer MAX_PAGE = Integer.valueOf(AppConfigs.getInstance().get(FTConstant.CONFIG_FU_MAX_PAGE));

    private Spider ftSpider = Guice.createInjector().getInstance(FTSpider.class);
    private FTParser ftParser = Guice.createInjector().getInstance(FTParser.class);
    private FTJobURLService ftJobURLService = Guice.createInjector().getInstance(FTJobURLServiceImpl.class);
    private FTJobService ftJobService = Guice.createInjector().getInstance(FTJobServiceImpl.class);

    public void run() {
        long start = 0;
        String baseUrl = "http://www.yingjiesheng.com/commend-fulltime-#.html";
        String homeUrl = null;
        String htmlContent = "";
        Set<String> jobLinks = null;
        List<String> homeLinks = null;
        FTJob ftJob = null;
        FTJobURL ftJobURL = null;
        String md5Sum = "";
        int okCount = 0;
        int existCount = 0;
        for (int i = 0; i <= MAX_PAGE; i++) {

            start = System.currentTimeMillis();
            if (i == 0) {
                homeUrl = "http://www.yingjiesheng.com";
            } else {
                homeUrl = baseUrl.replace("#", String.valueOf(i));
            }

            htmlContent = ftSpider.getHtmlContent(homeUrl, FTConstant.DEFAULT_ENCODING, FTConstant.DEFAULT_PROXY_IP);
            homeLinks = ftSpider.getLinks(htmlContent, "/job-");
            jobLinks = ftParser.parserHomeLinks(homeLinks);
            for (String yjsUrl : jobLinks) {
                ftJob = new FTJob();
                ftJobURL = new FTJobURL();

                // is already crawled
                md5Sum = StringUtil.md5(yjsUrl);
                if (ftJobURLService.exist(md5Sum)) {
                    existCount++;
                    LOGGER.info(" url = {} has crawled.", yjsUrl);
                    continue;
                }

                // for job
                ftJob.setYjsUrl(yjsUrl);
                ftParser.parserJob(yjsUrl, ftJob);

                // for job url
                ftJobURL.setUrl(yjsUrl);
                ftJobURL.setMd5sum(md5Sum);
                ftJobURL.setStatus(1);
                if (StringUtil.isNotEmpty(ftJob.getRecruitType())) {
                    ftJobURL.setRecruitType(Integer.valueOf(ftJob.getRecruitType()));
                } else {
                    ftJobURL.setRecruitType(1);
                }

                LOGGER.debug(">>>>>>>>>>>>>>>>>>" + ftJob.toString());
                if (ftJob.validate()) {
                    okCount++;
                    ftJobURLService.insert(ftJobURL);
                    ftJobService.insert(ftJob);
                } else {
                    LOGGER.warn("crawled {} failed.", yjsUrl);
                }
            }
            LOGGER.info("crawled {} finds {} projects and parsered-ok {} and exists {} projects used {} seconds.", homeUrl, jobLinks.size(), okCount, existCount, (System.currentTimeMillis() - start) / 1000);
            okCount = 0;
            existCount = 0;
        }
    }

    public static void main(String[] args) {
        FTCrawler crawler = new FTCrawler();
        crawler.run();
        System.exit(0);
    }
}
