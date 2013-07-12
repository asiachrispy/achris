package com.chris.crawler.index.ask;

import com.dajie.crawler.zhihu.model.ZHQuestion;
import com.dajie.crawler.zhihu.service.ZHQuestionService;
import com.dajie.crawler.zhihu.service.impl.ZHQuestionServiceImpl;
import com.google.inject.Guice;

import java.io.IOException;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-6-3
 */
public class Indexer {
    private static WriteIdx writer = Guice.createInjector().getInstance(WriteIdx.class);

    public static void main(String[] args) {
        try {
            int start = 1;
            if (args != null && args.length == 1) {
                start = Integer.valueOf(args[0]);
            }
            ZHQuestionService questionService = Guice.createInjector().getInstance(ZHQuestionServiceImpl.class);
            int total = 0;
            final int page = 300;
            List<ZHQuestion> qs = questionService.getPaging(start, page);
            while (qs.size() > 0) {
                for (ZHQuestion q : qs) {
                    total++;
                    writer.write(q);
                }
                writer.getIndexWriter().commit();
                start += qs.size();
                System.out.println(start);
                qs = questionService.getPaging(start, page);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
