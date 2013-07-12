package com.dajie.crawler.zhihu.demo;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.*;

public class TestLogin {
    String surl = "http://www.zhihu.com/login";
    String paremeter = "email=jimgreat@126.com&password=wowo99ppqq";

    @Before
    public void before() {
        paremeter = paremeter + "&_xsrf=" + new My(surl).cookie.get("_xsrf");
    }

    @Test
    public void testPost() throws IOException {
        String surl = "http://www.zhihu.com";
        String email = "email=" + URLEncoder.encode("jimgreat@126.com", "UTF-8");
        String password = "password=" + URLEncoder.encode("wowo99ppqq", "UTF-8");
        String _xsrf = "_xsrf=" + URLEncoder.encode("33c97c30f1fb4189bd50347a43e14191", "UTF-8");
        String paremeter = email + "&" + password + "&" + _xsrf;

        URL url = new URL(surl);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1)");
        connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.setRequestProperty("Referer", "https://www.zhihu.com/");
        connection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Authorization", paremeter);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        String[] kv = PTConstant.DEFAULT_PROXY_IP.split(":");
        System.setProperty("http.proxyHost", kv[0]);
        System.setProperty("http.proxyPort", kv[1]);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        out.write("email=jimgreat@126.com&password=wowo99ppqq&_xsrf=33c97c30f1fb4189bd50347a43e14191");
        out.flush();
        out.close();

        String line = "";
        StringBuffer sb = new StringBuffer();
        InputStream urlStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        System.out.println(sb.toString());
    }


    @Test
    public void testB() throws MalformedURLException, UnsupportedEncodingException {
//        String email = "email=" + URLEncoder.encode("jimgreat@126.com", "UTF-8");
//        String password = "password=" + URLEncoder.encode("wowo99ppqq", "UTF-8");
//        String _xsrf = "_xsrf=" + URLEncoder.encode("94cd5a6c5cf24e16a92901f6403c0178", "UTF-8");
//        String parameters = email + "&" + password + "&" + _xsrf;


        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL("http://www.zhihu.com/login");
            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "zh-cn");
//            connection.setRequestProperty("Request-Line", "POST /login HTTP/1.1");
            connection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1)");
//            connection.setRequestProperty("Authorization", parameters);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            wr.write(this.paremeter);
            wr.flush();
            wr.close();

            int rc = connection.getResponseCode();
            System.out.println("code = " + rc); // Always 200

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }
            rd.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Test
    public void testC() throws Exception {
        String agent = "Mozilla/4.0";
        String type = "application/x-www-form-urlencoded";
        URL url = new URL(surl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        String encodedData = URLEncoder.encode(paremeter, "UTF-8"); // user-supplied

        try {
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", agent);
            urlConnection.setRequestProperty("Content-Type", type);
            urlConnection.setRequestProperty("Content-Length", Integer.toString(encodedData.length()));

            OutputStream os = urlConnection.getOutputStream();
            os.write(encodedData.getBytes());

            int rc = urlConnection.getResponseCode();
            System.out.println(rc); // Always 200

            BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // handle the error here
        }
    }
}