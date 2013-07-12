package com.chris.algo.search;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 13-7-1
 * Time: 下午5:51
 */
public class ISunday {
    public int sundaySearch(String text, String pattern) {
        int ti = 0;
        int pi = 0;
        int pe = pattern.length() - 1;
        int tLen = text.length();
        int pLen = pattern.length();
        char[] ctext = text.toCharArray();
        char[] cpattern = pattern.toCharArray();
        int index = ti;

        while (ti < tLen && pi < pLen) {
            if (ctext[ti] == cpattern[pi]) {//如果找到一个相同的，则继续比较下一个字符
                ti++;
                pi++;
            } else {
                // 查找pattern中是否有 text[pe+1]字符 ，如果有则pattern[k]就是这个字符
                int k = pattern.lastIndexOf(ctext[pe + 1]);

                // 计算字符pattern[k]前面的字符个数
                // text需要移动的长度 ，也即是计算的新位置
                ti = ti + pLen - k;
                // 保留下一次要计算的起始位置
                pe = ti + pLen - 1;
                index = ti;
                pi = 0;
            }
        }

        if (ti <= tLen) {
            return index;
        }
        return -1;
    }

    public static void main(String[] args) {
        ISunday n = new ISunday();
        System.out.print(n.sundaySearch("asiachris desk china", "chi"));
    }
}

