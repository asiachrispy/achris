package com.dajie.common.util.segment;

import com.dajie.common.util.segment.impl.AbstractSegmentable;
import com.dajie.common.util.segment.impl.SegmentableArray;
import com.dajie.common.util.segment.impl.SegmentableCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;

/**
 * User:tao.li
 * Date: 13-3-19
 * Time: 上午10:35
 */
public class SimpleSegmentableFactory implements SegmentableFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSegmentableFactory.class);

    @Override
    public Segmentable getSegmentableForType(Class cls, int sizePerSeg) {
        if (cls.isArray()) {
            final Class elementCls = cls.getComponentType();
            return new SegmentableArray(new AbstractSegmentable.Creator<Object[]>() {

                @Override
                public Object[] create(int size) {
                    return (Object[]) Array.newInstance(elementCls, size);
                }
            }, sizePerSeg);
        }
        if (ArrayList.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new ArrayList(size);
                }
            }, sizePerSeg);
        }
        if (LinkedList.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new LinkedList();
                }
            }, sizePerSeg);
        }
        if (HashSet.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new HashSet();
                }
            }, sizePerSeg);
        }
        if (TreeSet.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new TreeSet();
                }
            }, sizePerSeg);
        }
        if (LinkedHashSet.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new LinkedHashSet();
                }
            }, sizePerSeg);
        }

        if (List.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new ArrayList(size);
                }
            }, sizePerSeg);
        }
        if (Set.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new HashSet(size);
                }
            }, sizePerSeg);
        }
        if (Collection.class == cls) {
            return new SegmentableCollection(new AbstractSegmentable.Creator<Collection>() {
                @Override
                public Collection create(int size) {
                    return new ArrayList(size);
                }
            }, sizePerSeg);
        }
        LOGGER.error("failed to find segment for type {}.", cls);
        return null;
    }
}
