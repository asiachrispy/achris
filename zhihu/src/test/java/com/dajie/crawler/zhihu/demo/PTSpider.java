package com.dajie.crawler.zhihu.demo;

import com.dajie.common.config.AppConfigs;
import com.dajie.common.util.StringUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class PTSpider extends Spider {
    class Item {
        private int r;
        private String[] msg;

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public String[] getMsg() {
            return msg;
        }

        public void setMsg(String[] msg) {
            this.msg = msg;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PTSpider.class);

    /**
     * @param htmlUrl  页面url
     * @param encoding 页面编码
     * @return html content
     */
    public String getHtmlContent(String htmlUrl, String encoding, String proxyKV) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            URL url = new URL(htmlUrl);
            // for proxy
            if (StringUtil.isNotEmpty(proxyKV) && proxyKV.contains(":")) {
                String[] kv = proxyKV.split(":");
                System.setProperty("http.proxyHost", kv[0]);
                System.setProperty("http.proxyPort", kv[1]);
            } else { // for default proxy
                String value = AppConfigs.getInstance().getConfigs().get(PTConstant.CONFIG_KEY_PROXY_IP);//
                if (StringUtil.isNotEmpty(value) && value.contains(":")) {
                    String[] kv = value.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                } else {
                    String[] kv = PTConstant.DEFAULT_PROXY_IP.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                }
            }


            // for httpConn
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
            httpConn.setRequestProperty("Cookie", "_xsrf=7547a995c9c840db8bf28202107a5fe6; q_c1=2a1b0bff85b445f082263baf1e4c8070|1368160566000|1368160566000; q_c0=\"MzNkNWFjZTAzNjM3MmMxOWZmNjBmYTAzM2M0NDAwMDR8UW45cFAxTHBEb2I5N3g1dQ==|1368160567|3f18ccec830bce45b68fdddf706546f38b00e118\"; __utma=51854390.1401284792.1368160395.1368160395.1368160395.1; __utmb=51854390.1.10.1368160395; __utmc=51854390; __utmz=51854390.1368158462.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
            /**
             * 把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
             * 通过把URLConnection设为输出，你可以把数据向你个Web页传送
             */
            httpConn.setDoOutput(true);
            /**
             * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
             */
            OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
            //out.write("email=jimgreat@126.com&password=wowo99ppqq"); //post的关键所在！
            // remember to clean up
            out.flush();
            out.close();


            int rc = httpConn.getResponseCode();
            System.out.println("code = " + rc); // Always 200

            // for inputStream
            InputStream inputStream = httpConn.getInputStream();
            System.out.println("ContentEncoding=" + httpConn.getContentEncoding());
            if (StringUtil.isNotEmpty(httpConn.getContentEncoding())) {// gzip html
                inputStream = new GZIPInputStream(inputStream);
            }

            // for bufferedReader
            BufferedReader in = null;
            if (StringUtil.isNotEmpty(encoding)) {
                in = new BufferedReader(new InputStreamReader(inputStream, encoding));
            } else {
                in = new BufferedReader(new InputStreamReader(inputStream));
            }

            // for content
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }

            // for closed
            in.close();
            inputStream.close();
            httpConn.disconnect();
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (Exception e) {
            LOGGER.warn("getHtmlContent Exception:", e);
        }
        Gson gson = new Gson();
        Item item = gson.fromJson(sb.toString(), Item.class);


        return item.getMsg()[1];
    }

    public String get(String htmlUrl, String encoding, String proxyKV) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            URL url = new URL(htmlUrl);
            // for proxy
            if (StringUtil.isNotEmpty(proxyKV) && proxyKV.contains(":")) {
                String[] kv = proxyKV.split(":");
                System.setProperty("http.proxyHost", kv[0]);
                System.setProperty("http.proxyPort", kv[1]);
            } else { // for default proxy
                String value = AppConfigs.getInstance().getConfigs().get(PTConstant.CONFIG_KEY_PROXY_IP);//
                if (StringUtil.isNotEmpty(value) && value.contains(":")) {
                    String[] kv = value.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                } else {
                    String[] kv = PTConstant.DEFAULT_PROXY_IP.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                }
            }


            // for httpConn
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
            httpConn.setRequestProperty("Cookie", "_xsrf=7547a995c9c840db8bf28202107a5fe6; q_c1=2a1b0bff85b445f082263baf1e4c8070|1368160566000|1368160566000; q_c0=\"MzNkNWFjZTAzNjM3MmMxOWZmNjBmYTAzM2M0NDAwMDR8UW45cFAxTHBEb2I5N3g1dQ==|1368160567|3f18ccec830bce45b68fdddf706546f38b00e118\"; __utma=51854390.1401284792.1368160395.1368160395.1368160395.1; __utmb=51854390.1.10.1368160395; __utmc=51854390; __utmz=51854390.1368158462.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");

            int rc = httpConn.getResponseCode();
            System.out.println("code = " + rc); // Always 200

            // for inputStream
            InputStream inputStream = httpConn.getInputStream();

            // for bufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, encoding));
            // for content
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }

            // for closed
            in.close();
            inputStream.close();
            httpConn.disconnect();
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (Exception e) {
            LOGGER.warn("getHtmlContent Exception:", e);
        }
        if (sb.toString().startsWith("{")) {
        Gson gson = new Gson();
        Item item = gson.fromJson(sb.toString(), Item.class);
        return item.getMsg()[1];
        } else {
            return sb.toString();
        }
    }

    public boolean sing(String htmlUrl, String proxyKV) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            URL url = new URL(htmlUrl);
            // for proxy
            if (StringUtil.isNotEmpty(proxyKV) && proxyKV.contains(":")) {
                String[] kv = proxyKV.split(":");
                System.setProperty("http.proxyHost", kv[0]);
                System.setProperty("http.proxyPort", kv[1]);
            } else { // for default proxy
                String value = AppConfigs.getInstance().getConfigs().get(PTConstant.CONFIG_KEY_PROXY_IP);//
                if (StringUtil.isNotEmpty(value) && value.contains(":")) {
                    String[] kv = value.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                } else {
                    String[] kv = PTConstant.DEFAULT_PROXY_IP.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                }
            }

            // for httpConn
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
//            httpConn.setRequestProperty("Cookie", "_xsrf=7547a995c9c840db8bf28202107a5fe6; ");
            /**
             * 把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
             * 通过把URLConnection设为输出，你可以把数据向你个Web页传送
             */
            httpConn.setDoOutput(true);
            /**
             * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
             */
            OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
            //out.write("email=jimgreat@126.com&password=wowo99ppqq"); //post的关键所在！
            // //post的关键所在！
            // remember to clean up
            out.flush();
            out.close();

            int rc = httpConn.getResponseCode();
            System.out.println("code = " + rc); // Always 200
            return rc > 0 ? true : false;
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (Exception e) {
            LOGGER.warn("getHtmlContent Exception:", e);
        }
        return false;
    }

    /**
     * <a target="_blank" href="/job-001-567-052.html">[北京]泰康资产管理公司招聘办公室—档案管理实习生 </a>
     *
     * @param htmlContent
     * @return
     */
    public List<String> getLinks(String htmlContent, String key) {
        List<String> list = new ArrayList<String>();
        String regex = "<a[^>]*href=(\"([^\"]key)\"|\'([^\']key)\'|([^\\s>]key))[^>]*>(.*?)</a>";
        regex = regex.replace(key, key);
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
    public static void main(String[] args) throws Exception {
        PTSpider ptSpider = new PTSpider();
//        String content = ptSpider.getHtmlContent("http://www.zhihu.com/log/questions?start=59071419&offset=20", "utf-8", "");

        //boolean sing = ptSpider.sing("http://www.zhihu.com", "");
        //System.out.println(">>>> sing " + sing);
        String content = ptSpider.get("http://www.zhihu.com/log/questions?start=59058872&offset=20", "UTF-8","");
        System.out.println(content.contains("WalframApha"));

        content = ptSpider.get("http://www.zhihu.com/log/questions?start=0&offset=20", "UTF-8","");
        System.out.println(content.contains("如果孙维不是真凶"));
//        List<String> links = ptSpider.getLinks(content, "/question/*");
//        for (String link : links) {
//            System.out.println(">>>>" + link);
//        }
    }
}