package com.dajie.crawler.yjs.ft.demo;

import com.chris.crawler.yjs.ft.FTConstant;
import com.chris.crawler.yjs.ft.filter.Filter;
import com.chris.crawler.yjs.ft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
public class TFTFilter implements Filter {
    @Override
    public String filter(String jobContent) {
        String content = jobContent.replaceAll("(<style.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)(</style>)", "");

        //---------------- for words
        List<String> list = new ArrayList<String>();
        String regex = "<p><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
        regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(content);
        while (ma.find()) {
            list.add(ma.group());
        }

        //------------ for scripts
//        regex = "<script[^>]*>((\r\n)*)(.*?)((\r\n)*)(.*?)</script>";
//        pa = Pattern.compile(regex, Pattern.DOTALL);
//        ma = pa.matcher(content);
//        while (ma.find()) {
//            list.add(ma.group());
//        }

        for (String tag : Util.getTags(content, "<script", "</script>")) {
            content = content.replace(tag, "");
        }

        // for end programs
        regex = "<p[^>]*style=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</p>";  //<p style="text-align: center;">
        regex = regex.replace("#", "text-align: center;");
        pa = Pattern.compile(regex, Pattern.DOTALL);
        ma = pa.matcher(content);
        while (ma.find()) {
            list.add(ma.group());
        }
        for (String pro : list) {
            content = content.replace(pro, "");
        }

        //-------------- for filter word
        list.clear();
        for (String filter : FTConstant.getFilterSites()) {
            regex = "<a[^>]*href=(\"([^\"]*#*)\"|\'([^\']*#*)\'|([^\\s>]*#*))[^>]*>(.*?)</a>";
            regex = regex.replace("#", filter);
            pa = Pattern.compile(regex, Pattern.DOTALL);
            ma = pa.matcher(content);
            while (ma.find()) {
                list.add(ma.group());
            }
        }
        for (String pro : list) {
            content = content.replace(pro, Util.outTag(pro));
        }

        regex = "<a[^>]*onclick=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a>";
        regex = regex.replace("#", "yjs_redirect");
        content = content.replaceAll(regex, "");
        content = content.replace("<p>&nbsp;</p>", "");

        //---------------- for head  programs
        int start = content.indexOf("<table");
        int end = content.indexOf("</table>");
        if (start > 0 && end > 0) {
            String tcontent = content.substring(start, end);
            if (tcontent.contains("搜索更多")) {
                content = "<div class=\"jobIntro\">\n" + content.substring(end + "</table>".length()).trim();
            }
        }

        return content;
    }

    public String filters(String jobContent) {
        String content = jobContent.replaceAll("(<script.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)(</script>)", "");
        content = content.replaceAll("(<style.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)(</style>)", "");

        String regex = "<p><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
        regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
        content = content.replaceAll(regex, "");

        regex = "<p[^>]*style=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</p>";  //<p style="text-align: center;">
        regex = regex.replace("#", "text-align: center;");
        content = content.replaceAll(regex, "");

        for (String filter : FTConstant.getFilterSites()) {
            regex = "<a[^>]*href=(\"([^\"]*#*)\"|\'([^\']*#*)\'|([^\\s>]*#*))[^>]*>(.*?)</a>";
            regex = regex.replace("#", filter);
            content = content.replaceAll(regex, "");
        }

        /*
 List<String> list = new ArrayList<String>();
 String regex = "<p><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
 regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
 Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
 Matcher ma = pa.matcher(content);
 while (ma.find()) {
     list.add(ma.group());
 }

 regex = "<p[^>]*style=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</p>";  //<p style="text-align: center;">
 regex = regex.replace("#", "text-align: center;");
 pa = Pattern.compile(regex, Pattern.DOTALL);
 ma = pa.matcher(content);
 while (ma.find()) {
     list.add(ma.group());
 }

 //  replace
 for (String pro : list) {
     content = content.replace(pro, "");
 }

 list.clear();

 for (String filter : FTConstant.getFilterSites()) {
     regex = "<a[^>]*href=(\"([^\"]*#*)\"|\'([^\']*#*)\'|([^\\s>]*#*))[^>]*>(.*?)</a>";
     regex = regex.replace("#", filter);
     pa = Pattern.compile(regex, Pattern.DOTALL);
     ma = pa.matcher(content);
     while (ma.find()) {
         list.add(ma.group());
     }
 }

 //  replace
 for (String pro : list) {
     content = content.replace(pro, Util.outTag(pro));
 }
        */
        return content;
    }
}
