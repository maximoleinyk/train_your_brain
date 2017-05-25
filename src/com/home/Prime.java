package com.home;

import java.util.Arrays;
import java.util.Stack;

public class Prime {
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }

        for (int i = 2; i*i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Stack<Integer> results = new Stack<>();
        int number = 2;

        while (results.size() != 10) {
            if (isPrime(number)) {
                results.push(number);
            }
            number++;
        }

        System.out.println(Arrays.toString(results.toArray()));
    }
}
