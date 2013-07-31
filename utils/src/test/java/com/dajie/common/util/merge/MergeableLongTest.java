package com.dajie.common.util.merge;

import com.dajie.common.util.merge.impl.MergeableLong;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: litao
 * Date: 13-3-23
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
public class MergeableLongTest {
    @Test
    public void typeTest() {
        Mergeable mergeable = new MergeableLong();
        Assert.assertEquals(Long.class, mergeable.getMergeResult().getClass());
    }

    @Test
    public void defaultValueTest() {
        Mergeable mergeable = new MergeableLong();
        Assert.assertNotNull(mergeable.getMergeResult());
    }

    @Test
    public void mergeTest() {
        Mergeable<Long> mergeable = new MergeableLong();
        mergeable.addData(1l);
        mergeable.addData(1l);
        mergeable.addData(1l);
        mergeable.addData(1l);
        mergeable.addData(1l);
        mergeable.addData(1l);
        Assert.assertEquals(6, mergeable.getMergeResult().intValue());

    }
}
