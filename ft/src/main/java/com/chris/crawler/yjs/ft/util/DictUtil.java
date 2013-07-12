package com.chris.crawler.yjs.ft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-2
 */
public final class DictUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(DictUtil.class);

    public static final Map<String, Integer> CITY_MAP = new HashMap<String, Integer>();
    public static final Map<String, String> MAJOR_MAP = new HashMap<String, String>();
    public static final Map<String, Integer> DAJIE_MAJOR_MAP = new HashMap<String, Integer>();
    public static final Map<String, Integer> DEGREE_MAP = new HashMap<String, Integer>();

    public DictUtil() {
    }

    static {
        try {
            String line = null;
            String[] kv = null;
            ClassLoader classLoader = (new DictUtil()).getClass().getClassLoader();

            InputStream is = classLoader.getResourceAsStream("CITY_DICT.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                kv = line.split("=");
                CITY_MAP.put(kv[0], Integer.valueOf(kv[1]));
            }

            is = classLoader.getResourceAsStream("MAJOR_DICT.txt");
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                kv = line.split("=");
                MAJOR_MAP.put(kv[0], kv[1]);
            }

            is = classLoader.getResourceAsStream("DAJIE_MAJOR_DICT.txt");
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                kv = line.split("=");
                DAJIE_MAJOR_MAP.put(kv[0], Integer.valueOf(kv[1]));
            }
            reader.close();

            DEGREE_MAP.put("博士学历", 10);//博士研究生
            DEGREE_MAP.put("博士研究生", 10);//博士研究生

            DEGREE_MAP.put("硕士学历", 12); //硕士研究生
            DEGREE_MAP.put("硕士研究生", 12); //硕士研究生
            DEGREE_MAP.put("硕士及以上学历", 12); //硕士研究生
            DEGREE_MAP.put("硕士以上学历", 12); //硕士研究生
            DEGREE_MAP.put("研究生学历", 12); //硕士研究生
            DEGREE_MAP.put("研究生以上学历", 12); //硕士研究生
            DEGREE_MAP.put("研究生及以上学历", 12); //硕士研究生

            DEGREE_MAP.put("本科学历", 13);
            DEGREE_MAP.put("本科以上学历", 13);
            DEGREE_MAP.put("本科及以上学历", 13);

            DEGREE_MAP.put("大专学历", 14);
            DEGREE_MAP.put("大专以上学历", 14);
            DEGREE_MAP.put("大专及以上学历", 14);

            DEGREE_MAP.put("学历不限", 16);//其他

        } catch (Exception e) {
            LOGGER.error("init ConfigLoader error:" + e.getMessage());
            throw new ExceptionInInitializerError("Failed to load config ");
        }
    }

    public static Map<String, Integer> getCityMap() {
        return CITY_MAP;
    }

    public static Map<String, String> getMajorMap() {
        return MAJOR_MAP;
    }

    public static Map<String, Integer> getDajieMajorMap() {
        return DAJIE_MAJOR_MAP;
    }

    public static Map<String, Integer> getDegreeMap() {
        return DEGREE_MAP;
    }

    public static void main(String[] args) {
        System.out.println(DictUtil.getCityMap().get("七台河"));
    }

}
