package com.home.sorting;

import com.home.Utils;

import java.util.Arrays;

public class MergeSort implements Sortable {
    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int[] sortedArray = this.sort(array, 0, array.length);

        System.arraycopy(sortedArray, 0, array, 0, array.length);
    }

    private int[] sort(int[] array, int lo, int hi) {
        // basis of recursion
        if (hi - lo == 1) {
            return new int[]{array[lo]};
        }

        // find middle of the array
        int middle = lo + ((hi - lo) >> 1);

        // 'divide and conquer'
        int[] leftSubArray = this.sort(array, lo, middle);
        int[] rightSubArray = this.sort(array, middle, hi);

        // merge together
        return this.merge(leftSubArray, rightSubArray);
    }

    private int[] merge(int[] leftSubArray, int[] rightSubArray) {
        int i = 0, i1 = 0, i2 = 0;
        int[] result = new int[leftSubArray.length + rightSubArray.length];

        while (i1 + i2 < result.length) {
            if (i1 < leftSubArray.length && i2 < leftSubArray.length) {
                result[i++] = leftSubArray[i1] < rightSubArray[i2] ? leftSubArray[i1++] : rightSubArray[i2++];
            } else {
                result[i++] = i1 == leftSubArray.length ? rightSubArray[i2++] : leftSubArray[i1++];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] array = Utils.generateArrayOfIntegers(20, 100);

        Sortable sb = new MergeSort();
        sb.sort(array);

        System.out.println(Arrays.toString(array));
    }
}
