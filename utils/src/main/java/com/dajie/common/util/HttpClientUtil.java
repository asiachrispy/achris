package com.dajie.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: haluo
 * Date: Jan 19, 2010
 * Time: 4:23:17 PM
 */
public class HttpClientUtil {
    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    public static final String GET = "get";
    public static final String POST = "post";


    /**
     * 获取url链接的html内容,默认连接超时2秒和socket超时30秒
     *
     * @param url 地址
     * @return html 内容
     */
    public static String getResponse(String url) {
        return response(url, null, null, GET);
    }

    public static String getResponse(String url, int timeoutMilliseconds) {
        return response(url, null, null, GET, timeoutMilliseconds);
    }

    public static String postResponse(String url, Map<String, String> parameters, Cookie[] cookies) {
        return response(url, parameters, cookies, POST);
    }

    public static String postResponse(String url, Map<String, String> parameters, Cookie[] cookies, int timeoutMilliseconds) {
        return response(url, parameters, cookies, POST, timeoutMilliseconds);
    }

    public static String response(String url, Map<String, String> parameters, Cookie[] cookies, String type) {
        return response(url, parameters, cookies, type, 8000);
    }

    public static String response(String url, Map<String, String> parameters, Cookie[] cookies, String type, int timeoutMilliseconds) {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeoutMilliseconds);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeoutMilliseconds);
        HttpContext localContext = null;
        if (cookies != null) {
            CookieStore cookieStore = new BasicCookieStore();
            localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            for (Cookie cookie : cookies) {
                BasicClientCookie clientCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                clientCookie.setVersion(1);
                clientCookie.setDomain(cookie.getDomain() == null ? url.substring(7, url.indexOf("/", 7)) : cookie.getDomain());
                clientCookie.setPath(cookie.getPath() == null ? "/" : cookie.getPath());
                clientCookie.setExpiryDate(new Date(cookie.getMaxAge() < 0 ? (System.currentTimeMillis() + 60 * 60 * 1000) : (System.currentTimeMillis() + cookie.getMaxAge() * 1000)));
                cookieStore.addCookie(clientCookie);
            }
        }

        HttpUriRequest method;
        if (type.equals(GET)) {
            method = new HttpGet(url);
            if (parameters != null) {
                StringBuilder sb = new StringBuilder();
                for (String k : parameters.keySet()) {
                    sb.append("&").append(k).append("=").append(parameters.get(k));
                }
                if (url.indexOf("?") > 0) {
                    url += sb.toString();
                } else {
                    url += "?";
                    url += sb.deleteCharAt(0).toString();
                }
            }
        } else {
            method = new HttpPost(url);
            if (parameters != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (String k : parameters.keySet()) {
                    nvps.add(new BasicNameValuePair(k, parameters.get(k)));
                }
                try {
                    ((HttpPost) method).setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                } catch (UnsupportedEncodingException e) {
                    log.warn("response:" + url + " error " + e.getMessage());
                }
            }
        }

        method.setHeader("Connection", "close");

        StringBuilder sb = new StringBuilder();
        try {
            HttpResponse response;
            if (localContext != null) {
                response = httpclient.execute(method, localContext);
            } else {
                response = httpclient.execute(method);
            }
            HttpEntity entity = response.getEntity();
            String contentType = entity.getContentType().getValue();
            String charset = contentType.replaceAll("([^;]*);\\s*charset=", "");
            try {
                if (!Charset.isSupported(charset)) {
                    charset = "UTF-8";
                    log.warn("charset not supported:" + charset + ";url=" + url);
                }
            } catch (Exception e) {
                charset = "UTF-8";
            }
            InputStream in = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s).append("\r\n");
            }
        } catch (Exception e) {
            System.err.println("response:" + url + " error ");
            e.printStackTrace();
        }

        return sb.toString();
    }
}
