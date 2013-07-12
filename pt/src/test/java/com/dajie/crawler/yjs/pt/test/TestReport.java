package com.dajie.crawler.yjs.pt.test;

import com.chris.crawler.yjs.pt.PTReporter;
import com.dajie.common.util.DateUtil;

import java.util.Date;

/**
 * User: zhong.huang
 * Date: 13-4-25
 */
public class TestReport {
    public static void main(String[] args) {
        String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        String file = "E:\\temp\\yjs-pt-report-" + date + ".txt";
        PTReporter report = new PTReporter(date, file);
        report.run();
    }
}
