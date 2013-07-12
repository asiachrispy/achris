package com.chris.common.clazz;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 12-10-12
 * Time: 下午4:29
 */
public class IsArray {

    public static void main(String[] args)
    {
        // Create a String object and an array of String objects.
        String str = "Hello, World!";
        String[] strArr = new String[] {
            "Hello", "World"
        };

        // Is the String class an array?
        Class strClass = str.getClass();
        boolean strIsArray = strClass.isArray();
        if (strIsArray)
        {
            System.out.println("Class " + strClass.getName() +
                " is an array.");
        }
        else
        {
            System.out.println("Class " + strClass.getName() +
                " is NOT an array.");
        }

        // Is the String array class an array?
        Class strArrClass = strArr.getClass();
        boolean strArrIsArray = strArrClass.isArray();
        if (strArrIsArray)
        {
            System.out.println("Class " + strArrClass.getName() +
                " is an array.");
        }
        else
        {
            System.out.println("Class " + strArrClass.getName() +
                " is NOT an array.");
        }
    }
}

/*
Output:
Class java.lang.String is NOT an array.
Class [Ljava.lang.String; is an array.
*/
