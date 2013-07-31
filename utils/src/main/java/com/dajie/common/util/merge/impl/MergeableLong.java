package com.dajie.common.util.merge.impl;

import com.dajie.common.util.merge.Mergeable;

/**
 * User:tao.li
 * Date: 13-3-18
 * Time: 下午1:57
 */
public class MergeableLong extends AbstractMergeable<Long> implements Mergeable<Long> {
    private Long result = 0l;
    @Override
    protected boolean doAddData(Long aLong) {
        result += aLong;
        return false;
    }

    @Override
    public Long getMergeResult() {
        return result;
    }
}
