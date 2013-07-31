package com.dajie.common.util.segment;

import com.dajie.common.util.segment.impl.SegmentableArray;
import com.dajie.common.util.segment.impl.SegmentableCollection;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: litao
 * Date: 13-3-23
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSegmentableFactoryTest {
    private final SegmentableFactory factory = new SimpleSegmentableFactory();

    @Test
    public void typeTest() {
        Assert.assertEquals(SegmentableArray.class, this.factory.getSegmentableForType(new Object[0].getClass(), 10).getClass());
        Object[] testData1 = new Object[]{1, 2, 3, 4, 5};
        for (Object o : this.factory.getSegmentableForType(testData1.getClass(), 1).segment(testData1)) {
            Assert.assertEquals(testData1.getClass(), o.getClass());
        }
        Assert.assertEquals(SegmentableCollection.class, this.factory.getSegmentableForType(Collection.class, 1).getClass());
        List testData2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        for (Object o : this.factory.getSegmentableForType(List.class, 1).segment(testData2)) {
            Assert.assertEquals(ArrayList.class, o.getClass());
        }
        ArrayList testData3 = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6));
        for (Object o : this.factory.getSegmentableForType(testData3.getClass(), 1).segment(testData3)) {
            Assert.assertEquals(testData3.getClass(), o.getClass());
        }
        LinkedList testData4 = new LinkedList(Arrays.asList(1, 2, 3, 4, 5, 6));
        for (Object o : this.factory.getSegmentableForType(testData4.getClass(), 1).segment(testData3)) {
            Assert.assertEquals(testData4.getClass(), o.getClass());
        }

        Set testData5 = new HashSet(Arrays.asList(1, 2, 3, 4, 5, 6));
        for (Object o : this.factory.getSegmentableForType(testData5.getClass(), 1).segment(testData3)) {
            Assert.assertEquals(testData5.getClass(), o.getClass());
        }

        HashSet testData6 = new HashSet(Arrays.asList(1, 2, 3, 4, 5, 6));
        for (Object o : this.factory.getSegmentableForType(testData6.getClass(), 1).segment(testData3)) {
            Assert.assertEquals(testData6.getClass(), o.getClass());
        }

        TreeSet testData7 = new TreeSet(Arrays.asList(1, 2, 3, 4, 5, 6));
        for (Object o : this.factory.getSegmentableForType(testData7.getClass(), 1).segment(testData3)) {
            Assert.assertEquals(testData7.getClass(), o.getClass());
        }

    }
}
