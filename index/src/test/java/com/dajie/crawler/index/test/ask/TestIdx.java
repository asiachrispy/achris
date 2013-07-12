package com.dajie.crawler.index.test.ask;

import com.chris.crawler.index.IndexConstant;
import com.chris.crawler.index.ask.WriteIdx;
import com.chris.crawler.index.ask.ReadIdx;
import com.dajie.crawler.zhihu.model.ZHQuestion;
import com.dajie.crawler.zhihu.service.ZHQuestionService;
import com.dajie.crawler.zhihu.service.impl.ZHQuestionServiceImpl;
import com.google.inject.Guice;
import org.apache.lucene.queryParser.ParseException;
import org.junit.Test;

import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-23
 */
public class TestIdx {

    ZHQuestionService questionService = Guice.createInjector().getInstance(ZHQuestionServiceImpl.class);

    @Test
    public void testA() {
        WriteIdx wInx = new WriteIdx();
//        ZHQuestion q = questionService.getById(8);//自我暴露和自我隐藏有什么关系呢？
        int total = 0;
        int start = 1;
        final int page = 300;
        List<ZHQuestion> qs = questionService.getPaging(start, page);
        while (qs.size() > 0) {
            for (ZHQuestion q : qs) {
                total++;
                wInx.write(q);
            }
            start += qs.size();
        }

        System.out.print(">>>total=" + total);
    }

    @Test
    public void testC() {
        WriteIdx wInx = new WriteIdx();
        ZHQuestion q = questionService.getById(8);//自我暴露和自我隐藏有什么关系呢？
        wInx.write(q);
    }


    @Test
    public void testB() {
        ReadIdx readIdx = new ReadIdx();
        try {
            readIdx.search(new String[]{IndexConstant.F_TITLE, IndexConstant.F_TAGS}, "法律");
        } catch (ParseException e) {
        }
    }
}
