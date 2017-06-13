package com.home;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static void main(String[] args) {
        System.out.println(rotateBitsRight(32, 8, 4));
    }

    public static int rotateBitsLeft(final int bits, int n, int times) {
        return (n << times) | (n >> (bits - times));
    }

    public static int rotateBitsRight(final int bits, int n, int times) {
        return (n >> times) | (n << (bits - times));
    }

    public static int sumBitDifferences(int[] array) {
        int result = 0;

        for (int i = 0; i < 32; i++) {
            int count = 0;
            for (int j = 0; j < array.length; j++) {
                if ((array[j] & (1 << i)) > 0) {
                    count++;
                }
            }
            result += (count * (array.length - count) * 2);
        }

        return result;
    }

    public static int getKthSmallest(int[] array, int k) {
        if (array == null || k >= array.length) {
            return Integer.MAX_VALUE;
        }

        int result = quickSelect(array, 0, array.length - 1, k);

        if (result == Integer.MAX_VALUE) {
            return array[k - 1];
        }

        return result;
    }

    private static int quickSelect(int[] array, int l, int h, int k) {
        if (l >= h) {
            return Integer.MAX_VALUE;
        }

        int lo = l;
        int hi = h;
        int pivotIndex = l;
        int pivot = array[pivotIndex];

        swap(array, pivotIndex, hi);
        while (lo < hi) {
            if (array[lo] > pivot) {
                while (hi > lo) {
                    if (array[hi] < pivot) {
                        swap(array, lo, hi);
                        break;
                    }
                    hi--;
                }
            }
            lo++;
        }
        swap(array, hi, h);

        if (k == hi + 1) {
            return array[hi];
        }

        int leftValue = quickSelect(array, l, lo - 1, k);
        int rightValue = quickSelect(array, lo, h, k);

        return leftValue == Integer.MAX_VALUE ? rightValue : leftValue;
    }

    public int[] getPrimeNumbers(int total) {
        int[] result = new int[total];
        int i = 0;
        int number = 2;

        while (i < total) {
            if (isPrime(number)) {
                result[i++] = number;
            }
            number++;
        }

        return result;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }

        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int[] generateArrayOfIntegers(int size, int bound) {
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = ThreadLocalRandom.current().nextInt(0, bound);
        }

        return result;
    }

    public static void swap(int[] array, int i1, int i2) {
        int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }
}
