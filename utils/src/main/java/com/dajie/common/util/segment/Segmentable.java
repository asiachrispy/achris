package com.dajie.common.util.segment;

import java.util.Collection;

/**
 * User:tao.li
 * Date: 13-3-18
 * Time: 下午8:36
 */
public interface Segmentable<T> {
    Collection<T> segment(T t);
}
