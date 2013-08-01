package com.chris.algo.review;

public class Merge {
    //递归分成小部分
    public void merge_sort(int[] arrays, int start, int end) {
        if (start < end) {
            int m = (start + end) / 2;
            merge_sort(arrays, start, m);
            merge_sort(arrays, m + 1, end);
            combin_arrays(arrays, start, m, end);
        }
    }

    //合并数组
    public void combin_arrays(int[] arrays, int start, int m, int end) {
        int length = end - start + 1;
        int temp[] = new int[length];//用来存放比较的数组，用完复制回到原来的数组
        int i = start;
        int j = m + 1;
        int c = 0;
        while (i <= m && j <= end) {
            if (arrays[i] < arrays[j]) {
                temp[c] = arrays[i];
                i++;
                c++;
            } else {
                temp[c] = arrays[j];
                j++;
                c++;
            }
        }
        while (i <= m) {
            temp[c] = arrays[i];
            i++;
        }
        while (j <= end) {
            temp[c] = arrays[j];
            j++;
        }
        c = 0;
        for (int t = start; t <= end; t++, c++) {
            arrays[t] = temp[c];
        }
        snp(arrays);
    }

    //打印数组
    public void snp(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Merge m = new Merge();
        int a[] = {5, 4, 10, 8, 7, 9};
        m.merge_sort(a, 0, a.length - 1);

    }
}