package com.chris.crawler.yjs.pt;

import com.chris.crawler.yjs.pt.parser.PTParser;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * User: zhong.huang
 * Date: 13-4-25
 */
public class PTMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTMain.class);

    /**
     * 如果有时间参数则使用提供的时间，否则默认使用系统当前时间
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            LOGGER.info(">>>>start crawler.");
            PTParser ptParser = new PTParser();
            PTCrawler ptCrawler = new PTCrawler();
            ptCrawler.run(ptParser);

            LOGGER.info(">>>>finish crawler. sleep 1 min.");
            Thread.sleep(60 * 1000);
            LOGGER.info(">>>>start sendEmail.");

            String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
            if (args != null && args.length > 0) {
                date = args[0];
            }

            String file = AppConfigs.getInstance().get("report_path") + "yjs-pt-report-" + date + ".txt";
            PTReporter report = new PTReporter(date, file);
            report.run();
            LOGGER.info(">>>>finish sendEmail.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
