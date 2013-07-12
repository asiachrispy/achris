package com.chris.crawler.yjs.ft;

import com.dajie.common.config.AppConfigs;
import com.dajie.common.dictionary.DictEnum;
import com.dajie.common.dictionary.DictManager;
import com.dajie.common.dictionary.model.Dict;
import com.dajie.common.dictionary.model.DictItem;
import com.dajie.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public final class FTConstant {
    public static final String YJS_HOME = "http://www.yingjiesheng.com/";

    public static final String DEFAULT_ENCODING = "gb2312";
    // for basic info keys
    public static final String BASIC_SCHEDULEBEGIN_CN = "发布时间";
    public static final String BASIC_PLACE_CN = "工作地点";
    public static final String BASIC_REFERURL_CN = "来源";//信息来源
    public static final String BASIC_RECRUITTYPE_CN = "职位"; //职位类型
    public static final String BASIC_RECRUITTYPE_FT_CN = "全职"; //职位类型
    public static final String BASIC_RECRUITTYPE_PT_CN = "兼职";

    public static final String CONFIG_FU_MAX_PAGE = "fu_max_page";
    public static final String CONFIG_PUB_DAY = "pub_day";
    public static final String CONFIG_FILTER_SITE = "filter_site";
    public static final String CONFIG_PROXY_IP = "proxy_ip";

    public static final String DEFAULT_PROXY_IP = "210.101.131.231:8080";
    public static final String[] PROXY_IP = {
        "218.241.153.43:8080", "218.241.153.43:8080",
        "122.72.12.52:8118", "124.81.113.183:8080", "211.154.83.38:80",
        "219.159.105.180:8080", "173.213.108.113:8080", "202.98.123.126:8080"};

    public static void main(String[] args) {
        Map<DictEnum, Dict> v = DictManager.getInstance().getDictMap();
        Dict d = DictManager.getInstance().getDict(DictEnum.DICT_CITY);
        List<DictItem> list = v.get(DictEnum.DICT_CITY).getData();
    }

    public static List<String> getFilterSites() {
        String value = AppConfigs.getInstance().get(CONFIG_FILTER_SITE);
        List<String> list = new ArrayList<String>();
        if (StringUtil.isNotEmpty(value)) {
            for (String site : value.split(";")) {
                list.add(site);
            }
        }
        return list;
    }
}
