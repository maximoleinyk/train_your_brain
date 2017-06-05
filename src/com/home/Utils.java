package com.home;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public int getKthSmallest(int[] array, int k) {
        if (array == null || k >= array.length) {
            return -1;
        }


        return -1;
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
