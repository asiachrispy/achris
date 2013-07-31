package com.dajie.common.util.merge.impl;

import com.dajie.common.util.merge.Mergeable;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:12
 */
public abstract class AbstractMergeable<T> implements Mergeable<T> {
    @Override
    public boolean addData(T t) {
        return t == null ? false : doAddData(t);
    }

    protected abstract boolean doAddData(T t);

}
