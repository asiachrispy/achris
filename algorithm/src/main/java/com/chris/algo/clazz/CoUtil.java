package com.chris.algo.clazz;

import java.util.*;

/**
 * User: zhong.huang
 * Date: 13-7-26
 */
public class CoUtil {

    public static List sorted(Map map) {

        List<Map.Entry<?, ?>> p = new ArrayList<Map.Entry<?, ?>>(map.entrySet());

        Collections.sort(p, new Comparator<Map.Entry<?, ?>>() {
            @Override
            public int compare(Map.Entry<?, ?> o1, Map.Entry<?, ?> o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()) .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        return p;
    }
}
