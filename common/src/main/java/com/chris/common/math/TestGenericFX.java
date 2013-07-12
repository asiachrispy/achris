package com.chris.common.math;

/**
 * 在此例中，GenericFX类就是要定义的泛型类，类型变量E是泛型类的类型参数，
 * 我们可以使用GenericFX<E>这样的类型名来声明一个泛型类。如
 * <p/>
 * GenericFX<E> fx = new GenericFX<E>(),其中E就是具体的类型，
 * 下面看一个应用泛型类的例子.
 *
 * @author asiachris
 */
public class TestGenericFX{

    public void main(String[] args)
    {
        GenericFX<Integer> f1 = new GenericFX<Integer>();

        GenericFX<Boolean> f2 = new GenericFX<Boolean>();

        f1.setFX(new Integer(10));

        System.out.println(f1.getFX());//

        f2.setFX(new Boolean(true));

        System.out.println(f2.getFX());//

    }

}
