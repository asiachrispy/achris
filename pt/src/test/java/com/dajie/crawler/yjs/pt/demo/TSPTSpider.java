package com.dajie.crawler.yjs.pt.demo;

import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.pt.spider.PTSpider;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class TSPTSpider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTSpider.class);

    /**
     * @param htmlUrl
     * @return html content
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    public String getHtmlContent(String htmlUrl, String encoding) {
        String ret = "";
        try {
            HttpClient client = new DefaultHttpClient();

            HttpGet httpget = new HttpGet(htmlUrl);
            httpget.addHeader("Content-Type", "text/html; charset=gb2312");
//            httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1)");

//            httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//            httpget.setHeader("Accept-Charset", "gbk");

            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            httpget.abort();
            client.getConnectionManager().shutdown();
            /// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
            ret = EntityUtils.toString(entity);

            ret = new String(ret.getBytes("ISO-8859-1"), encoding);

        } catch (Exception e) {
            LOGGER.warn("getHtmlContent IOException:", e);
        } finally {
        }
        return ret;
    }

    /**
     * <a target="_blank" href="/job-001-567-052.html">[北京]泰康资产管理公司招聘办公室—档案管理实习生 </a>
     *
     * @param htmlContent
     * @return
     */
    public List<String> getWebLinks(String htmlContent) {
        List<String> list = new ArrayList<String>();
        String regex = "<a[^>]*href=(\"([^\"]/job-*)\"|\'([^\']/job-*)\'|([^\\s>]/job-*))[^>]*>(.*?)</a>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /*
     * http://www.yingjiesheng.com/beijing-moreptjob-3.html
     * http://www.yingjiesheng.com/beijing/ptjob.html
     */
    public static void main(String[] args) {
        TSPTSpider ptSpider = new TSPTSpider();
        String url = "http://www.yingjiesheng.com/beijing/ptjob.html";
        url = "http://www.yingjiesheng.com/beijing-moreptjob-3.html";
        String content = ptSpider.getHtmlContent(url, "gb2312");
        System.out.print(content);
        List<String> links = ptSpider.getWebLinks(content);
        for (String link : links) {
            System.out.println(link);
        }
    }

    @Test
    public void testGetHtml() {
        String strURL = "http://www.yingjiesheng.com/shanghai/ptjob.html";
//        strURL = "http://www.yingjiesheng.com/beijing-moreptjob-3.html";
        try {
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            String encoding = httpConn.getContentEncoding();
            System.out.println("getContentEncoding:" + encoding);
            InputStream inputStream = httpConn.getInputStream();
            if (StringUtil.isNotEmpty(encoding)) {
                inputStream = new GZIPInputStream(inputStream);
            }
            InputStreamReader input = new InputStreamReader(inputStream, "GB2312");//GB2312 ISO-8859-1 UTF-8
            BufferedReader bufReader = new BufferedReader(input);
            String line = "";
            StringBuilder contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line).append("\r\n");
            }
            String buf = contentBuf.toString();
            System.out.println(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}