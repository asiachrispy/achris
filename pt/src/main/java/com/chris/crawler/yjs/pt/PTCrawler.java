package com.chris.crawler.yjs.pt;

import com.chris.crawler.yjs.pt.model.PTJob;
import com.chris.crawler.yjs.pt.parser.PTParser;
import com.dajie.common.config.AppConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public class PTCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTCrawler.class);

    private Map<String, Integer> getCities() {
        Map<String, Integer> citys = new LinkedHashMap<String, Integer>();
        String[] cities = AppConfigs.getInstance().get(PTConstant.CITIES).split(";");
        String[] kvs = null;
        for (String city : cities) {
            kvs = city.trim().split("-");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("kv = {},{}", kvs[0], kvs[1]);
            }
            citys.put(kvs[0], Integer.valueOf(kvs[1]));
        }
        return citys;
    }

    public void run(PTParser ptParser) {
        List<String> tlinks = null;
        List<String> links = new ArrayList<String>();
        Map<String, Integer> citys = getCities();
        long start = 0;
        for (String city : citys.keySet()) {
            start = System.currentTimeMillis();
            tlinks = ptParser.getCityLinks(city, citys.get(city));
            LOGGER.info("crawler city-{} used {} mins.", city, (System.currentTimeMillis() - start) / 1000);
            links.addAll(tlinks);
        }
        start = System.currentTimeMillis();
        Map<String, PTJob> map = ptParser.parserJob(links);
        LOGGER.info("parser {} jobs used {} mins.", links.size(), (System.currentTimeMillis() - start) / 1000);
        LOGGER.info("save to DB {} size.", ptParser.save2DB(map));
    }

    public static void main(String[] args) {
        PTParser ptParser = new PTParser();
        PTCrawler ptCrawler = new PTCrawler();
        ptCrawler.run(ptParser);
        System.exit(0);
    }
}
