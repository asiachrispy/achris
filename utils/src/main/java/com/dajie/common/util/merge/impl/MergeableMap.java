package com.dajie.common.util.merge.impl;

import com.dajie.common.util.merge.Mergeable;

import java.util.HashMap;
import java.util.Map;

/**
 * User:tao.li
 * Date: 13-3-13
 * Time: 下午8:08
 */
public class MergeableMap extends AbstractMergeable<Map> implements Mergeable<Map> {
    private final Map result;

    public MergeableMap(Map result) {
        this.result = result;
    }

    public MergeableMap() {
        this(new HashMap());
    }

    @Override
    public boolean doAddData(Map map) {
        this.result.putAll(map);
        return true;
    }

    @Override
    public Map getMergeResult() {
        return this.result;
    }
}
