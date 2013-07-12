package com.chris.crawler.yjs.ft.parser;


import com.chris.crawler.yjs.ft.filter.FTFilter;
import com.chris.crawler.yjs.ft.filter.Filter;
import com.chris.crawler.yjs.ft.model.FTJob;
import com.chris.crawler.yjs.ft.spider.FTSpider;
import com.chris.crawler.yjs.ft.spider.Spider;
import com.chris.crawler.yjs.ft.util.DictUtil;
import com.chris.crawler.yjs.ft.util.Util;
import com.dajie.common.util.DateUtil;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.ft.FTConstant;
import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FTParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTParser.class);
    private Spider ftSpider = Guice.createInjector().getInstance(FTSpider.class);
    private Filter filter = Guice.createInjector().getInstance(FTFilter.class);

    public FTParser() {
    }

    /**
     * @return
     */
    public void parserJob(String yjsUrl, FTJob ftJob) {

        String jobHtmlContent = ftSpider.getHtmlContent(yjsUrl, FTConstant.DEFAULT_ENCODING, FTConstant.DEFAULT_PROXY_IP);
        if (StringUtil.isNotEmpty(jobHtmlContent)) {
            String title = ftSpider.getTitle(jobHtmlContent);
            parserTitle(title, ftJob);

            List<String> links = ftSpider.getBasicInfo(jobHtmlContent);
            parserBasicInfo(links, ftJob);
            if (StringUtil.isEmpty(ftJob.getPlace()) || ftJob.getPlace().equals("999999,")) {
                parserPlace(title, ftJob);
            }

            links = ftSpider.getMajor(jobHtmlContent);
            parserMajor(links, ftJob);

            String jobContent = ftSpider.getContent(jobHtmlContent);
            parserDegree(jobContent, ftJob);

            String apply = ftSpider.getApply(jobContent);
            parserApply(apply, ftJob);

            parserContent(jobContent, ftJob);
            ftJob.setCreateDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
    }

    /**
     * <a href="/job-001-571-120.html" target="_blank"><span class="emphasis">[置顶]</span> <span style="color: #008000;">甘肃</span>农业银行甘肃省分行2013合同制柜员招聘</a>
     *
     * @return set
     */
    public Set<String> parserHomeLinks(List<String> homeLinks) {
        Set<String> jobUrls = new HashSet<String>();
        String url = null;
        for (String homeLink : homeLinks) {
            url = homeLink.split("/")[1].split("\"")[0].trim();
            if (StringUtil.isNotEmpty(url)) {
                jobUrls.add(FTConstant.YJS_HOME + url);
            }
        }
        return jobUrls;
    }

    /**
     * <ul class="basicInfo s_clear">
     * <li class="floatl"><span>发布时间：</span>2013-04-28</li>
     * <li class="floatl"><span>工作地点：</span>其它 </li>
     * <li class="floatl"><span>来源：</span><a href="#" onclick="window.open('/job_source.php?ID=1572279')">中国科学院</a></li>
     * <li class="floatl"><span>职位类型：</span>全职</li>
     * </ul>
     * <p/>
     * <p/>
     * <span class="memo">
     * (全职，发布于2013-04-22)
     * <a target="_blank" href="http://s.yingjiesheng.com/result.jsp?keyword=%5B%E5%A4%A9%E6%B4%A5%5D%E8%81%94%E5%90%88%E4%BF%A1%E7%94%A82013%E6%98%A5%E5%AD%A3%E6%A0%A1%E5%9B%AD%E6%8B%9B%E8%81%98&do=1">相关搜索</a>
     * </span>     *
     *
     * @param list
     * @param ftJob
     */
    public void parserBasicInfo(List<String> list, FTJob ftJob) {
        String item = "";
        String itemValue = "";
        String[] kv = null;
        String[] places = null;
        StringBuffer sb = new StringBuffer();
        for (String info : list) {
            item = Util.outTag(info);
            if (item.contains("：")) {
                kv = item.split("：");
                if (kv.length > 1) {
                    itemValue = kv[1].trim();
                } else {
                    itemValue = "";
                }
                if (kv[0].contains(FTConstant.BASIC_SCHEDULEBEGIN_CN)) {
                    ftJob.setScheduleBegin(itemValue);// no time
                } else if (kv[0].contains(FTConstant.BASIC_PLACE_CN)) {
                    places = itemValue.split(" ");
                    for (String place : places) {
                        place = place.trim();
                        if (StringUtil.isNotEmpty(place) && DictUtil.getCityMap().containsKey(place)) {
                            sb.append(DictUtil.getCityMap().get(place)).append(",");
                        }
                    }
                    ftJob.setPlace(sb.toString());// dict
                } else if (kv[0].contains(FTConstant.BASIC_REFERURL_CN)) {
                    if (StringUtil.isEmpty(itemValue)) {
                        itemValue = "--";
                    } else {
                        itemValue = FTConstant.YJS_HOME.substring(0, FTConstant.YJS_HOME.length() - 1) + info.split("'")[1];
                        itemValue = ftSpider.getHtmlContent(itemValue, "gb2312", "");
                        itemValue = ftSpider.getLinks(itemValue, "").get(0);
                        itemValue = Util.outTag(itemValue);
                        //if has yingjiesheng or not startwith http://
                        if (StringUtil.isNotEmpty(itemValue) && itemValue.startsWith("http://") && !itemValue.contains("yingjiesheng")) {
                        } else {
                            LOGGER.warn("Refer Url is validate {}.", itemValue);
                            itemValue = "--";
                        }
                    }
                    ftJob.setReferUrl(itemValue);
                } else if (kv[0].contains(FTConstant.BASIC_RECRUITTYPE_CN)) {
                    if (StringUtil.isNotEmpty(itemValue) && itemValue.contains(FTConstant.BASIC_RECRUITTYPE_FT_CN)) {
                        ftJob.setRecruitType("1");// full time 1
                    } else if (StringUtil.isNotEmpty(itemValue) && itemValue.contains(FTConstant.BASIC_RECRUITTYPE_PT_CN)) {
                        ftJob.setRecruitType("0");// parttime 0
                    } else {
                        ftJob.setRecruitType("1");// others
                    }
                }
            }
        }

        //
    }

    /**
     * 解析专业
     * <div class="relHotJob">
     * <span>职位专业分类：</span>
     * <a target="_blank" href="/major/wuli/">物理</a>
     * <a target="_blank" href="/major/wenke/">文史哲政</a>
     * <a target="_blank" href="/major/shengwu/">生物工程</a>
     * <a target="_blank" href="/major/nonglin/">农林牧渔</a>
     * <a target="_blank" href="/major/lixue/">力学</a>
     * <a target="_blank" href="/major/jixie/">机械</a>
     * <a target="_blank" href="/major/huaxue/">化学化工</a>
     * <a target="_blank" href="/major/cailiao/">材料</a>
     * </div>
     *
     * @param majors
     * @param ftJob
     */
    public void parserMajor(List<String> majors, FTJob ftJob) {
        String major = "";
        String dmajor = "";
        StringBuilder imajor = new StringBuilder();
        StringBuilder ymajor = new StringBuilder();
        for (String majorHref : majors) {
            major = Util.outTag(majorHref).trim();
            ymajor.append(major).append(",");
            if (DictUtil.getMajorMap().containsKey(major)) {
                dmajor = DictUtil.getMajorMap().get(major); //
                if (DictUtil.getDajieMajorMap().containsKey(dmajor)) {
                    imajor.append(DictUtil.getDajieMajorMap().get(dmajor).toString()).append(",");//dajie major
                }
            }
        }
        ftJob.setYjsMajor(ymajor.toString());
        ftJob.setMajor(imajor.toString());
    }

    /**
     * 解析学历
     *
     * @param jobContent
     * @param ftJob
     */
    public void parserDegree(String jobContent, FTJob ftJob) {
        if (StringUtil.isNotEmpty(jobContent)) {
            StringBuilder degree = new StringBuilder();
            for (Map.Entry<String, Integer> entry : DictUtil.getDegreeMap().entrySet()) {
                if (jobContent.contains(entry.getKey())) {
                    degree.append(entry.getValue()).append(",");
                }
            }
            ftJob.setDegree(degree.toString());
        }
    }

    /**
     * 解析title
     *
     * @param titleContent
     * @param ftJob
     */
    public void parserTitle(String titleContent, FTJob ftJob) {
        String title = "";
        if (StringUtil.isNotEmpty(titleContent)) {
            title = Util.outTag(titleContent).trim();
            if (StringUtil.isNotEmpty(title) && title.contains("]")) {
                String[] heads = title.split("]");
                title = heads[1];
                title = title.replace("_最新校园招聘职位信息", "");
            }
        }
        ftJob.setName(title);
    }

    /**
     * 如果basicinfo 中没有Place，则通过title解析Place
     *
     * @param titleContent
     * @param ftJob
     */
    public void parserPlace(String titleContent, FTJob ftJob) {
        String places = "";
        if (StringUtil.isNotEmpty(titleContent) && titleContent.contains("]")) {
            places = Util.outTag(titleContent).trim();
            String[] heads = places.split("]");
            places = heads[0].substring(1, heads[0].length());
            StringBuffer sb = new StringBuffer();
            if (places.contains("|")) {
                heads = places.split("|");
                for (String place : heads) {
                    place = place.trim();
                    if (StringUtil.isNotEmpty(place) && DictUtil.getCityMap().containsKey(place)) {
                        sb.append(DictUtil.getCityMap().get(place)).append(",");
                    }
                }
                if (StringUtil.isNotEmpty(sb.toString())) {
                    ftJob.setPlace(sb.toString());
                }
            }
        }
    }

    /**
     * 解析 apply url
     * <a href="#" onclick="yjs_redirect('http://www.yingjiesheng.com/job_source.php?ID=1565932')"><img src="/html/images/apply.gif" /></a>
     * <p/>
     * <a target="_blank" href="http://my.yingjiesheng.com/index.php/personal/applyjob.htm/?jobid=1567141&appcomid=514&applnk=aHR0cHM6Ly9qb2IuaWNiYy5jb20uY24vSUNCQ0R5bmFtaWNTaXRlMi9FbXBsb3kvQWZmaUxpc3QuYXNweD9BZmZpVHlwZT0xJlBsYW5JRD0yMDEzMDA2Mg==">
     * <img src="/html/images/apply.gif">
     * </a>
     *
     * @param applyContent
     * @param ftJob
     */
    public void parserApply(String applyContent, FTJob ftJob) {
        if (StringUtil.isNotEmpty(applyContent)) {
            int start = applyContent.indexOf("http://");
            int end = applyContent.indexOf("<img");
            if (start >= 0 && end > 0) {
                String href = applyContent.substring(start, end).trim();
                if (href.contains(")")) {
                    href = href.substring(0, href.length() - 4);
                } else {
                    href = href.substring(0, href.length() - 2);
                }

                ftJob.setApplyUrl(href);
            } else {
                ftJob.setApplyUrl("");
            }
        } else {
            ftJob.setApplyUrl("");
        }
    }


    /**
     * 解析 job content
     *
     * @param jobContent
     * @param ftJob
     */
    public void parserContent(String jobContent, FTJob ftJob) {
        String content = jobContent;
        content = filter.filter(content);
        ftJob.setContent(content);
    }

    /**
     * <p><a href="http://bbs.yingjiesheng.com/thread-1100509-1-1.html" target="_blank" style="color:#ff0000;" >本站提醒:如何识别虚假招聘信息？求职必看，切勿受骗上当！</a></p>
     * <p><a href="http://bbs.yingjiesheng.com/thread-690300-1-1.html" target="_blank">如何写一份简单、直接、高效的求职信？</a></p>
     * <p/>
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
     * <p/>
     * <a target="_blank" class="word_link" href="http://topic.yingjiesheng.com/cet4/">四级</a>
     *
     * @param jobContent
     * @return
     */
    private List<String> getFilterProgram(String jobContent) {
        List<String> list = new ArrayList<String>();
        if (StringUtil.isNotEmpty(jobContent)) {
            String regex = "<p><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
            regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
            Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            Matcher ma = pa.matcher(jobContent);
            while (ma.find()) {
                list.add(ma.group());
            }

            regex = "<p[^>]*style=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</p>";  //<p style="text-align: center;">
            regex = regex.replace("#", "text-align: center;");
            pa = Pattern.compile(regex, Pattern.DOTALL);
            ma = pa.matcher(jobContent);
            while (ma.find()) {
                list.add(ma.group());
            }

            for (String filter : FTConstant.getFilterSites()) {
                regex = "<a[^>]*href=(\"([^\"]*#*)\"|\'([^\']*#*)\'|([^\\s>]*#*))[^>]*>(.*?)</a>";
                regex = regex.replace("#", filter);
                pa = Pattern.compile(regex, Pattern.DOTALL);
                ma = pa.matcher(jobContent);
                while (ma.find()) {
                    list.add(ma.group());
                }
            }
        }
        return list;
    }
}