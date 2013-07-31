package com.dajie.common.util.segment.impl;

import com.dajie.common.util.segment.Segmentable;
import org.apache.commons.collections.iterators.ArrayIterator;

import java.util.Iterator;

/**
 * User:tao.li
 * Date: 13-3-19
 * Time: 上午10:25
 */
public class SegmentableArray extends AbstractSegmentable<Object[]> implements Segmentable<Object[]> {

    public SegmentableArray(Creator<Object[]> creator, int countPerSeg) {
        super(creator, countPerSeg);
    }

    @Override
    protected Iterator getIteratorFor(Object[] objects) {
        return new ArrayIterator(objects);
    }

    @Override
    protected void addElement(Object[] seg, int i, Object o) {
        seg[i] = o;
    }

    @Override
    protected int getSize(Object[] objects) {
        return objects.length;
    }
}
