package com.home.sorting;

import java.util.Arrays;

import static com.home.Utils.generateArrayOfIntegers;

public class BubbleSort implements Sortable {
    public void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j] > array[i]) {
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = generateArrayOfIntegers(10, 10);
        Sortable bs = new BubbleSort();
        bs.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
