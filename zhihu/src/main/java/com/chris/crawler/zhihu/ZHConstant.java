package com.chris.crawler.zhihu;

import com.dajie.common.config.AppConfigs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public final class ZHConstant {
    public static final int CONNECTION_OK = 200;
    public static final String COOKIE = "cookie";
    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String CONFIG_KEY_EMAIL_PERSONS = "email_persons";
    public static final String CONFIG_KEY_EMAIL_OWNER = "email_owner";
    public static final String CONFIG_KEY_END_QID = "end_qid";
    public static final String CONFIG_KEY_END_LOGITEM = "end_logitem";
    public static final String CONFIG_KEY_PROXY_IP = "proxy_ip";
    public static final String DEFAULT_PROXY_IP = "210.101.131.231:8080";
    public static final String[] PROXY_IP = {
        "218.241.153.43:8080", "122.72.12.52:8118", "124.81.113.183:8080",
        "211.154.83.38:80", "219.159.105.180:8080", "173.213.108.113:8080",
        "202.98.123.126:8080", "210.101.131.231:8080", "58.22.108.11:8118"};
    public static final List PERSONS = new LinkedList<String>();

    static {
        String report_receiver = AppConfigs.getInstance().get(ZHConstant.CONFIG_KEY_EMAIL_PERSONS);
        String[] report_receivers = report_receiver.split(";");
        for (String email : report_receivers) {
            PERSONS.add(email.trim());
        }
    }

    public static final List<String> getEmailPersons() {
        return PERSONS;
    }

    public static final List<String> getEmailOwner() {
        List owner = new ArrayList<String>();
        owner.add(AppConfigs.getInstance().get(ZHConstant.CONFIG_KEY_EMAIL_OWNER));
        return owner;
    }

}
