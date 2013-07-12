package com.chris.common.clazz;

/**
 * User: zhong.huang
 * Date: 12-10-12
 * Time: 下午4:36
 */
public class Component {
    public static void main(String[] args)
    {
        // Create a String object and an array of String objects.
        String str = "Hello, World!";
        String[] strArr = new String[] {
            "Hello", "World"
        };

        // Get the component type for the String.
        Class strClass = str.getClass();
        Class strClassComp = strClass.getComponentType();
        if (strClassComp != null)
        {
            System.out.println("String component type: " +
                strClassComp.getName());
        }
        else
        {
            System.out.println("String component type is null.");
        }

        // Get the component type for the array of String objects.
        Class strArrClass = strArr.getClass();
        Class strArrClassComp = strArrClass.getComponentType();
        if (strArrClassComp != null)
        {
            System.out.println("String array component type: " +
                strArrClassComp.getName());
        }
        else
        {
            System.out.println("String array component type " +
                "is null.");
        }
    }
}

/*
Output:
String component type is null.
String array component type: java.lang.String
*/
