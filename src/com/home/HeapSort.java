package com.home;

import java.util.Arrays;

import static com.home.Utils.generateArrayOfIntegers;

public class HeapSort implements Sortable {
    private void swap(int[] array, int l, int r) {
        int temp = array[l];
        array[l] = array[r];
        array[r] = temp;
    }

    @Override
    public void sort(int[] array) {
        this.heapify(array, array.length);

        for (int i = array.length - 1; i >= 0; i--) {
            swap(array, 0, i);
            this.heapify(array, i);
        }
    }

    private void heapify(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            int left = i == 0 ? 1 : i*2;
            int right = i*2 + 1;

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
        int[] array = generateArrayOfIntegers(100, 100);
        Sortable bs = new HeapSort();
        bs.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
