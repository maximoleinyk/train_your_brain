package com.home.sorting;

import com.home.Utils;

import java.util.Arrays;

public class ShellSort implements Sortable {
    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int gap = array.length >> 1;

        while (gap > 0) {
            for (int i = 0; i < array.length; i++) {
                for (int j = i + gap; j < array.length; j += gap) {
                    if (array[j] < array[j - gap]) {
                        for (int k = j; k > i; k -= gap) {
                            if (array[k] < array[k - gap]) {
                                Utils.swap(array, k, k - gap);
                            } else {
                                break;
                            }
                        }
                    }
                }
                if (gap == 1) {
                    break;
                }
            }
            gap >>= 1;
        }
    }

    public static void main(String[] args) {
        int[] array = Utils.generateArrayOfIntegers(20, 100);

        Sortable sb = new ShellSort();
        sb.sort(array);

        System.out.println(Arrays.toString(array));
    }
}
