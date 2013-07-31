package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: litao
 * Date: 13-3-23
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMergeableFactoryTest {
    private MergeableFactory factory = new SimpleMergeableFactory();

    @Test
    public void typeTest() {
        Assert.assertEquals(MergeableInteger.class, this.factory.getMergeForType(Integer.class).getClass());
        Assert.assertEquals(MergeableInteger.class, this.factory.getMergeForType(Integer.TYPE).getClass());

        Assert.assertEquals(MergeableLong.class, this.factory.getMergeForType(Long.class).getClass());
        Assert.assertEquals(MergeableLong.class, this.factory.getMergeForType(Long.TYPE).getClass());

        Assert.assertEquals(MergeableSet.class, this.factory.getMergeForType(Set.class).getClass());
        Assert.assertEquals(MergeableSet.class, this.factory.getMergeForType(HashSet.class).getClass());
        Assert.assertEquals(HashSet.class, this.factory.getMergeForType(HashSet.class).getMergeResult().getClass());
        Assert.assertEquals(MergeableSet.class, this.factory.getMergeForType(TreeSet.class).getClass());
        Assert.assertEquals(TreeSet.class, this.factory.getMergeForType(TreeSet.class).getMergeResult().getClass());
        Assert.assertEquals(MergeableSet.class, this.factory.getMergeForType(LinkedHashSet.class).getClass());
        Assert.assertEquals(LinkedHashSet.class, this.factory.getMergeForType(LinkedHashSet.class).getMergeResult().getClass());

        Assert.assertEquals(MergeableList.class, this.factory.getMergeForType(List.class).getClass());
        Assert.assertEquals(MergeableList.class, this.factory.getMergeForType(ArrayList.class).getClass());
        Assert.assertEquals(ArrayList.class, this.factory.getMergeForType(ArrayList.class).getMergeResult().getClass());
        Assert.assertEquals(MergeableList.class, this.factory.getMergeForType(LinkedList.class).getClass());
        Assert.assertEquals(LinkedList.class, this.factory.getMergeForType(LinkedList.class).getMergeResult().getClass());

        Assert.assertEquals(MergeableMap.class, this.factory.getMergeForType(Map.class).getClass());
        Assert.assertEquals(MergeableMap.class, this.factory.getMergeForType(HashMap.class).getClass());
        Assert.assertEquals(HashMap.class, this.factory.getMergeForType(HashMap.class).getMergeResult().getClass());
        Assert.assertEquals(MergeableMap.class, this.factory.getMergeForType(TreeMap.class).getClass());
        Assert.assertEquals(TreeMap.class, this.factory.getMergeForType(TreeMap.class).getMergeResult().getClass());
        Assert.assertEquals(MergeableMap.class, this.factory.getMergeForType(LinkedHashMap.class).getClass());
        Assert.assertEquals(LinkedHashMap.class, this.factory.getMergeForType(LinkedHashMap.class).getMergeResult().getClass());

        Assert.assertNotNull(this.factory.getMergeForType(Collection.class));
        Assert.assertNotNull(Collection.class.isAssignableFrom(this.factory.getMergeForType(Collection.class).getMergeResult().getClass()));


    }

}
