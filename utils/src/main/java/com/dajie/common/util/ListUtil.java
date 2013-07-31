package com.dajie.common.util;

import org.apache.commons.lang.math.NumberUtils;

import java.util.*;

/**
 * User: Danyun.Liu
 * Date: 11/24/11
 * Time: 11:09 AM
 */
public class ListUtil {
    /**
     * transfer integer list to string list
     *
     * @param intList integer list
     * @return string list
     */
    public static List<String> intListToStrList(List<Integer> intList) {
        List<String> strList = new ArrayList<String>();
        if (intList != null && !intList.isEmpty()) {
            for (Integer item : intList) {
                strList.add(String.valueOf(item));
            }
        }
        return strList;
    }

    /**
     * transfer string list to integer list
     *
     * @param strList string list
     * @return integer list
     */
    public static List<Integer> strListToIntList(List<String> strList) {
        if (!strList.isEmpty()) {
            List<Integer> intList = new ArrayList<Integer>(strList.size());
            for (String item : strList) {
                int id = NumberUtils.toInt(item);
                if (id > 0) {
                    intList.add(id);
                }
            }
            return intList;
        }
        return Collections.emptyList();
    }

    /**
     * transfer dict id string to integer list, default delimiter is comma
     *
     * @param idStr dict id string
     * @return integer id list
     */
    public static List<Integer> IdStrToIntStr(String idStr) {
        return idStrToIntStr(idStr, ",");
    }

    /**
     * Transfer the id string to integer list.
     * It is common we send back a id string to back-end server, and you need to transfer the str to List<Integer>
     *
     * @param idStr     id string split by the given delimiter
     * @param delimiter comma, colon etc.
     * @return Integer List
     */
    public static List<Integer> idStrToIntStr(String idStr, String delimiter) {
        List<String> tempList = StringUtil.splitStringByDelimiter(idStr, delimiter, "[0-9]+");
        return strListToIntList(tempList);
    }

    /**
     * Sort the map with the sorted List order
     *
     * @param targetMap  the map will be sorted
     * @param sortedList list in specific order
     * @param <K>        KEY
     * @param <V>        VALUE
     * @return sorted map
     */
    public static <K, V> Map<K, V> sortMap(Map<K, V> targetMap, List<K> sortedList) {
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (K k : sortedList) {
            if (targetMap.get(k) != null) {
                sortedMap.put(k, targetMap.get(k));
            }
        }
        return sortedMap;
    }
}
