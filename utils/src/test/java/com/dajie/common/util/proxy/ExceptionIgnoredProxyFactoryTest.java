package com.dajie.common.util.proxy;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: tao.li
 * Date: 13-6-21
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionIgnoredProxyFactoryTest {
    @Test
    public void testSingleCreate() throws Exception {
        Test1 test = ExceptionIgnoredProxyFactory.getInstance().create(new Test1());
        Assert.assertEquals(test.get(10), 10);
        Assert.assertEquals(test.getWithException(10), -1);
    }

    @Test
    public void testSingleFinalCreate() throws Exception {
        Test2 test = ExceptionIgnoredProxyFactory.getInstance().create(new Test2());
        Assert.assertEquals(test.get(10), 10);
        try {
            test.getWithException(10);
            Assert.assertFalse(true);
        } catch (Exception e) {

        }

    }

    @Test
    public void testInterfaceCreate() throws Exception {
        ITest test = ExceptionIgnoredProxyFactory.getInstance().create(new Test3());
        Assert.assertEquals(test.get(10), 10);
        Assert.assertEquals(test.getWithException(10), -1);
    }

    @Test
    public void testInterfaceFinalCreate() throws Exception {
        ITest test = ExceptionIgnoredProxyFactory.getInstance().create(new Test4());
        Assert.assertEquals(test.get(10), 10);
        Assert.assertEquals(test.getWithException(10), -1);
    }

    @Test(expected = ClassCastException.class)
    public void testInterfaceFinalCreate2() throws Exception {
        Test4 test = ExceptionIgnoredProxyFactory.getInstance().create(new Test4());
    }


    public static class Test1 {
        public int get(int i) {
            return i;
        }

        public int getWithException(int i) {
            throw new RuntimeException();
        }
    }

    public static final class Test2 {
        public int get(int i) {
            return i;
        }

        public int getWithException(int i) {
            throw new RuntimeException();
        }
    }

    public static interface ITest {
        public int get(int i);

        public int getWithException(int i);
    }

    public static class Test3 implements ITest {
        public int get(int i) {
            return i;
        }

        public int getWithException(int i) {
            throw new RuntimeException();
        }
    }

    public static final class Test4 implements ITest {
        public int get(int i) {
            return i;
        }

        public int getWithException(int i) {
            throw new RuntimeException();
        }
    }
}
