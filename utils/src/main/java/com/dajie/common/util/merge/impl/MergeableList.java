package com.dajie.common.util.merge.impl;

import com.dajie.common.util.merge.Mergeable;

import java.util.ArrayList;
import java.util.List;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:06
 */
public class MergeableList extends AbstractMergeable<List> implements Mergeable<List> {
    private final List result;

    public MergeableList(List result) {
        this.result = result;
    }

    public MergeableList() {
        this(new ArrayList());
    }

    @Override
    public boolean doAddData(List list) {
        return this.result.addAll(list);
    }

    @Override
    public List getMergeResult() {
        return this.result;
    }
}
