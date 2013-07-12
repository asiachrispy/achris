package com.chris.crawler.zhihu.spider;

import com.chris.crawler.zhihu.ZHConstant;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.util.StringUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZHSpider.class);

    public ZHSpider() {
        super();
    }

    public ZHSpider(String proxyKV) {
        super(proxyKV);
        initProxy();
    }

    @Override
    public String getHtml(String url, String encoding) throws MalformedURLException, Exception {
        String html = "";
        try {
            URL rUrl = new URL(url);
            LOGGER.debug("open url");
            HttpURLConnection httpConn = (HttpURLConnection) rUrl.openConnection();
            httpConn.setConnectTimeout(1000 * 60 * 1);
            httpConn.setReadTimeout(1000 * 60);
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
            int status = httpConn.getResponseCode();
            if (status == ZHConstant.CONNECTION_OK) {
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
                html = sb.toString();
                in.close();
                inputStream.close();
            } else {
                LOGGER.warn("getHtml failed with response code {}", status);
            }
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtml MalformedURLException:", me);
            throw new MalformedURLException();
        } catch (Exception e) {
            LOGGER.warn("getHtml Exception:", e);
        }
        return html;
    }

    @Override
    public String getHtmlWithLogin(String url, String encoding) throws MalformedURLException, Exception {
        String html = "";
        try {
            URL rUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) rUrl.openConnection();
            httpConn.setConnectTimeout(1000 * 60 * 1);
            httpConn.setReadTimeout(1000 * 60);
            httpConn.setRequestProperty("Referer", "http://www.zhihu.com/");
            httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConn.setRequestProperty("Connection", "keep-alive");
            httpConn.setRequestProperty("Host", "www.zhihu.com");
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
            httpConn.setRequestProperty("Cookie", AppConfigs.getInstance().get(ZHConstant.COOKIE));
            int status = httpConn.getResponseCode();
            if (status == ZHConstant.CONNECTION_OK) {
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
                html = sb.toString();
                in.close();
                inputStream.close();
                httpConn.disconnect();

                if (html.startsWith("{")) {
                    Gson gson = new Gson();
                    GsonItem item = gson.fromJson(html, GsonItem.class);
                    return item.getMsg()[1];
                }
            } else {
                LOGGER.warn("getHtml failed with response code {}", status);
            }
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtml MalformedURLException:", me);
            throw new MalformedURLException();
        } catch (Exception e) {
            LOGGER.warn("getHtml Exception:", e);
        }
        return html;
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
