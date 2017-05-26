package com.home;

import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedList;

public class RadixSort implements Sortable {

    @SuppressWarnings("unchecked")
    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int maxValue = this.getMax(array);

        for (int i = 1; i <= maxValue; i *= 10) {
            Queue<Integer>[] buckets = (Queue<Integer>[]) Array.newInstance(Queue.class, 10);

            for (int element : array) {
                int lsd = (element / i) % 10;
                Queue<Integer> queue = buckets[lsd];

                if (queue == null) {
                    queue = new LinkedList<>();
                    buckets[lsd] = queue;
                }

                queue.add(element);
            }

            int counter = 0;

            for (Queue<Integer> bucket : buckets) {
                if (bucket == null) {
                    continue;
                }

                for (int enqueuedValue : bucket) {
                    array[counter++] = enqueuedValue;
                }
            }
        }
    }

    private int getMax(int[] array) {
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[] array = Utils.generateArrayOfIntegers(10, 1000);

        Sortable radix = new RadixSort();
        radix.sort(array);

        System.out.println(Arrays.toString(array));
    }
}
