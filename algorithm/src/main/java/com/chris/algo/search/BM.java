package com.chris.algo.search;

/**
 * 字符串对齐
 *
 * 从字符串右边开始比较
 */
public class BM extends PatternMatcher {

    static int[] patternArray;
    int offset = 0;

    public BM(String pattern) {
        super(pattern);
        patternArray = new int[256];
        for (int i = 0; i < patternArray.length; i++) {
            patternArray[i] = -1;
        }
        char[] tmp = pattern.toCharArray();
        for (int j = tmp.length - 1; j >= 0; j--) {
            if (patternArray[tmp[j]] == -1) {
                patternArray[tmp[j]] = j; // 记录字符出现的位置 a字符出现在第3（index=2）位置
            }
        }
    }

    public int match(String text) {
        int i = getPattern().length() - 1;
        int j = i;
        char t, p;
        while (i < text.length()) {
            t = text.charAt(i);
            p = getPattern().charAt(j);
            System.out.println("text[" + i + "]=" + t + "; pattern[" + j + "]=" + p);
            if (t == p) { // 比较最后以后一个字符是否相等
                if (j == 0) {//如果已经比较到第一字符，则结束
                    return i;
                } else {
                    i--;
                    j--;
                }
            } else {
                offset = patternArray[t] + 1;// 不相等则继续往后移动 来比较
                i = i + getPattern().length() - Math.min(j, offset);
                j = getPattern().length() - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        BM pattern = new BM("HKas");
        String text = "chrisasia,helloHKas,hangkong,HKCHINA";
        System.out.println(pattern.match(text));
    }
}