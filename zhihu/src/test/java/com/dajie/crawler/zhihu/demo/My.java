package com.dajie.crawler.zhihu.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * User: zhong.huang
 * Date: 13-5-9
 */
public class My {
    public static Map<String, Map<String, String>> cookieMap = new HashMap<String, Map<String, String>>();
    public static Map<String, String> cookie = new HashMap<String, String>();
    public static String cookies;

    public My(String url) {
        retrieveCookies(url);
    }

    public static HttpURLConnection retrieveCookies(String urls) {
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            cookies = conn.getHeaderField("Set-Cookie");
            if (cookies != null) {
                StringTokenizer st = new StringTokenizer(cookies, ";");
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    System.out.println(token);
                    String name = token.substring(0, token.indexOf("=")).trim();
                    String value = token.substring(token.indexOf("="), token.length()).trim();
                    cookie.put(name, value);
                    cookieMap.put(name, cookie);
                }
            }
            return conn;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
}
