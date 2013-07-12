package com.chris.crawler.yjs.pt;

import com.chris.crawler.yjs.pt.model.PTJob;
import com.chris.crawler.yjs.pt.service.impl.PTJobServiceImpl;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.monitor.postman.EmailLetter;
import com.dajie.common.monitor.postman.SenderManager;
import com.dajie.common.monitor.postman.exception.ChannelException;
import com.dajie.common.util.DateUtil;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.pt.service.PTJobService;
import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-25
 */
public class PTReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTReporter.class);
    private PTJobService ptJobService = Guice.createInjector().getInstance(PTJobServiceImpl.class);
    private String date;
    private String file;

    public PTReporter(String date, String file) {
        this.date = date;
        this.file = file;
    }

    public void run() {
        if (StringUtil.isNotEmpty(date)) {
            List<PTJob> ptJobList = ptJobService.getByDate(date);
            StringBuilder row = new StringBuilder();

            for (PTJob ptjob : ptJobList) {
                row.append("[").append(ptjob.getCityCN()).append("]").append(ptjob.getTitle()).append("\n")
                    .append(ptjob.getUrl()).append("\n\n");
            }
            this.save2File(row.toString());
            this.postEmail();
        }
    }

    private void save2File(String row) {
        try {
            File f = new File(file);
            if (f.exists()) {
                LOGGER.warn("{} exists.", file);
                String sufix = DateUtil.formatDate(new Date(), "HH-mm-ss");
                f.renameTo(new File(f.getAbsoluteFile() + "." + sufix));
            }
            f.createNewFile();
            FileOutputStream fout = new FileOutputStream(f);
            OutputStreamWriter osw = new OutputStreamWriter(fout, "UTF-8");// 注意乱码问题
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(row);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void postEmail() {
        try {
            List list = new ArrayList<String>();
            String report_receiver = AppConfigs.getInstance().get(PTConstant.REPORT_RECEIVER);
            String[] report_receivers = report_receiver.split(";");
            for (String email : report_receivers) {
                list.add(email.trim());
            }
            String content = "应届生实习职位抓取日报表";
            EmailLetter letter = new EmailLetter("应届生实习职位抓取日报表", content, list, file);
            SenderManager.getInstance().send(letter);
        } catch (ChannelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        if (args != null && args.length > 0) {
            date = args[0];
        }

        String file = AppConfigs.getInstance().get(PTConstant.REPORT_PATH) + "yjs-pt-report-" + date + ".txt";
        PTReporter report = new PTReporter(date, file);
        report.run();
        System.exit(0);

    }
}
