package com.chris.crawler.zhihu.convert;

import com.chris.crawler.zhihu.model.ZHLog;
import com.chris.crawler.zhihu.service.ZHLogService;
import com.chris.crawler.zhihu.service.impl.ZHLogServiceImpl;
import com.google.inject.Guice;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * User: zhong.huang
 * Date: 13-5-15
 */
public class UpdateLogItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateLogItem.class);
    private ZHLogService logService = Guice.createInjector().getInstance(ZHLogServiceImpl.class);

    public void run(int id, int startId, int endId) {
        ZHLog log = new ZHLog();
        log.setId(id);
        log.setStartId(startId);
        log.setEndId(endId);
        int n = logService.update(log);
        if (n == 0) {
            logService.insert(log);
        }
    }

    public static void main(String[] args) {
        UpdateLogItem update = new UpdateLogItem();
        if (args != null && args.length == 3) {
            int start = Integer.valueOf(args[0]);
            int end = Integer.valueOf(args[1]);
            int id = 0;
            if ("day".equals(args[2])) {// for daily
                id = 1;
            } else if ("his".equals(args[2])) {// for history
                id = 2;
            } else {
                LOGGER.warn("Usage: [day|his] [startid] [endid]");
                return;
            }
            update.run(id, start, end);
        } else {
            LOGGER.warn("Usage:[day|his] [startid] [endid]");
        }
    }
}
