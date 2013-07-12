package com.chris.crawler.yjs.pt.util;

import com.chris.crawler.yjs.pt.PTConstant;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * User: zhong.huang
 * Date: 13-4-22
 */
public class Util {
    /**
     */
    public static String getFileNameByUrl(String url, String contentType) {
        url = url.substring(7);
        //text/html
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_");//+ ".html";
            return url;
        } else {
            //application/pdf
            return url.replaceAll("[\\?/:*|<>\"]", "_") + "." +
                contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    /**
     * @param htmlContents
     */
    public static String outTag(String htmlContents) {
        return htmlContents.replaceAll("<.*?>", "");
    }

    /**
     */
    public static void saveToLocal(byte[] data, String filePath) {
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

    public static String getRandomProxy() {
        Random random = new Random();
        String proxy = PTConstant.PROXY_IP[random.nextInt(PTConstant.PROXY_IP.length)];
        return proxy;
    }

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(getRandomProxy());

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(2));
        }
    }


}
