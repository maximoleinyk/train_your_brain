package com.home.sorting;

import java.util.Arrays;

import static com.home.Utils.generateArrayOfIntegers;

public class InsertionSort implements Sortable {
    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        for (int i = 1; i < array.length; i++) {
            for (int j = i; j >= 1; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = generateArrayOfIntegers(10, 10);
        Sortable bs = new InsertionSort();
        bs.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
