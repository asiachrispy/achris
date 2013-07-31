package com.chris.algo.clazz;

import com.dajie.algo.clazz.MajorModel;
import com.dajie.algo.clazz.MajorParser;
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
    private Map<String, Set<String>> jd_words = new HashMap<String, Set<String>>();
    private Map<String, List<MajorModel>> majors = null;
    //private Map<Integer, Map<String, Double>> words_x2 = new HashMap<Integer, Map<String, Double>>();
    private Map<String, Double> words_idf = new HashMap<String, Double>();
    //private Map<String, Double> words_tf_idf = new HashMap<String, Double>();
    private Map<String,Map<String, Double>> jd_words_tf_idf = new HashMap<String,Map<String, Double>>(); //<jd,<word,tf-idf>>
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
        Set<String> jdWords = null;
        Set<String> jds = null;
        String jd = null;
        boolean next = false;
        MajorModel model = null;
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
                jdWords = new HashSet<String>();
                jd_words.put(jd, jdWords);

                for (String m : arr) {
                    model = getMajorModel(m);
                    if (model != null) { // 对专业进行验证，确保专业存在
                        next = true;
                        if (major_words.containsKey(model.getId())) {
                            words = major_words.get(model.getId());
                        } else {
                            words = new HashSet<String>();
                            if (model.isMain()) {
                                major_words.put(model.getId(), words);
                            } else {
                                major_words.put(model.getParent().getId(), words);
                            }
                        }

                        if (major_jds.containsKey(model.getId())) {
                            jds = major_jds.get(model.getId());
                        } else {
                            jds = new HashSet<String>();
                            if (model.isMain()) {
                                major_jds.put(model.getId(), jds);
                            } else {
                                major_jds.put(model.getParent().getId(), jds);
                            }
                        }
                        jds.add(jd);//get job id

                    } else {
                        next = false;
                    }
                }
            } else if (line.startsWith("corp:")) {
                // ignore
                //} else if (line.startsWith("title:")) {
                // ignore
            } else {
                if (next) {
                    for (String word : splitter.split(line)) {
                        words.add(word);
                        jdWords.add(word);
                    }
                    jdWords.remove("intro:");
                    jdWords.remove("title:");
                    words.remove("intro:");
                    words.remove("title:");
                }
            }
        }
    }

    //step2
    public void x2() {
        int a = 0; // 属于某个分类且出现在这个分类所有jd中的次数
        int b = 0; // 不属于这个分类但出现在其它分类所有jd中的次数
        int c = 0; // 属于这个分类但没有出现在这个分类所有jd中的次数
        int d = 0; // 不属于这个分类也没有出现在其它分类所有jd中的次数

        double P_key = 0d;
        double E_key = 0d;//包含Key的jd数量
        double X2_key = 0d;
        Iterator<Set<String>> others = null;
        // 每专业下的词的x2值
        //Map<String, Double> word_x2 = null;
        Map<Integer, Set<String>> tmp = new HashMap<Integer, Set<String>>();
        for (Map.Entry<Integer, Set<String>> entry : major_words.entrySet()) {
            //word_x2 = new HashMap<String, Double>();
            for (String keyWord : entry.getValue()) {
                //属于这个分类
                for (String jd : major_jds.get(entry.getKey())) {// key在某个分类下的所有jd
                    if (jd_words.get(jd).contains(keyWord)) {// 包含这个key的jd
                        a++;
                    } else {
                        c++;// 不包含这个key的Jd
                    }
                }

                //不属于这个分类 ,注意这里要删除当前的分类
                tmp.putAll(major_jds);
                tmp.remove(entry.getKey());
                others = tmp.values().iterator();
                while (others.hasNext()) {
                    for (String jd : others.next()) {
                        if (jd_words.get(jd).contains(keyWord)) {// 包含这个key的jd
                            b++;
                        } else {
                            d++;// 不包含这个key的Jd
                        }
                    }
                }
                //P_key = (a + b) / (a + b + c + d);
                //E_key = (a + c) * P_key;//

                X2_key = Math.pow(a * d - b * c, 2) / ((a + b) * (c + d));
                //word_x2.put(keyWord, X2_key);
                // keyword idf
                words_idf.put(keyWord, idf(jd_words.size() / (a + b) + 0.0d));

            }
            //words_x2.put(entry.getKey(), word_x2);
        }
    }

    private double idf(double n) {
        return Math.log(n + 0.01);
    }

    private double tf_idf(int tf, double idf) {
        return tf * idf;
    }

    public Map<String, Double> clazz(JDModel model) {
        double tf_itf = 0d;
        int tf = 0;
        Map<String, Double> words_tf_idf = new HashMap<String, Double>();
        for (String word : model.getWords().keySet()) {
            if (words_idf.containsKey(word)) {
                tf = model.getWords().get(word);
                tf_itf = tf_idf(tf, words_idf.get(word));
                words_tf_idf.put(word, tf_itf);
                System.out.println("word=" + word + " tf" + tf + "  tf-idf : " + tf_itf);
            } else {
                System.out.println(word + " is not in words_x2.");
            }
        }

        List<Map.Entry<String, Double>> s = CoUtil.sorted(words_tf_idf);
        Map<String, Double> f = new HashMap<String, Double>();
        Map.Entry<String, Double> entry = null;

        int size = 30;
        if (s.size() > 30) {
            size = 30 + (s.size() - 30) / 2;
        }

        for (int i = 0; i < size; i++) {
            entry = s.get(i);
            f.put(entry.getKey(), entry.getValue());
        }
        return f;
    }

    private MajorModel getMajorModel(String major) {
        for (MajorModel model : majors.get("one")) {
            if (major.equals(model.getValue())) {
                return model;
            }
        }

        for (MajorModel model : majors.get("two")) {
            if (major.equals(model.getValue())) {
                return model;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Chi chi = new Chi();
        chi.handler();
        chi.x2();
        JDParser parser = new JDParser();
        List<JDModel> models = parser.parser();
        for (JDModel model : models) {
            chi.clazz(model);
        }
    }

}
