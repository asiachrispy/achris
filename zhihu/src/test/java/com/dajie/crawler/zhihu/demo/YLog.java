package com.dajie.crawler.zhihu.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class YLog {

    Map<String, Map<String, String>> cookieMap = new HashMap<String, Map<String, String>>();
    Map<String, String> cookie = new HashMap<String, String>();
    String cookies;

    public YLog() {
        try {
            HttpURLConnection conn = retrieveCookies("http://www.zhihu.com"); ///login?email=jimgreat@126.com&password=wowo99ppqq
            HttpURLConnection conn2 = signIn("http://www.zhihu.com");//http://www.zhihu.com/question/20909836
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String args[]) {
        new YLog();
    }

    public HttpURLConnection signIn(String urls) {
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Cookie", cookies);
            return conn;
        } catch (Exception ex) {
            return null;
        }
    }

    public HttpURLConnection retrieveCookies(String urls) {
        try {
            boolean result = false;
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            if (conn.getHeaderField("Location") != null && conn.getHeaderField("Location").indexOf("error.jsp") == -1 && conn.getResponseCode() == 302) {
                result = true;
                System.out.print(result);
            }
            cookies = conn.getHeaderField("Set-Cookie");

            if (cookies != null) {
                StringTokenizer st = new StringTokenizer(cookies, ";");
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    System.out.println(token);
                    String name = token.substring(0, token.indexOf("=") + 1).trim();
                    String value = token.substring(token.indexOf("=") + 1, token.length()).trim();
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