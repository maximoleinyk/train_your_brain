package com.home;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort implements Sortable {

    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        this.sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(lo, hi);
        int pivot = array[randomIndex];

        // move pivot to the end of array
        swap(array, randomIndex, hi);

        int i = lo;
        int j = hi;

        while(i < j) {
            if (array[i] > pivot) {
                while (j > i) {
                    if (array[j] < pivot) {
                        swap(array, i, j);
                        break;
                    }
                    j--;
                }
            }
            i++;
        }
        // swap pivot back
        swap(array, j, hi);
        // do sorting for the left sub array
        sort(array, lo, i - 1);
        // perform sorting for the right sub array
        sort(array, i, hi);
    }

    private void swap(int[] array, int i1, int i2) {
        int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    public static void main(String[] args) {
        int[] array = Utils.generateArrayOfIntegers(4, 10);
        Sortable qs = new QuickSort();

        qs.sort(array);

        System.out.println(Arrays.toString(array));
    }
}
