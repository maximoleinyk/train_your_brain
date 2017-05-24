package com.home;

import java.util.Arrays;

public class InterpolationSearch {

    public static int search(int[] array, int number) {
        int l = 0;
        int h = array.length - 1;
        int mid;

        while (array[h] != array[l] && number >= array[l] && number <= array[h]) {
            mid = l + (number - array[l]) * (h - l) / (array[h] - array[l]);

            if (array[mid] < number) {
                l = mid + 1;
            } else if (array[mid] > number) {
                h = mid - 1;
            } else {
                return mid;
            }
        }

        if (number == array[l]) {
            return l;
        }

        return -1;
    }

    public static void main(String[] args) {
        int number = 100;
        int[] array = {1, 2, 3, 4, 5, 6, 6, 7, 8, 15, 17, 18, 23, 45, 100};
        int index = InterpolationSearch.search(array, number);

        System.out.println(Arrays.toString(array));
        System.out.println("Found item: " + number + " at: " + index + " index.");
    }
}
