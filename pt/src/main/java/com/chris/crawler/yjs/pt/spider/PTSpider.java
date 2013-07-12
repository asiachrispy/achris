package com.chris.crawler.yjs.pt.spider;

import com.chris.crawler.yjs.pt.PTConstant;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class PTSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTSpider.class);

    /**
     * @param htmlUrl  页面url
     * @param encoding 页面编码
     * @return html content
     */
    public String getHtmlContent(String htmlUrl, String encoding, String proxyKV) {
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

            /*
               Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(kv[0], Integer.valueOf(kv[1])));
               if (proxy != null) {
                   httpConn = (HttpURLConnection) url.openConnection(proxy);
               } else {
                   httpConn = (HttpURLConnection) url.openConnection();
               }
            */

            // for httpConn
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(1000 * 60 * 1);
            httpConn.setReadTimeout(1000 * 60);
            httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConn.setRequestProperty("Connection", "keep-alive");
            httpConn.setRequestProperty("Host", "www.yingjiesheng.com");
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
            int code = httpConn.getResponseCode();
            if (code == 200) {
                // for inputStream
                InputStream inputStream = httpConn.getInputStream();
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
            } else {
                LOGGER.warn("getHtmlContent failed response {}", code);
            }
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (Exception e) {
            LOGGER.warn("getHtmlContent Exception:", e);
        }
        return sb.toString();
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
        Spider ptSpider = new PTSpider();
        String content = ptSpider.getHtmlContent("http://www.yingjiesheng.com/beijing/ptjob.html", "gb2312", "");
        System.out.print(content);
        List<String> links = ptSpider.getWebLinks(content);
        for (String link : links) {
            System.out.println(link);
        }
    }
}