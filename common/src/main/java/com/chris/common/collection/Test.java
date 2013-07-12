package com.chris.common.collection;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * User: zhong.huang
 * Date: 12-8-21
 * Time: 下午3:10
 */
public class Test {
    public static void main(String[] args) {
        List a = new LinkedList();
        a = new ArrayList();

        Set s = new HashSet();
        s = new LinkedHashSet();
        s = new TreeSet();

        Map m = new HashMap();
        m = new LinkedHashMap();
//
//        TreeSet ts = new TreeSet();
//        TreeMap tm = new TreeMap();
//        ConcurrentHashMap  cm = new ConcurrentHashMap();

//        Map m1 = new HashMap();
//        m1.put("Ankit", "8");
//        m1.put("Kapil", "31");
//        m1.put("Saurabh", "12");
//        m1.put("Apoorva", "14");
//        System.out.println();
//        System.out.println("Elements of Map");
//        System.out.print(m1);
//
//        String[] arr = {"1","a","123","abc"};
//        List list = Arrays.asList(arr); //Adapter//Adapter
//        StringBuffer sb = new StringBuffer();
//        sb.append("a");//Builder (recognizeable by creational methods returning the instance itself)
            System.out.println(5%2);

          ArrayBlockingQueue que = new ArrayBlockingQueue(10);

        Collections.sort(a);

         System.gc();
//       bMotion();

    }

    public static void bMotion()
    {
        int a = -15;
        int b = 2;
        int x = a << b;
        int y = a >> b;
        int z = a >>> b;
        System.out.println(a + "<<" + b + "=" + x );
        System.out.println(a + ">>" + b + "=" + y);
        System.out.println(a + ">>>" + b + "=" + z);
    }
    
}
