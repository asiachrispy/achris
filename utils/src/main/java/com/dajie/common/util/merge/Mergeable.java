package com.dajie.common.util.merge;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:04
 */
public interface Mergeable<T> {
    boolean addData(T t);

    T getMergeResult();
}
