package com.dajie.crawler.zhihu.demo;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-6-13
 */
public class TestG {
    @Test
    public void testF() {
        StringBuilder sb = new StringBuilder();
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 1; i <= 350; i++) {
            ids.add(i);
        }

        for (int i = 0; i < ids.size() - 1; i++) {
            if (i % 50 > 0) {
                sb.append("-").append(ids.get(i)).append(",");
            } else if (i % 50 == 0) {
                sb.append("-").append(ids.get(i));
                System.out.println(sb.toString());
                sb.replace(0, sb.length(), "");
            }
        }
        System.out.println(sb.append("-").append(ids.get(ids.size()-1)).toString());
    }
}
