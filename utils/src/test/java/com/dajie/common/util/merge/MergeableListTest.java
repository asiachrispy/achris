package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.MergeableList;
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
public class MergeableListTest {
    @Test
    public void typeTest() {
        Mergeable mergeable = new MergeableList();
        Assert.assertEquals(ArrayList.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableList(new LinkedList());
        Assert.assertEquals(LinkedList.class, mergeable.getMergeResult().getClass());
        mergeable = new MergeableList(new ArrayList());
        Assert.assertEquals(ArrayList.class, mergeable.getMergeResult().getClass());
    }

    @Test
    public void defaultValueTest() {
        Mergeable mergeable = new MergeableList();
        Assert.assertNotNull(mergeable.getMergeResult());
    }

    @Test
    public void mergeTest() {
        Mergeable<List> mergeable = new MergeableList();
        mergeable.addData(createList(1));
        mergeable.addData(createList(2));
        mergeable.addData(createList(3));
        mergeable.addData(createList(4));
        mergeable.addData(createList(5));
        mergeable.addData(createList(6));
        List result = mergeable.getMergeResult();
        Assert.assertEquals(6, result.size());
        Assert.assertTrue(result.contains(1));
        Assert.assertTrue(result.contains(2));
        Assert.assertTrue(result.contains(3));
        Assert.assertTrue(result.contains(4));
        Assert.assertTrue(result.contains(5));
        Assert.assertTrue(result.contains(6));

    }

    private List<Integer> createList(int value) {
        List<Integer> result = new ArrayList<Integer>();
        result.add(value);
        return result;
    }
}
