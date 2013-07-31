package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.MergeableSet;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: litao
 * Date: 13-3-23
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class MergeableSetTest {
    @Test
    public void typeTest() {
        Mergeable mergeable = new MergeableSet();
        Assert.assertEquals(HashSet.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableSet(new HashSet());
        Assert.assertEquals(HashSet.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableSet(new TreeSet());
        Assert.assertEquals(TreeSet.class, mergeable.getMergeResult().getClass());
    }

    @Test
    public void defaultValueTest() {
        Mergeable mergeable = new MergeableSet();
        Assert.assertNotNull(mergeable.getMergeResult());
    }

    @Test
    public void mergeTest() {
        Mergeable<Set> mergeable = new MergeableSet();
        mergeable.addData(createSet(1));
        mergeable.addData(createSet(2));
        mergeable.addData(createSet(3));
        mergeable.addData(createSet(4));
        mergeable.addData(createSet(5));
        mergeable.addData(createSet(6));
        Set result = mergeable.getMergeResult();
        Assert.assertEquals(6, result.size());
        Assert.assertTrue(result.contains(1));
        Assert.assertTrue(result.contains(2));
        Assert.assertTrue(result.contains(3));
        Assert.assertTrue(result.contains(4));
        Assert.assertTrue(result.contains(5));
        Assert.assertTrue(result.contains(6));

    }

    private Set<Integer> createSet(int value) {
        Set<Integer> result = new HashSet<Integer>();
        result.add(value);
        return result;
    }
}
