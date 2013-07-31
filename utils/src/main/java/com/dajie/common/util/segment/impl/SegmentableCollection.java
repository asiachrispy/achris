package com.dajie.common.util.segment.impl;

import com.dajie.common.util.segment.Segmentable;

import java.util.Collection;
import java.util.Iterator;

/**
 * User:tao.li
 * Date: 13-3-18
 * Time: 下午8:55
 */
public class SegmentableCollection extends AbstractSegmentable<Collection> implements Segmentable<Collection> {

    public SegmentableCollection(Creator<Collection> creator, int countPerSeg) {
        super(creator, countPerSeg);
    }

    @Override
    protected Iterator getIteratorFor(Collection collection) {
        return collection.iterator();
    }

    @Override
    protected void addElement(Collection seg, int i, Object o) {
        seg.add(o);
    }

    @Override
    protected int getSize(Collection list) {
        return list.size();
    }
}
