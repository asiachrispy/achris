package com.dajie.common.util.segment;

/**
 * User:tao.li
 * Date: 13-3-19
 * Time: 上午10:32
 */
public interface SegmentableFactory {
    Segmentable getSegmentableForType(Class cls, int sizePerSeg);
}
