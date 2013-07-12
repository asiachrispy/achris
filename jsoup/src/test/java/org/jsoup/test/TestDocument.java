package org.jsoup.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * User: zhong.huang
 * Date: 13-5-21
 */
public class TestDocument {

    @Test
    public void testGet() {
        String url = "http://www.zhihu.com/question/21081468";
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.print(doc.getElementsByTag("body"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testA() {
        try {//email=siachrispy@126.com&password=asia2013
            //email=jimgreat@126.com&password=wowo99ppqq
//            Connection.Response res = Jsoup.connect("http://www.zhihu.com/")
//                .data("email", "asiachrispy@126.com", "password", "asia2013")
//                .method(Connection.Method.POST)
//                .execute();
//
//            Document doc = res.parse();
//            System.out.println("doc1" + doc.getElementsByTag("body"));
//            String sessionId = res.cookie("SESSIONID"); // you will need to check what the right cookie name is

            Document doc2 = Jsoup.connect("http://www.zhihu.com/question/19800656")
                //.cookie("Cookie", "_xsrf=7547a995c9c840db8bf28202107a5fe6; q_c1=2a1b0bff85b445f082263baf1e4c8070|1368160566000|1368160566000; q_c0=\"MzNkNWFjZTAzNjM3MmMxOWZmNjBmYTAzM2M0NDAwMDR8UW45cFAxTHBEb2I5N3g1dQ==|1368160567|3f18ccec830bce45b68fdddf706546f38b00e118\"; __utma=51854390.1401284792.1368160395.1368160395.1368160395.1; __utmb=51854390.1.10.1368160395; __utmc=51854390; __utmz=51854390.1368158462.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)")
                .get();
            System.out.println(("doc2" + doc2.getElementsByTag("body")));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
