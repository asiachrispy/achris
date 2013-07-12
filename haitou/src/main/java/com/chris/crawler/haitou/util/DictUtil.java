package com.chris.crawler.haitou.util;

import com.dajie.common.config.AppConfigs;
import com.chris.crawler.haitou.HTConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * User: zhong.huang
 * Date: 13-5-2
 */
public final class DictUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(DictUtil.class);

    public static final Map<String, Integer> CITIES = new HashMap<String, Integer>();
    public static final Map<String, Map<String, Integer>> SCHOOL_MAP = new HashMap<String, Map<String, Integer>>();

    private DictUtil() {
    }

    public static DictUtil getInstance() {
        return new DictUtil();
    }

    static {
        try {
            String[] cities = AppConfigs.getInstance().get(HTConstant.CONFIG_KEY_CITY).split(";");
            String[] keys = null;
            for (String city : cities) {
                keys = city.split("-");
                CITIES.put(keys[0], Integer.valueOf(keys[1]));
            }

            ClassLoader classLoader = (new DictUtil()).getClass().getClassLoader();
            InputStream is = classLoader.getResourceAsStream("SCHOOL_DICT.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            String[] kv = null;
            String abb = null;
            Map<String, Integer> sch = null;
            while ((line = reader.readLine()) != null) {
                kv = line.split("=");
                if (CITIES.containsKey(kv[0])) {
                    abb = kv[0];
                    sch = new HashMap<String, Integer>();
                    SCHOOL_MAP.put(abb, sch);
                    continue;
                }
                sch.put(kv[0], Integer.valueOf(kv[1]));
            }

        } catch (Exception e) {
            LOGGER.error("init ConfigLoader error:" + e.getMessage());
            throw new ExceptionInInitializerError("Failed to load config ");
        }
    }

    public Map<String, Integer> getCity() {
        return CITIES;
    }

    public Map<String, Map<String, Integer>> getSchool() {
        return SCHOOL_MAP;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().getCity().get("gz"));
        System.out.println(getInstance().getSchool().get("gz").get("中大"));
    }
}
