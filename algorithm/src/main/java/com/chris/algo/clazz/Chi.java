package com.chris.algo.clazz;

import com.google.common.base.Splitter;

import java.io.InputStream;
import java.util.*;

/**
 * User: zhong.huang
 * Date: 13-7-25
 */
public class Chi {
    private Map<Integer, Set<String>> major_jds = new HashMap<Integer, Set<String>>();
    private Map<Integer, Set<String>> major_words = new HashMap<Integer, Set<String>>();
    private Map<String, Integer> majors = null;
    private String SRC_JD = null;

    public Chi() {
        SRC_JD = "jd.txt";
        majors = MajorParser.parser("major.txt");
    }

    //step1
    // 计算各个分类的Jd数
    public void handler() {
        ClassLoader classLoader = (new Chi()).getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(SRC_JD);
        Scanner scanner = new Scanner(stream);

        String line = null;
        String jdLine = null;
        String[] arr = null;
        Set<String> words = null;
        Set<String> jds = null;
        String jd = null;

        Splitter splitter = Splitter.on(" ").omitEmptyStrings().trimResults();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line == null || line.trim().equals("")) {
                continue;
            }

            if (line.startsWith("jobid:")) {
                jdLine = line;
            } else if (line.startsWith("majorcalssify:")) {
                arr = line.split(":")[1].split(",");
                jd = jdLine.split(" ")[1];
                for (String m : arr) {
                    //if (majors.containsKey(m)) { // 对专业进行验证，确保专业存在
                    if (major_words.containsKey(majors.get(m))) {
                        words = major_words.get(majors.get(m));
                    } else {
                        words = new HashSet<String>();
                        major_words.put(majors.get(m), words);
                    }

                    if (major_jds.containsKey(majors.get(m))) {
                        jds = major_jds.get(majors.get(m));
                    } else {
                        jds = new HashSet<String>();
                        major_jds.put(majors.get(m), jds);
                    }
                    jds.add(jd);//get job id

                    //}
                }
            } else if (line.startsWith("corp:")) {
                // ignore
                //} else if (line.startsWith("title:")) {
                // ignore
            } else {
                for (String word : splitter.split(line)) {
                    words.add(word);
                }
                words.remove("intro:");
                words.remove("title:");
            }
        }
    }

    public static void main(String[] args) {
        Chi chi = new Chi();
        chi.handler();
    }

}
