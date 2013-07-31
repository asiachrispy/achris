package com.dajie.algo.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: zhong.huang
 * Date: 13-7-25
 */
public class MajorParser {
    private static String SRC_MAJOR = null;

    public MajorParser() {
    }

    public static Map<String,List<com.dajie.algo.clazz.MajorModel>> parser(String file) {

        if (file == null || "".equals(file)) {
            SRC_MAJOR = "major.txt";
        } else {
            SRC_MAJOR = file;
        }

        Map<String,List<com.dajie.algo.clazz.MajorModel>> MAJOR = new HashMap<String,List<com.dajie.algo.clazz.MajorModel>>();
        List<com.dajie.algo.clazz.MajorModel> one = new ArrayList<com.dajie.algo.clazz.MajorModel>();
        List<com.dajie.algo.clazz.MajorModel> two = new ArrayList<com.dajie.algo.clazz.MajorModel>();
        MAJOR.put("one", one);
        MAJOR.put("two", two);

        try {
            ClassLoader classLoader = (new MajorParser()).getClass().getClassLoader();
            InputStream stream = classLoader.getResourceAsStream(SRC_MAJOR);
            Scanner scanner = new Scanner(stream);

            String line = null;
            String[] arr = null;
            com.dajie.algo.clazz.MajorModel model = null;
            while (scanner.hasNextLine()) {
                model = new com.dajie.algo.clazz.MajorModel();
                line = scanner.nextLine();
                arr = line.split(" ");
                model.setParent(new com.dajie.algo.clazz.MajorModel(Integer.valueOf(arr[2])));
                model.setId(Integer.valueOf(arr[0]));
                model.setValue(arr[1]);
                if ("0".equals(arr[2])) {
                  one.add(model);
                } else {
                  two.add(model);
                }

            }

            scanner.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return MAJOR;
    }
}
