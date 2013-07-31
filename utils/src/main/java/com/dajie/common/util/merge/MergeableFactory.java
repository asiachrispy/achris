package com.dajie.common.util.merge;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:20
 */
public interface MergeableFactory {
    <T> Mergeable<T> getMergeForType(Class<T> cls);
}
