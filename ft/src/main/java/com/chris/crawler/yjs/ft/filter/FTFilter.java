package com.chris.crawler.yjs.ft.filter;

import com.chris.crawler.yjs.ft.FTConstant;
import com.chris.crawler.yjs.ft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: zhong.huang
 * Date: 13-4-28
 */
public class FTFilter implements Filter {
    @Override
    public String filter(String jobContent) {
        String content = jobContent.replace("<p>&nbsp;</p>", "");
        //------------ for scripts
        for (String tag : Util.getTags(content, "<script", "</script>")) {
            content = content.replace(tag, "");
        }

        for (String tag : Util.getTags(content, "<style", "</style>")) {
            content = content.replace(tag, "");
        }

        //---------------- for words
        List<String> list = new ArrayList<String>();
        String regex = "<p><a[^>]*href=(\"([^\"]#*)\"|\'([^\']#*)\'|([^\\s>]#*))[^>]*>(.*?)</a></p>";
        regex = regex.replace("#", "http://bbs.yingjiesheng.com/thread-");
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(content);
        while (ma.find()) {
            list.add(ma.group());
        }

        //------------- for end programs
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

//        regex = "<a[^>]*onclick=(\"([^\"]#*)\"|\'([

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

}
