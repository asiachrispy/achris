package com.chris.crawler.haitou;

import com.chris.crawler.haitou.parser.HTParser;
import com.chris.crawler.haitou.service.HTxjhService;
import com.chris.crawler.haitou.model.HTxjh;
import com.chris.crawler.haitou.service.impl.HTxjhServiceImpl;
import com.chris.crawler.haitou.util.DictUtil;
import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-31
 */
public class HTCrawler extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTCrawler.class);

    HTParser parser = Guice.createInjector().getInstance(HTParser.class);
    HTxjhService service = Guice.createInjector().getInstance(HTxjhServiceImpl.class);

    public void run() {
        List<HTxjh> hts = null;
        int count = -1;
        for (String city : DictUtil.getInstance().getCity().keySet()) {
            hts = parser.getArticle(city);

            for (HTxjh ht : hts) {
                count = service.getUpdateCount(ht.getUrlMd5());
                if (count >= 0) { // exists
                    if (ht.getUpdateCount() > count) { // has update
                        service.updateCount(ht.getUrlMd5(), ht.getUpdateCount());
                    }
                    continue;
                }
                service.insert(ht);
                LOGGER.info(String.format("%d - %s", ht.getCity(), ht.getUrl()));
            }
        }
    }

    public static void main(String[] args) {
        HTCrawler crawler = new HTCrawler();
        crawler.start();
    }

}
