package com.home;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by moliinyk on 24/05/2017.
 */
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
        int number = 1;

        while (results.size() <= 100) {
            if (isPrime(number)) {
                results.push(number);
            }
            number++;
        }

        System.out.println(Arrays.toString(results.toArray()));
    }
}
