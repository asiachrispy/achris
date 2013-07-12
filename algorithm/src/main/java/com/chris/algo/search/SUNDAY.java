package com.chris.algo.search;

public class SUNDAY {

    public int findChr(String str, String sub) {
        int str_length = str.length();
        int sub_length = sub.length();

        int find_count = 0;
        int start = 0;
        int moveNum = 0;

        if ( str_length < sub_length) {
            return find_count;
        }
        String s_temp = null;
        while (start + sub_length <= str_length) {
            moveNum++;
            s_temp = str.substring(start, sub_length);
            if (s_temp.equals(sub)) {
                find_count++;
                start = start + sub_length;
                continue;
            } else {

                start= findPos(str, sub, start, sub_length);
            }
        }
        return find_count;
    }

    //找字符在字符串(不算最后一个字符)的位置(倒数)
    //没找到返回fin_length,找到返回位置
    /// <summary>
    /// 找字符在字符串(不算最后一个字符)的位置(倒数);没找到返回str.length,找到返回位置
    /// </summary>
    /// <param name="str"></param>
    /// <param name="find"></param>
    /// <param name="pos"></param>
    /// <param name="fin_length"></param>
    /// <returns></returns>
    public int findPos(String str, String find, int pos, int fin_length) {
        int returnPos = str.length();
        char[] Schr = str.toCharArray();

        if ((pos + fin_length) < str.length()) {
            char chrFind = Schr[pos + fin_length];//要找的字符
            if (fin_length >= 1) {
                if (find.lastIndexOf(chrFind) > -1) {
                    returnPos = pos + fin_length - find.lastIndexOf(chrFind);
                } else {
                    returnPos = pos + fin_length + 1;
                }
            }
        }
        return returnPos;
    }
}
