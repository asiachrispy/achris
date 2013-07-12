package com.chris.common.clazz;

/**
 * User: zhong.huang
 * Date: 12-10-12
 * Time: 下午4:21
 */

public class AssignableFrom
{
    public static void main(String[] args)
    {
        // Get the Class object associated with this class.
        com.bplan.cls.AssignableFrom program = new com.bplan.cls.AssignableFrom();
        Class progClass = program.getClass();

        // Get the Class object associated with BaseClass.
        Class baseClassClass = com.bplan.cls.BaseClass.class;

        // Is Program assignable from BaseClass?
        boolean check1 = progClass.isAssignableFrom(baseClassClass);
        System.out.println("Program is assignable from BaseClass? " +
            check1);

        // Is BaseClass assignable from Program?
        boolean check2 = baseClassClass.isAssignableFrom(progClass);
        System.out.println("BaseClass is assignable from Program? " +
            check2);
    }
}

class BaseClass extends AssignableFrom
{
    public BaseClass()
    {
    }
}