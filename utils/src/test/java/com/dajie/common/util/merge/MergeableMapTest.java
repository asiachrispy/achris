package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.MergeableMap;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: litao
 * Date: 13-3-23
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class MergeableMapTest {
    @Test
    public void typeTest() {
        Mergeable mergeable = new MergeableMap();
        Assert.assertEquals(HashMap.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableMap(new HashMap());
        Assert.assertEquals(HashMap.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableMap(new TreeMap());
        Assert.assertEquals(TreeMap.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableMap(new LinkedHashMap());
        Assert.assertEquals(LinkedHashMap.class, mergeable.getMergeResult().getClass());
    }

    @Test
    public void defaultValueTest() {
        Mergeable mergeable = new MergeableMap();
        Assert.assertNotNull(mergeable.getMergeResult());
    }

    @Test
    public void mergeTest() {
        Mergeable<Map> mergeable = new MergeableMap();
        mergeable.addData(createMap(1, 1));
        mergeable.addData(createMap(2, 2));
        mergeable.addData(createMap(3, 3));
        mergeable.addData(createMap(4, 4));
        mergeable.addData(createMap(5, 5));
        mergeable.addData(createMap(6, 6));
        Map result = mergeable.getMergeResult();
        Assert.assertEquals(6, result.size());
        Assert.assertTrue(result.containsKey(1));
        Assert.assertTrue(result.get(1).equals(1));
        Assert.assertTrue(result.containsKey(2));
        Assert.assertTrue(result.get(2).equals(2));
        Assert.assertTrue(result.containsKey(3));
        Assert.assertTrue(result.get(3).equals(3));
        Assert.assertTrue(result.containsKey(4));
        Assert.assertTrue(result.get(4).equals(4));
        Assert.assertTrue(result.containsKey(5));
        Assert.assertTrue(result.get(5).equals(5));
        Assert.assertTrue(result.containsKey(6));
        Assert.assertTrue(result.get(6).equals(6));

    }

    private Map<Integer, Integer> createMap(int key, int value) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        result.put(key, value);
        return result;
    }
}
