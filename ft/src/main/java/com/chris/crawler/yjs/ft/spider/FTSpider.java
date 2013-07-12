package com.chris.crawler.yjs.ft.spider;

import com.chris.crawler.yjs.ft.FTConstant;
import com.chris.crawler.yjs.ft.util.Util;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class FTSpider extends Spider {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTSpider.class);

    /**
     * @param htmlUrl  页面url
     * @param encoding 页面编码
     * @return html content
     */
    @Override
    public String getHtmlContent(String htmlUrl, String encoding, String proxyKV) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            URL url = new URL(htmlUrl);
            // for proxy
            if (StringUtil.isNotEmpty(proxyKV) && proxyKV.contains(":")) {
                String[] kv = proxyKV.split(":");
                System.setProperty("http.proxyHost", kv[0]);
                System.setProperty("http.proxyPort", kv[1]);
            } else { // for default proxy
                String value = AppConfigs.getInstance().getConfigs().get(FTConstant.CONFIG_PROXY_IP);//
                if (StringUtil.isNotEmpty(value) && value.contains(":")) {
                    String[] kv = value.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                } else {
                    String[] kv = FTConstant.DEFAULT_PROXY_IP.split(":");
                    System.setProperty("http.proxyHost", kv[0]);
                    System.setProperty("http.proxyPort", kv[1]);
                }
            }

            // for httpConn
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setReadTimeout(1000*60*1);
            httpConn.setConnectTimeout(1000*60*1);
            httpConn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1)");

            // for inputStream
            InputStream inputStream = httpConn.getInputStream();
            if (StringUtil.isNotEmpty(httpConn.getContentEncoding())) {// gzip html
                inputStream = new GZIPInputStream(inputStream);
            }

            // for bufferedReader
            BufferedReader in = null;
            if (StringUtil.isNotEmpty(encoding)) {
                in = new BufferedReader(new InputStreamReader(inputStream, encoding));
            } else {
                in = new BufferedReader(new InputStreamReader(inputStream));
            }

            // for content
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\r\n");
            }

            // for closed
            in.close();
            inputStream.close();
            httpConn.disconnect();
        } catch (MalformedURLException me) {
            LOGGER.warn("getHtmlContent MalformedURLException:", me);
        } catch (Exception e) {
            LOGGER.warn("getHtmlContent Exception:", e);
        }
        return sb.toString();
    }

    /**
     * <a target="_blank" href="/job-001-567-052.html">[北京]泰康资产管理公司招聘办公室—档案管理实习生 </a>
     *
     * @param htmlContent
     * @return
     */
    public List<String> getLinks(String htmlContent, String prefix) {
        List<String> list = new ArrayList<String>();
        String regex = "<a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a>";
        regex = regex.replace("#", prefix);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }


    /**
     * <div class="relHotJob">
     * <strong>职位专业分类：</strong>
     * <a target="_blank" href="/major/wenke/">文史哲政</a>
     * <a target="_blank" href="/major/waiyu/">外语</a>
     * <a target="_blank" href="/major/meiti/">媒体广告影视</a>
     * <a target="_blank" href="/major/jinrong/">经济金融</a>
     * <a target="_blank" href="/major/jisuanji/">计算机电子</a>
     * <a target="_blank" href="/major/falv/">法律</a>
     * <a target="_blank" href="/major/caikuai/">财务会计</a>
     * </div>
     *
     * @param htmlContent
     * @return
     */
    public List<String> getMajor(String htmlContent) {
        // for div
        String prefix = "relHotJob";
        List<String> list = new ArrayList<String>();
        String regex = "<div[^>]*class=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</div>";
        regex = regex.replace("#", prefix);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }

        // for href
        if (list != null && list.size() > 0) {
            String div = list.get(0);
            list = getLinks(div, "/major/");
        }
        return list;
    }

    @Deprecated
    public String getContent(String htmlContent) {
        // for div
        String prefix = "jobIntro";
        List<String> list = new ArrayList<String>();
        String regex = "<div[^>]*class=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</div>";
        regex = regex.replace("#", prefix);
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }

        String content = "";
        if (list != null && list.size() > 0) {
            content = list.get(0);
        }

        // for multi div
        if (content.lastIndexOf("<div") > 0) {
            content = content.substring(0, content.indexOf(">") + 1);
            int start = htmlContent.indexOf(content);

            regex = "<p><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
            regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
            pa = Pattern.compile(regex, Pattern.DOTALL);
            ma = pa.matcher(htmlContent);
            list.clear();
            while (ma.find()) {
                list.add(ma.group());
            }

            if (list.size() > 0) {
                int end = htmlContent.indexOf(list.get(list.size() - 1)) + list.get(list.size() - 1).length();
                content = htmlContent.substring(start, end) + "<div>";
            }
        }
        return content;
    }


    public String getJobContent(String htmlContent) {
        String content = "";
        String prefix = "<div class=\"jobIntro\">";
        int start = htmlContent.indexOf(prefix);

        String regex = "<p[^>]*><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
        regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(htmlContent);
        List<String> list = new ArrayList<String>();
        while (ma.find()) {
            list.add(ma.group());
        }
        if (list.size() > 0) {
            int end = htmlContent.indexOf(list.get(list.size() - 1)) + list.get(list.size() - 1).length();
            content = htmlContent.substring(start, end) + "<div>";
        } else {

        }
        return content;
    }

    /**
     * <h1>[山西]中国科学院山西煤炭化学研究所2013年度科研人才招聘</h1>
     * <p/>
     * <title>[山西]中国科学院山西煤炭化学研究所2013年度科研人才招聘_最新校园招聘职位信息</title>
     *
     * @param htmlContent
     * @return
     */
    @Override
    public String getTitle(String htmlContent) {
        String title = "";
        List<String> list = Util.getTags(htmlContent, "<h1", "</h1>");

        if (list.size() > 0) {
            title = list.get(0);
        } else {
            list = Util.getTags(htmlContent, "<title", "</title>");
            if (list.size() > 0) {
                title = list.get(0);
            }
        }

        return title;
    }

    /**
     * <h1>[山西]中国科学院山西煤炭化学研究所2013年度科研人才招聘</h1>
     * <p/>
     * <title>[山西]中国科学院山西煤炭化学研究所2013年度科研人才招聘_最新校园招聘职位信息</title>
     *
     * @param htmlContent
     * @return

     @Override public String getTitle(String htmlContent) {
     String title = "";
     List<String> list = new ArrayList<String>();
     String regex = "<title*>(.*?)</title>";
     Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
     Matcher ma = pa.matcher(htmlContent);
     while (ma.find()) {
     list.add(ma.group());
     }

     if (list.size() > 0) {
     title = list.get(0);
     } else {
     regex = "<h1*>(.*?)</h1>";
     pa = Pattern.compile(regex, Pattern.DOTALL);
     ma = pa.matcher(htmlContent);
     while (ma.find()) {
     list.add(ma.group());
     }

     if (list.size() > 0) {
     title = list.get(0);
     }
     }

     return title;
     }  */

    /**
     * <p style="text-align: center;">
     * <a target="_blank" href="http://my.yingjiesheng.com/index.php/personal/applyjob.htm/?jobid=1567141&appcomid=514&applnk=aHR0cHM6Ly9qb2IuaWNiYy5jb20uY24vSUNCQ0R5bmFtaWNTaXRlMi9FbXBsb3kvQWZmaUxpc3QuYXNweD9BZmZpVHlwZT0xJlBsYW5JRD0yMDEzMDA2Mg==">
     * <img src="/html/images/apply.gif">
     * </a>
     * <a title="到中国工商银行求职讨论区提问" target="_blank" href="http://bbs.yingjiesheng.com/forum-275-1.html">
     * <br>
     * </p>
     * <p/>
     * <p style="text-align: center;">
     * <a onclick="yjs_redirect('http://www.yingjiesheng.com/job_source.php?ID=1565932')" href="#">
     * <img src="/html/images/apply.gif">
     * </a>
     * <a title="到中国农业银行求职讨论区提问" target="_blank" href="http://bbs.yingjiesheng.com/forum-277-1.html">
     * <br>
     * </p>
     *
     * @param jobContent
     * @return
     */
    @Override
    public String getApply(String jobContent) {
        List<String> list = new ArrayList<String>();
        String regex = "<a[^>]*><img[^>]*src=(\"([^\"]/html/images/apply.gif)\"|\'([^\']/html/images/apply.gif)\'|([^\\s>]/html/images/apply.gif))[^>]*>(.*?)</a>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(jobContent);
        while (ma.find()) {
            list.add(ma.group());
        }

        if (list.size() > 0) {
            return list.get(0);
        }
        return "";
    }

    /**
     * @param htmlContent
     * @return
     */
    public List<String> getBasicInfo(String htmlContent) {
        List<String> list = new ArrayList<String>();
        String regex = "<ul[^>]*class=(\"basicInfo s_clear\"|\'basicInfo s_clear\'|(basicInfo s_clear))[^>]*>(.*?)</ul>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(htmlContent);
        while (ma.find()) {
            list.add(ma.group());
        }

        if (list.size() == 0) {
            regex = "<ul[^>]*class=(\"s_clear\"|\'s_clear\'|(s_clear))[^>]*>(.*?)</ul>";
            pa = Pattern.compile(regex, Pattern.DOTALL);
            ma = pa.matcher(htmlContent);
            while (ma.find()) {
                list.add(ma.group());
            }

            if (list.size() > 0) {
                regex = "<li.*?>(.*?)</li>";
                pa = Pattern.compile(regex, Pattern.DOTALL);
                ma = pa.matcher(list.get(0));
                list.clear();
                while (ma.find()) {
                    list.add(ma.group());
                }

                //for special        <span class="memo">
                regex = "<span[^>]*class=(\"memo\"|\'memo\'|(memo))[^>]*>(.*?)</span>";
                pa = Pattern.compile(regex, Pattern.DOTALL);
                ma = pa.matcher(htmlContent);
                String memo = "";
                while (ma.find()) {
                    memo = ma.group();
                }                         //(全职，发布于2013-04-22) 相关搜索
                memo = Util.outTag(memo);
                if (memo.contains("发布于")) {
                    list.add("<li>职位类型：" + memo.split("发布于")[0] + "</li>");
                    list.add("<li>发布时间：" + memo.split("发布于")[1].subSequence(0, 10) + "</li>");
                }
            }
        } else {
            regex = "<li[^>]*class=(\"floatl\"|\'floatl\'|(floatl))[^>]*>(.*?)</li>";
            pa = Pattern.compile(regex, Pattern.DOTALL);
            ma = pa.matcher(list.get(0));
            list.clear();
            while (ma.find()) {
                list.add(ma.group());
            }
        }
        return list;
    }

    /*
     * http://www.yingjiesheng.com/beijing-moreptjob-3.html
     * http://www.yingjiesheng.com/beijing/ptjob.html
     */
    public static void main(String[] args) {
        Spider ptSpider = new FTSpider();
        String content = ptSpider.getHtmlContent("http://www.yingjiesheng.com/beijing-moreptjob-3.html", "gb2312", "");
        System.out.print(content);
        List<String> links = ptSpider.getLinks(content, "/job-");
        for (String link : links) {
            System.out.println(link);
        }
    }
}