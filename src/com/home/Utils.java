package com.home;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int[] generateArrayOfIntegers(int size, int bound) {
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = ThreadLocalRandom.current().nextInt(0, bound);
        }

        return result;
    }
}
