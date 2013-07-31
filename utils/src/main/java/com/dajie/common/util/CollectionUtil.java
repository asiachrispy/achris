package com.dajie.common.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * User:tao.li
 * Date: 13-1-15
 * Time: 下午6:30
 */
public class CollectionUtil {

    /**
     * sep为空的话，则默认以空格分隔
     *
     * @param src
     * @param sep
     * @return
     */
    public static List<Integer> getIntegerListFromString(String src, String sep) {
        if (StringUtil.isEmpty(src)) {
            return Collections.EMPTY_LIST;
        }
        if (StringUtil.isEmpty(sep)) {
            sep = " ";
        }
        Set<Integer> resSet = new HashSet<Integer>();
        String[] srcArray = (src.trim()).split(sep);
        for (String number : srcArray) {
            if (StringUtil.isInteger(number.trim())) {
                resSet.add(Integer.parseInt(number.trim()));
            }
        }
        return new ArrayList<Integer>(resSet);
    }

    public static List<String> getStringListFromString(String src, String sep) {
        if (StringUtil.isEmpty(src)) {
            return Collections.EMPTY_LIST;
        }
        Set<String> resSet = new HashSet<String>();
        String[] srcArray = (src.trim()).split(sep);
        for (String item : srcArray) {
            if (StringUtil.isNotEmpty(item.trim())) {
                resSet.add(item.trim());
            }
        }
        return new ArrayList<String>(resSet);
    }

    public static <T> boolean isCollectionEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isCollectionNotEmpty(Collection<T> collection) {
        return !isCollectionEmpty(collection);
    }


    /**
     * list 必须是拍好顺序的
     *
     * @param list 必须是拍好顺序的
     * @param i
     * @return
     */
    public static boolean isContain(List<Integer> list, Integer i) {
        if (isCollectionEmpty(list) || i == null) {
            return false;
        }
        return Collections.binarySearch(list, i) >= 0;
    }

    /**
     * sep为空的话，则默认以空格分隔
     *
     * @param list
     * @param sep
     * @param <T>
     * @return
     */
    public static <T> String getStringFromList(List<T> list, String sep) {
        if (isCollectionEmpty(list)) {
            return "";
        }
        if (StringUtil.isEmpty(sep)) {
            sep = " ";
        }
        StringBuilder sb = new StringBuilder();
        for (T t : list) {
            sb.append(t.toString()).append(sep);
        }
        int lastSep = sb.lastIndexOf(sep);
        if (lastSep > 0) {
            return sb.substring(0, lastSep);
        } else {
            return sb.toString();
        }
    }

    public static <T> List<T>[] subList(List<T> src, int preBatchCount) {
        int count = src.size();
        int batchCount = (count + preBatchCount - 1) / preBatchCount;
        List<T>[] result = new List[batchCount];
        for (int index = 0; index < batchCount; index++) {
            int begin = index * preBatchCount;
            int end = (index + 1) * preBatchCount;
            if (end > count) {
                end = count;
            }
            result[index] = src.subList(begin, end);
        }
        return result;
    }

    public static <T, E> List<T> convertList(Collection<E> src, Converter<E, T> converter, boolean includeNull) {
        if (CollectionUtils.isEmpty(src)) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<T>(src.size());
        for (E e : src) {
            T t = converter.convert(e);
            if (!includeNull && t != null) {
                result.add(t);
            } else {
                result.add(t);
            }
        }
        return result;
    }

    public interface Converter<E, T> {
        T convert(E e);
    }
}
