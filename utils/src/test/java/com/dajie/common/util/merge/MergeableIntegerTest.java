package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.MergeableInteger;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: litao
 * Date: 13-3-23
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class MergeableIntegerTest {
    @Test
    public void typeTest() {
        Mergeable mergeable = new MergeableInteger();
        Assert.assertEquals(Integer.class, mergeable.getMergeResult().getClass());
    }

    @Test
    public void defaultValueTest() {
        Mergeable mergeable = new MergeableInteger();
        Assert.assertNotNull(mergeable.getMergeResult());
    }

    @Test
    public void mergeTest() {
        Mergeable<Integer> mergeable = new MergeableInteger();
        mergeable.addData(1);
        mergeable.addData(1);
        mergeable.addData(1);
        mergeable.addData(1);
        mergeable.addData(1);
        mergeable.addData(1);
        Assert.assertEquals(6, mergeable.getMergeResult().intValue());

    }
}
