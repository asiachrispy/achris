package com.dajie.crawler.zhihu.demo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestLoging {
    public static void main(String[] args) {
        String loginUrl = "http://www.zhihu.com";
        String username = "jimgreat@126.com";
        String password = "wowo99ppqq";
        String htmlGetSource = "";
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
        //获取登录框的隐含参数 type="hidden" name="_xsrf"
        try {
            HttpGet httpGet = new HttpGet(loginUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                String str = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((str = br.readLine()) != null) {
                    htmlGetSource += str + "\n";
                }
            }
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        System.out.println(">>>>>1" + htmlGetSource);

        //应用JsoupHtml解析包解析html包含参数
        Document doc = Jsoup.parse(htmlGetSource);
        Element formContent = null;
        Element inputContent = null;
        String actionStr = "";
        String _xsrf = "";
        String checked = "on";
        formContent = doc.select("form[method=post]").first();
        inputContent = doc.select("input[type=hidden]").first();
        actionStr = loginUrl + formContent.attr("action");
        _xsrf = inputContent.attr("value");
        System.out.println(actionStr);

        HttpPost httpPost = new HttpPost(actionStr);
        HttpResponse response;
        //请求Header
        httpPost.setHeader("(Request-Line)", "POST /login HTTP/1.1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20100101 Firefox/15.0.1");
        httpPost.setHeader("Referer", "http://www.zhihu.com/");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.setHeader("Connection", "keep-alive");
        //POST参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("_xsrf", _xsrf));
        nvps.add(new BasicNameValuePair("email", username));
        nvps.add(new BasicNameValuePair("password", password));
        nvps.add(new BasicNameValuePair("rememberme", checked));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = httpClient.execute(httpPost);
            Header[] headers = response.getAllHeaders();
            for (Header h : headers) {
                System.out.println(h);
            }

            response.getStatusLine();
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();

            // for bufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            // for content
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            System.out.println(">>>>2" + sb.toString());

            // for closed
            in.close();
            inputStream.close();

            URL url = new URL(loginUrl + "/topic");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
    }


}