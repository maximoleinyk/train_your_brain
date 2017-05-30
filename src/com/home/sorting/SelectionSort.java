package com.home.sorting;

import com.home.Utils;

import java.util.Arrays;

public class SelectionSort implements Sortable {
    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        for (int i = array.length - 1; i >= 0; i--) {
            int maxIndex = this.getMax(array, i);

            Utils.swap(array, i, maxIndex);
        }
    }

    private int getMax(int[] array, int i) {
        int maxIndex = i;

        for (int j = 0; j < i; j++) {
            if (array[maxIndex] < array[j]) {
                maxIndex = j;
            }
        }

        return maxIndex;
    }

    public static void main(String[] args) {
        int[] array = Utils.generateArrayOfIntegers(20, 100);
        Sortable sr = new SelectionSort();
        sr.sort(array);
        System.out.println(Arrays.toString(array));
    }
}

