package com.dajie.crawler.yjs.pt.demo;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownLoadFile {
    /**
     */
    public String getFileNameByUrl(String url, String contentType) {
        //移除http:
        url = url.substring(7);
        //text/html 类型
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        }
        //如application/pdf 类型
        else {
            return url.replaceAll("[\\?/:*|<>\"]", "_") + "." +
                contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    /**
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
            for (int i = 0; i < data.length; i++) {
                out.write(data[i]);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(String url) {
        String filePath = null;
        // 1.生成HttpClinet 对象并设置参数
        HttpClient httpClient = new HttpClient();
        // 设置HTTP 连接超时5s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        // 2.生成GetMethod 对象并设置参数
        GetMethod getMethod = new GetMethod(url);
        // 设置get 请求超时5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        // 3.执行HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
                filePath = null;
            }

            byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
            filePath = "E:\\temp\\" + getFileNameByUrl(url, getMethod.getResponseHeader("Content-Type").getValue());
            saveToLocal(responseBody, filePath);
        } catch (HttpException e) {
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return filePath;
    }

    public static void main(final String args[]) {
        DownLoadFile d = new DownLoadFile();
        d.downloadFile("http://www.yingjiesheng.com/beijing/ptjob.html");
    }

}