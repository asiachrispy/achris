package com.chris.crawler.haitou.parser;

import com.chris.crawler.haitou.HTConstant;
import com.chris.crawler.haitou.model.HTxjh;
import com.chris.crawler.haitou.util.DictUtil;
import com.dajie.common.util.DateUtil;
import com.dajie.common.util.StringUtil;
import com.dajie.crawler.core.CrawlerConstant;
import com.dajie.crawler.core.exception.SpiderException;
import com.dajie.crawler.core.spider.CommonSpider;
import com.dajie.crawler.core.spider.Spider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */
public class HTParser {

    private static Spider SPIDER = new CommonSpider(HTConstant.getProxy()).useProxy(true);
    private static String HOME = "http://xjh.haitou.cc/#/";
    private static String URL = "http://xjh.haitou.cc";

    public HTParser() {
    }

    public List<HTxjh> getArticle(String city) {
        List<HTxjh> articles = new ArrayList<HTxjh>();
        try {
            String v = SPIDER.download(HOME.replace("#", city), CrawlerConstant.ENCODING_UTF8);
            Elements trs = Jsoup.parse(v).getElementById("infoTable").getElementsByTag("tr");
            trs.remove(0);// remove first tr of table
            HTxjh ht = null;
            for (Element element : trs) {
                if (element.getElementsByTag("a").size() > 0) {
                    ht = new HTxjh();
                    ht.setCity(DictUtil.getInstance().getCity().get(city));// todo
                    parserTR(element, ht, city);
                    articles.add(ht);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpiderException e) {
            e.printStackTrace();
        }
        return articles;
    }

    private void parserTR(Element src, HTxjh ht, String city) throws IOException, SpiderException {
        if (src == null) {
            return;
        }

        Elements elements = src.getElementsByTag("td");
        elements.remove(0);// remove first td
        Iterator<Element> its = elements.iterator();

        Element e = its.next();
        String url = URL + e.getElementsByTag("a").first().attr("href");
        String content = SPIDER.download(url, CrawlerConstant.ENCODING_UTF8);
        content = Jsoup.parse(content).getElementsByAttributeValue("class", "sub_con").first().html();

        String title = e.getElementsByTag("a").text();
        Elements fonts = e.getElementsByTag("font");
        String school = fonts.first().text();
        int count = 0;
        if (fonts.size() == 2) {
            count = Integer.valueOf(fonts.last().text().replaceAll("[^0-9]", ""));
        }

        String schedu_date = its.next().text();
        String address = its.next().text();
        String pub_date = its.next().text();

        ht.setUrl(url);
        ht.setUrlMd5(StringUtil.md5(url));
        ht.setTitle(title);
        ht.setContent(content);
        Integer sch = DictUtil.getInstance().getSchool().get(city).get(school);
        if (sch == null) {
            throw new SpiderException(String.format("dict of city %d's school %s is null", city, school));
        }
        ht.setSchool(sch);
        ht.setUpdateCount(count);
        ht.setScheduleDate(DateUtil.parseDate(schedu_date, "yyyy-MM-dd HH:mm"));
        ht.setAddress(address);
        ht.setPublishDate(DateUtil.parseDate(pub_date, "yyyy-MM-dd HH:mm"));
        ht.setCreateDate(new Date());
    }

    public static void main(String[] args) {
        HTParser g = new HTParser();
        g.getArticle("gz");
    }
}
