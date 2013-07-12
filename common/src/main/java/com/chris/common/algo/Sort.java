package com.chris.common.algo;

/**
 * 排序测试类 排序算法的分类如下： 1.插入排序（直接插入排序、折半插入排序、希尔排序）； 2.交换排序（冒泡泡排序、快速排序）；
 * 3.选择排序（直接选择排序、堆排序）； 4.归并排序； 5.基数排序。
 * <p/>
 * 关于排序方法的选择：
 * (1)若n较小(如n≤50)，可采用直接插入或直接选择排序。
 * 　当记录规模较小时，直接插入排序较好；否则因为直接选择移动的记录数少于直接插人，应选直接选择排序为宜。
 * (2)若文件初始状态基本有序(指正序)，则应选用直接插人、冒泡或随机的快速排序为宜；
 * (3)若n较大，则应采用时间复杂度为O(nlogn)的排序方法：快速排序、堆排序或归并排序。
 */
public class Sort {
    public static void main(String[] args) {
        int[] arr2 = {4, 3, 7, 1, 6};
        insertSort(arr2);
        int[] arr3 = {4, 3, 7, 1, 6};
        //bubbleSort(arr3);
        int[] arr4 = {4, 3, 7, 1, 6};
        //quickSortAsc(arr4,0,arr4.length-1);
        int[] arr5 = {4, 3, 7, 1, 6};
        //selectSort(arr5);
        for (int i = 0; i < 10; i++) {
            System.out.println("f(" + i + ") = " + f(i));
        }
    }

    /*
      * 插入排序原理：前面的都是有序的(x1,x2...xk)，在添加一个数据e时，从最后一个到第一个比较(从xk到x1)，直到找到合适的位置（比如，从小到大排序
      * ——直到第一个比e小）。 时间复杂度：平均O(n2)，最坏情况O(n2)。
      */
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1); // 交换
                }
            }
        }
    }

    /*
      * 冒泡排序原理：把最大的（或最小的）冒出来。从底端（即index=0）向上面紧挨着的比较，大的（或小的）冒上来（交换），
      * 直到冒到“顶”（顶的解释：没有冒过的，即是冒一趟，顶就矮一层。）。然后继续下一趟冒，直到底端不是“顶”。
      * 时间复杂度：平均O(n2)，最坏情况O(n2)。
      */
    public static void bubbleSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /*
      * 简选择排序原理：
      * 从无序区域里选择最小（或最大）的添加到有序区里。一开始所有的数据都是在无序区里，排好一个，无序区个数就少一个。
      * 时间复杂度：平均O(n2)，最坏情况O(n2)。
      */
    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];

            for (int j = i + 1; j < arr.length; j++) {// 在后面, 找最小的.
                if (arr[j] < min) {
                    minIndex = j;
                    min = arr[j];
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    /*
      * 堆排序是高效的排序方法。没有最坏情况（即与平均情况一样），空间占用又小，综合效率比快速排序还好。
      * 堆排序原理：
      * 1、从数据集中构建大顶堆（或小顶堆）
      * 2、堆顶与最后一个数据交换，然后维护堆顶使它还是大顶堆（或小顶堆） 3、重复2的过程，直到剩下一个数据
      * 时间复杂度：平均O(nlog2n)，最坏情况O(nlog2n)。
      */
    public static void heapSort(int[] arr) {
    }

    /**
     * @param low
     */
/*快速排序的具体实现，排正序
 *快速排序（Quicksort）是对冒泡排序的一种改进。由C. A. R. Hoare在1962年提出。它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，
 *其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 *算法过程
		设要排序的数组是A[0]……A[N-1]，首先任意选取一个数据（通常选用第一个数据）作为关键数据，然后将所有比它小的数都放到它前面，所有比它大的数都放到它后面，这个过程称为一趟快速排序。
		一趟快速排序的算法是：
　　1）设置两个变量I、J，排序开始的时候：I=1，J=N；
　　2）以第一个数组元素作为关键数据，赋值给X，即 X=A[1]；
　　3）从J开始向前搜索，即由后开始向前搜索（J=J-1），找到第一个小于X的值，让该值与X交换；
　　4）从I开始向后搜索，即由前开始向后搜索（I=I+1），找到第一个大于X的值，让该值与X交换；
　　5）重复第3、4步，直到 I=J；
　　例如：待排序的数组A的值分别是：（初始关键数据：X=49）
　　A[0] 、 A[1]、 A[2]、 A[3]、 A[4]、 A[5]、 A[6]：
　　49 38 65 97 76 13 27
　　进行第一次交换后： 27 38 65 97 76 13 49
　　( 按照算法的第三步从后面开始找)
　　进行第二次交换后： 27 38 49 97 76 13 65
　　( 按照算法的第四步从前面开始找>X的值，65>49,两者交换，此时：I=3 )
　　进行第三次交换后： 27 38 13 97 76 49 65
　　( 按照算法的第五步将又一次执行算法的第三步从后开始找
　　进行第四次交换后： 27 38 13 49 76 97 65
　　( 按照算法的第四步从前面开始找大于X的值，97>49,两者交换，此时：J=4 )
　　此时再执行第三步的时候就发现I=J，从而结束一躺快速排序，那么经过一趟快速排序之后的结果是：27 38 13 49 76 97 65，即所以大于49的数全部在49的后面，所以小于49的数全部在49的前面。
　　快速排序就是递归调用此过程——在以49为中点分割这个数据序列，分别对前面一部分和后面一部分进行类似的快速排序，从而完成全部数据序列的快速排序，最后把此数据序列变成一个有序的序列，根据这种思想对于上述数组A的快速排序的全过程如图6所示：
　　初始状态 {49 38 65 97 76 13 27}
　　进行一次快速排序之后划分为 {27 38 13} 49 {76 97 65}
　　分别对前后两部分进行快速排序 :
				{27 38 13} 经第三步和第四步交换后变成 {13 27 38} 完成排序
　　			{76 97 65} 经第三步和第四步交换后变成 {65 76 97} 完成排序
 */
    public static void quickSortAsc(int[] arr, int low, int hight) {
        if (low < hight) { // 这个条件用来结束递归
            int pre = low;
            int bac = hight;
            int value = arr[low];//任意

            while (pre < bac) {
                while (pre < bac && arr[bac] > value) {
                    bac--;// 从右向左找第一个小于x的数
                }

                if (pre < bac) {
                    arr[pre] = arr[bac];
                    pre++;
                }

                while (pre < bac && arr[pre] < value) {
                    pre++; // 从左向右找第一个大于x的数
                }

                if (pre < bac) {
                    arr[bac] = arr[pre];
                    bac--;
                }

                arr[pre] = value;

                quickSortAsc(arr, low, pre - 1);
                quickSortAsc(arr, pre + 1, hight);
            }
        }
    }

    public static void swap(int[] arr, int src, int dest) {
        int tmp = arr[dest];
        arr[dest] = arr[src];
        arr[src] = tmp;
    }

    /*
    * 实现Fibonacci数列算法:0,1，1，2，3，4，5，8,13
    */
    public static int f(int n) {
        if (0 == n) {
            return 0;
        } else if (1 == n) {
            return 1;
        } else {
            return f(n - 1) + f(n - 2);
        }
    }

    public static void fibonacci(int number) {
        int nLeft = 0;
        int nRight = 1;
        System.out.print(nRight);

        for (int i = 0; i < number; i++) {
            int sum = nLeft + nRight;
            System.out.print("  " + sum);
            nLeft = nRight;
            nRight = sum;
        }
    }

    public static void printArr(int[] arr) {
        System.out.println("\n sort end:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
