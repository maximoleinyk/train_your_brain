package com.home.sorting;

import java.util.Arrays;

import static com.home.Utils.generateArrayOfIntegers;
import static com.home.Utils.swap;

public class HeapSort implements Sortable {

    @Override
    public void sort(int[] array) {
        // build max heap
        this.heapify(array, array.length);

        for (int i = array.length - 1; i > 0; i--) {
            // move biggest element to the end of the array
            swap(array, 0, i);
            // build max heap
            this.heapify(array, i);
        }
    }

    private void heapify(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            int left = i == 0 ? 1 : i * 2;
            int right = i * 2 + 1;

            if (array[i] < array[left]) {
                swap(array, i, left);
            }

            if (array[i] < array[right]) {
                swap(array, i, right);
            }

            if (array[left] > array[right]) {
                swap(array, left, right);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = generateArrayOfIntegers(20, 100);
        Sortable bs = new HeapSort();
        bs.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
