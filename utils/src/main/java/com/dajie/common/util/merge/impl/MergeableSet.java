package com.dajie.common.util.merge.impl;

import com.dajie.common.util.merge.Mergeable;

import java.util.HashSet;
import java.util.Set;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:15
 */
public class MergeableSet extends AbstractMergeable<Set> implements Mergeable<Set> {
    private final Set result;

    public MergeableSet(Set result) {
        this.result = result;
    }

    public MergeableSet() {
        this(new HashSet());
    }

    @Override
    protected boolean doAddData(Set set) {
        return this.result.addAll(set);
    }

    @Override
    public Set getMergeResult() {
        return this.result;
    }
}
