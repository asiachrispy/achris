package com.chris.common.collection;
import java.util.HashMap;

/**
 * User: zhong.huang
 * Date: 12-8-21
 * Time: 下午5:32
 */
public class FailFast {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("a", "a");
        map.put("b", "b");
        java.util.Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            it.next();
//            map.put("", "");
            it.remove();
            System.out.println(map.size());
        }
    }
}
