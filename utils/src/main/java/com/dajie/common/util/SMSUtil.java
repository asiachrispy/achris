package com.dajie.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 发送短信工具类.
 * User: zhong.huang
 * Date: 12-10-17
 * Time: 下午4:28
 */
public class SMSUtil {
    private final static Log LOGGER = LogFactory.getLog(SMSUtil.class);
    public static final String HTTP_EXCEPTION = "-4";
    public static final String INVALID_PARAMETER = "-1";
    public static final int TIMEOUT_MILLISECONDS = 5000;
    public static final String CHARSET_ENCODE = "GB2312";

    /**
     * 发送短信接口，3个参数都是必需且不能为空的.
     * 返回值大于0，则表示成功，小于0或者其他值表示失败.
     * @param type  required
     * @param mobile  required
     * @param msg   required
     * @return  if success return > 0 ,otherwise return <=0
     */
    public static String sendSMS(String type, String mobile, String msg) {
        if (StringUtil.isEmpty(type) || StringUtil.isEmpty(msg) || StringUtil.isEmpty(mobile)) {
            LOGGER.warn("invalid parameter.. ");
            return INVALID_PARAMETER;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("http://www.dajie.com/sms/internal/mt?type=").append(type);
        sb.append("&msg=").append(UrlEncoderUtil.encode(msg, CHARSET_ENCODE, CHARSET_ENCODE));
        sb.append("&mobile=").append(mobile);

        return getResponse(sb.toString(), TIMEOUT_MILLISECONDS);
    }

    private static String getResponse(String url, int timeoutMilliseconds) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeoutMilliseconds);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeoutMilliseconds);

            HttpUriRequest method = new HttpGet(url);
            HttpResponse response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, CHARSET_ENCODE));
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
        } catch (Exception e) {
            LOGGER.error("response {" + url + "} error ");
            e.printStackTrace();
            return HTTP_EXCEPTION;
        }

        return sb.toString();
    }
}
