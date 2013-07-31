package com.dajie.common.util.segment.impl;

import com.dajie.common.util.segment.Segmentable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * User:tao.li
 * Date: 13-3-18
 * Time: 下午8:38
 */
public abstract class AbstractSegmentable<T> implements Segmentable<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSegmentable.class);
    private final Creator<T> creator;
    private final int countPerSeg;

    protected AbstractSegmentable(Creator<T> creator, int countPerSeg) {
        this.creator = creator;
        this.countPerSeg = countPerSeg;
    }

    @Override
    public Collection<T> segment(T t) {
        if (t == null || isEmpty(t)) {
            LOGGER.info("input for segment is null or empty.");
            return Collections.emptyList();
        }
        int size = getSize(t);
        int segCount = (size + countPerSeg - 1) / countPerSeg;
        if (segCount == 1) {
            return Arrays.asList(t);
        }
        Collection<T> result = new ArrayList<T>(segCount);
        int index = 0;
        int segIndex = 0;
        T seg = null;
        Iterator iterator = getIteratorFor(t);
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (index++ % countPerSeg == 0) {
                int needCreateCount = segIndex++ == segCount - 1 ? size - this.countPerSeg * (segCount - 1) : this.countPerSeg;
                seg = creator.create(needCreateCount);
                result.add(seg);
            }
            addElement(seg, index % this.countPerSeg, o);
        }
        return result;
    }

    protected abstract Iterator getIteratorFor(T t);

    protected abstract void addElement(T seg, int i, Object o);

    private boolean isEmpty(T t) {
        return getSize(t) == 0;
    }

    protected abstract int getSize(T t);

    public interface Creator<T> {
        T create(int size);
    }
}
