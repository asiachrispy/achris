package com.dajie.crawler.yjs.pt.demo;

import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.pt.spider.PTSpider;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class TPTSpider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PTSpider.class);

    @Test
    public void testGet() {
        String strURL = "http://www.yingjiesheng.com/shanghai/ptjob.html";
        strURL = "http://www.yingjiesheng.com/beijing-moreptjob-3.html";
        StringBuilder sb = new StringBuilder();
        String line;
        String encoding = "gb2312";
        try {
            URL url = new URL(strURL);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("218.247.129.6", 80));
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(proxy);
            httpConn.setReadTimeout(5*1000);
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1)");

            InputStream inputStream = httpConn.getInputStream();
            if (StringUtil.isNotEmpty(httpConn.getContentEncoding())) {// gzip html
                inputStream = new GZIPInputStream(inputStream);
            }

            BufferedReader in = null;
            if (StringUtil.isNotEmpty(encoding)) {
                in = new BufferedReader(new InputStreamReader(inputStream, encoding));
            } else {
                in = new BufferedReader(new InputStreamReader(inputStream));
            }
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            in.close();
            System.out.println("getHtml\n" + sb.toString());
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (IOException e) {
            LOGGER.warn("getHtmlContent IOException:", e);
        }
    }

    @Test
    public void testGetHtml() {
        String strURL = "http://www.yingjiesheng.com/shanghai/ptjob.html";
        strURL = "http://www.yingjiesheng.com/beijing-moreptjob-3.html";
        try {
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            System.out.println("getContentEncoding:" + httpConn.getContentEncoding());
            InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "GB2312");//GB2312 ISO-8859-1 UTF-8
            BufferedReader bufReader = new BufferedReader(input);
            String line = "";
            StringBuilder contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line).append("\r\n");
            }
            String buf = contentBuf.toString();
//            buf = new String(buf.toString().getBytes("GB2312"), "GB2312");
//            Util.saveToLocal(buf.getBytes(), "E:\\temp\\test.html");
            System.out.println("getHtml\n" + buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String htmlUrl, String encoding) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            URL url = new URL(htmlUrl);
            BufferedReader in = null;
            if (StringUtil.isNotEmpty(encoding)) {
                in = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
            } else {
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            }
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            in.close();
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (IOException e) {
            LOGGER.warn("getHtmlContent IOException:", e);
        }
        return sb.toString();
    }

    /**
     * @return html content
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    @Test
    public void testA() throws Exception {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            URL url = new URL("http://www.yingjiesheng.com/beijing/ptjob.html");

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "ISO-8859-1"));
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            in.close();
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (IOException e) {
            LOGGER.warn("getHtmlContent IOException:", e);
        }

//        String fp = "E:\\temp\\" + Util.getFileNameByUrl(htmlUrl,"text/html");
//        Util.saveToLocal(sb.toString().getBytes(), fp);
        line = new String(sb.toString().getBytes("ISO-8859-1"), "UTF-8");
        System.out.print(line);
    }

    /**
     * @param htmlContent
     * @return �����ҳ����
     */
    public String getTitle(String htmlContent) {
        List<String> list = new ArrayList<String>();
        String regex = "<title>.*?</title>";
        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }

        StringBuilder titles = new StringBuilder();
        for (String title : list) {
            titles.append(title);
        }
        return outTag(titles.toString());
    }

    /**
     * @param htmlContents
     * @return �������
     */
    public List<String> getLinks(String htmlContents) {
        List<String> list = new ArrayList<String>();
        String regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(htmlContents);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * <a target="_blank" href="/job-001-567-052.html">[北京]泰康资产管理公司招聘办公室—档案管理实习生 </a>
     *
     * @param htmlContent
     * @return
     */
    public List<String> getLink(String htmlContent) {
        List<String> list = new ArrayList<String>();
        String regex = "<a[^>]*href=(\"([^\"]/job-*)\"|\'([^\']/job-*)\'|([^\\s>]/job-*))[^>]*>(.*?)</a>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * @param htmlContents
     * @return ��ýű�����
     */
    public List<String> getScripts(String htmlContents) {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "<script.*?</script>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(htmlContents);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * @param htmlContents
     * @return ���CSS
     */
    public List<String> getCSSs(String htmlContents) {
        List<String> list = new ArrayList<String>();
        String regex = "<style.*?</style>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(htmlContents);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * @param htmlContents
     * @return �����
     */
    public String outTag(String htmlContents) {
        return htmlContents.replaceAll("<.*?>", "");
    }

    /**
     * @param htmlContents
     * @return ��ȡ�Ż�֪ʶ�����±��⼰����
     */
    public HashMap<String, String> getFromYahoo(String htmlContents) {
        final HashMap<String, String> hm = new HashMap<String, String>();
        final StringBuffer sb = new StringBuffer();
        String html = "";
        System.out.println("\n------------------��ʼ��ȡ��ҳ(" + htmlContents + ")--------------------");
        try {
            html = get(htmlContents, "gbk");
        } catch (final Exception e) {
            e.getMessage();
        }
        // System.out.println(html);
        System.out.println("------------------��ȡ��ҳ(" + htmlContents + ")����--------------------\n");
        System.out.println("------------------����(" + htmlContents + ")�������--------------------\n");
        String title = outTag(getTitle(html));
        title = title.replaceAll("_�Ż�֪ʶ��", "");
        // Pattern pa=Pattern.compile("<div
        // class=\"original\">(.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)</div>",Pattern.DOTALL);
        final Pattern pa = Pattern.compile("<div class=\"original\">(.*?)</p></div>", Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            sb.append(ma.group());
        }
        String temp = sb.toString();
        temp = temp.replaceAll("(<br>)+?", "\n");// ת������
        temp = temp.replaceAll("<p><em>.*?</em></p>", "");// ȥͼƬע��
        hm.put("title", title);
        hm.put("original", outTag(temp));
        return hm;

    }

    /**
     * @param args ����һ����ҳ������Ż�֪ʶ��
     */
    public static void main(final String args[]) {
        String url = "";
        final List<String> list = new ArrayList<String>();
        System.out.print("����URL��һ��һ���������������� go ����ʼ����:   \n");
        /*
        * http://ks.cn.yahoo.com/question/1307121201133.html
        * http://ks.cn.yahoo.com/question/1307121101907.html
        * http://ks.cn.yahoo.com/question/1307121101907_2.html
        * http://ks.cn.yahoo.com/question/1307121101907_3.html
        * http://ks.cn.yahoo.com/question/1307121101907_4.html
        * http://ks.cn.yahoo.com/question/1307121101907_5.html
        * http://ks.cn.yahoo.com/question/1307121101907_6.html
        * http://ks.cn.yahoo.com/question/1307121101907_7.html
        * http://ks.cn.yahoo.com/question/1307121101907_8.html
        */
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!(url = br.readLine()).equals("go")) {
                list.add(url);
            }
        } catch (final Exception e) {
            e.getMessage();
        }
        final PTSpider wc = new PTSpider();
        HashMap<String, String> hm = new HashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            hm = null;// wc.getFromYahoo(list.get(i));
            System.out.println("���⣺ " + hm.get("title"));
            System.out.println("���ݣ� \n" + hm.get("original"));
        }
        /*
        * String htmlurl[] = {
        * "http://ks.cn.yahoo.com/question/1307121201133.html",
        * "http://ks.cn.yahoo.com/question/1307121101907.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_2.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_3.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_4.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_5.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_6.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_7.html",
        * "http://ks.cn.yahoo.com/question/1307121101907_8.html" }; WebContent
        * wc = new WebContent(); HashMap<String, String> hm = new HashMap<String,
        * String>(); for (int i = 0; i < htmlurl.length; i++) { hm =
        * wc.getFromYahoo(htmlurl[i]); System.out.println("���⣺ " +
        * hm.get("title")); System.out.println("���ݣ� \n" + hm.get("original")); }
        */
        /*
        * String html=""; String link=""; String sscript=""; String content="";
        * System.out.println(htmlurl+" ��ʼ��ȡ��ҳ���ݣ�");
        * html=wc.getOneHtml(htmlurl); System.out.println(htmlurl+"
        * ��ȡ��Ͽ�ʼ��������"); html=html.replaceAll("(<script.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)(</script>)","
        * ");//ȥ��ű� html=html.replaceAll("(<style.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)(</style>)","
        * ");//ȥ��CSS html=html.replaceAll("<title>.*?</title>"," ");//��ȥҳ�����
        * html=html.replaceAll("<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>","
        * ");//ȥ������ html=html.replaceAll("(\\s){2,}?"," ");//��ȥ����ո�
        * html=wc.outTag(html);//������ System.out.println(html);
        */

        /*
        * String s[]=html.split(" +"); for(int i=0;i<s.length;i++){
        * content=(content.length()>s[i].length())?content:s[i]; }
        * System.out.println(content);
        */

        // System.out.println(htmlurl+"��ҳ���ݽ���");
        /*
        * System.out.println(htmlurl+"��ҳ�ű���ʼ��"); List
        * script=wc.getScript(html); for(int i=0;i<script.size();i++){
        * System.out.println(script.get(i)); }
        * System.out.println(htmlurl+"��ҳ�ű�����");
        *
        * System.out.println(htmlurl+"CSS��ʼ��"); List css=wc.getCSS(html);
        * for(int i=0;i<css.size();i++){ System.out.println(css.get(i)); }
        * System.out.println(htmlurl+"CSS����");
        *
        * System.out.println(htmlurl+"ȫ���������ݿ�ʼ��"); List list=wc.getLink(html);
        * for(int i=0;i<list.size();i++){ link=list.get(i).toString(); }
        * System.out.println(htmlurl+"ȫ���������ݽ���");
        *
        * System.out.println("����"); System.out.println(wc.outTag(html));
        */
    }

    @Test
    public void testB() throws Exception {
        HttpClient httpClient = new HttpClient();
        String strURL = "http://www.yingjiesheng.com/shanghai/ptjob.html";
        strURL = "http://www.yingjiesheng.com/beijing-moreptjob-3.html";


//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
//        URLConnection conn = url.openConnection(proxy);


        String proxy = "proxy.mydomain.com";
        String port = "8080";
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", proxy);
        systemProperties.setProperty("http.proxyPort", port);

        GetMethod getMethod = new GetMethod(strURL);
        List headers = new ArrayList();
        headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2)"));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        httpClient.executeMethod(getMethod);

        getMethod.getResponseHeader("Content-Encoding");
        System.out.println(getMethod.getResponseCharSet());
        if (getMethod.getResponseCharSet() != null && getMethod.getResponseHeader("Content-Encoding").getValue().equals("gzip")) {
            System.out.println("gzip");
            BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(getMethod.getResponseBodyAsStream()), "GBK"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } else {
            System.out.println(getMethod.getResponseBodyAsString());
        }
    }
}