package com.chris.crawler.core.spider;

import com.chris.crawler.core.CrawlerConstant;
import com.chris.crawler.core.exception.SpiderException;
import com.dajie.common.util.StringUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class CommonSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonSpider.class);

    public CommonSpider() {
        super();
    }

    public CommonSpider(String proxyKV) {
        super(proxyKV);
    }

    @Override
    public String download(String url, String encoding) throws IOException, SpiderException {
        HttpURLConnection httpConn = getConnection(url);
        return get(httpConn, encoding);
    }

    @Override
    public String download(String url, String encoding, String cookies) throws IOException, SpiderException {
        HttpURLConnection httpConn = getConnection(url);
        //httpConn.setRequestProperty("Referer", "http://www.zhihu.com/");
//        httpConn.setRequestProperty("Host", "xjh.haitou.cc");
        httpConn.setRequestProperty("Cookie", cookies);

        return get(httpConn, encoding);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        try {
            URL rUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) rUrl.openConnection();

            httpConn.setConnectTimeout(1000 * 60 * 1);
            httpConn.setReadTimeout(1000 * 60);
            httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConn.setRequestProperty("Connection", "keep-alive");
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");

            return httpConn;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    private String get(HttpURLConnection httpConn, String encoding) throws SpiderException, IOException {
        try {
            int status = httpConn.getResponseCode();
            if (status == CrawlerConstant.CONNECTION_OK) {
                InputStream inputStream = httpConn.getInputStream();
                if (StringUtil.isNotEmpty(httpConn.getContentEncoding())) {// gzip x-gzip html
                    inputStream = new GZIPInputStream(inputStream);
                }

                BufferedReader in = null;
                if (StringUtil.isNotEmpty(encoding)) {
                    in = new BufferedReader(new InputStreamReader(inputStream, encoding));
                } else {
                    in = new BufferedReader(new InputStreamReader(inputStream));
                }

                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
                String html = sb.toString();
                in.close();
                inputStream.close();
                httpConn.disconnect();

                // for json content
                if (html.startsWith("{")) {
                    Gson gson = new Gson();
                    GsonItem item = gson.fromJson(html, GsonItem.class);
                    return item.getMsg()[1];
                }
                return html;
            } else {
                LOGGER.warn("getHtml failed with response code {}", status);
                throw new SpiderException("download url get failed response code.");
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    class GsonItem {
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
}
