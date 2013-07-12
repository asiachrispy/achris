package com.dajie.crawler.zhihu.test;

import com.chris.crawler.zhihu.model.ZHComment;
import com.chris.crawler.zhihu.parser.ZHCParser;
import com.chris.crawler.zhihu.service.ZHCommentService;
import com.chris.crawler.zhihu.service.impl.ZHCommentServiceImpl;
import com.google.inject.Guice;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-13
 */
public class TestCParser {
    int qid = 21062948;
    int aid = 1954857;
    ZHCommentService service = Guice.createInjector().getInstance(ZHCommentServiceImpl.class);

    @Test
    public void testA() {
        ZHCParser parser = new ZHCParser();
        List<ZHComment> list = new LinkedList<ZHComment>();
        parser.parser(aid + "", list);
        for (ZHComment zhComment : list) {
            System.out.println(zhComment.toString());
            zhComment.setQid(qid);
            zhComment.setAid(aid);
            zhComment.setCid(0);
//            service.insert(zhComment);
        }
    }
}
