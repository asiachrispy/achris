package com.dajie.crawler.yjs.pt.test;

import com.chris.crawler.yjs.pt.model.PTJob;
import com.chris.crawler.yjs.pt.parser.PTParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
public class TestPTParser {
    PTParser p = new PTParser();
    List<String> links = new ArrayList<String>();

    @Before
    public void before() {
        links.add("<a target=\"_blank\" href=\"/job-001-567-250.html\">[北京]托马斯国际(中国)</a>");
        links.add("<a target=\"_blank\" href=\"/job-001-567-251.html\">[上海]加进来吧加进来吧实习生 </a>");
        links.add("<a target=\"_blank\" href=\"/job-001-567-252.html\">[全国]加进来吧加进来吧实习生1 </a>");
        links.add("<a target=\"_blank\" href=\"/job-001-567-253.html\">[其它]加进来吧加进来吧实习生2 </a>");
        links.add("<a target=\"_blank\" href=\"/job-001-567-254.html\">[南昌]实习生3吧实习生aaa </a>");
        links.add("<a target=\"_blank\" href=\"/job-001-567-255.html\">[北京|上海]加进来吧加进来吧实习生4 </a>");
    }

    @Test
    public void testParserLink() {
        Map<String, PTJob> map = p.parserJob(links);
        PTJob ptJob = null;
        for (String md5Url : map.keySet()) {
            ptJob = map.get(md5Url);
            System.out.println(ptJob.getCityCN() + "-----" + ptJob.getTitle());
        }
    }
}
