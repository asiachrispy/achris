package com.chris.algo.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * User: zhong.huang
 * Date: 13-7-25
 */
public class MajorParser {
    private static String SRC_MAJOR = null;

    public MajorParser() {
    }

    public static Map<String, Integer> parser(String file) {
        if (file == null || "".equals(file)) {
            SRC_MAJOR = "major.txt";
        } else {
            SRC_MAJOR = file;
        }

        Map<String, Integer> majors = new HashMap<String, Integer>();
        try {
            ClassLoader classLoader = (new MajorParser()).getClass().getClassLoader();
            InputStream stream = classLoader.getResourceAsStream(SRC_MAJOR);
            Scanner scanner = new Scanner(stream);

            String line = null;
            String[] arr = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                arr = line.split("\t");
                if ("0".equals(arr[2])) {
                    majors.put(arr[1], Integer.valueOf(arr[0]));
                } else {
                    System.out.println("2 level major." + line);
                }
            }

            scanner.close();
            stream.close();

        } catch (IOException e) {
           e.printStackTrace();
        } finally {

        }
        return majors;
    }
}
