package com.dajie.common.util.merge.impl;

import com.dajie.common.util.merge.Mergeable;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:10
 */
public class MergeableInteger extends AbstractMergeable<Integer> implements Mergeable<Integer> {
    private Integer result;

    public MergeableInteger(Integer result) {
        this.result = result;
    }

    public MergeableInteger() {
        this(0);
    }

    @Override
    public boolean doAddData(Integer integer) {
        if (integer != null) {
            this.result += integer;
            return true;
        }
        return false;
    }

    @Override
    public Integer getMergeResult() {
        return this.result;
    }
}
