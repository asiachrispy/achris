package com.dajie.crawler.zhihu.demo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class LoginTest2 {

    private static CookieStore cs = new BasicCookieStore();
    public static String login = "https://www.zhihu.com/login";
    public static String params = "email:jimgreat@126.com&password:wowo99ppqq";

    @Test
    public void test1() throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        // 创建一个本地上下文信息
        HttpContext localContext = new BasicHttpContext();
        // 在本地上下问中绑定一个本地存储
        localContext.setAttribute(ClientContext.COOKIE_STORE, cs);

        // 目标地址
        HttpPost httppost = new HttpPost(login);
        System.out.println("请求: " + httppost.getRequestLine());
        // 构造最简单的字符串数据
        StringEntity reqEntity = new StringEntity(params);
        // 设置类型
//        reqEntity.setContentType("application/x-www-form-urlencoded");
        // 设置请求的数据
        httppost.setEntity(reqEntity);
        // 执行
        HttpResponse response = httpclient.execute(httppost);

        Header[] headers = response.getAllHeaders();
        for (Header h : headers) {
            String name = h.getName();
            String value = h.getValue();
            System.out.println("header : " + h.getName() + ":" + h.getValue());

            if ("Set-Cookie".equalsIgnoreCase(name)) {
                String[] strs = value.split(";");
                for (String str : strs) {
                    String[] cookies = str.split("=");
                    System.out.println("=============== : " + cookies[0] + ":"
                            + cookies[1]);
                    cs.addCookie(new BasicClientCookie(cookies[0], cookies[1]));
                }
            }

        }

        HttpEntity entity = response.getEntity();
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: "
                    + entity.getContentLength());

        }

        // 显示结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        opera();
    }

    private void opera() throws Exception {

        System.out.println("----------------------------------------");
        System.out.println("----------------------------------------");

        String logPath = "http://www.zhihu.com/question/20909836";

        DefaultHttpClient httpclient = new DefaultHttpClient();

        String cookieStr = "";
        List<Cookie> list = cs.getCookies();
        for (Cookie cookie : list) {
            cookieStr += cookie.getName() + "=" + cookie.getValue() + ";";
        }

        // 目标地址
        HttpGet httpget = new HttpGet(logPath);
        httpget.setHeader("Cookie", cookieStr);

        System.out.println("请求: " + httpget.getRequestLine());
        // 设置类型
        // 执行
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

    }
}