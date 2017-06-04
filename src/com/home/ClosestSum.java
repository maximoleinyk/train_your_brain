package com.home;

public class ClosestSum {
    public static void main(String[] args) {
        ClosestSum cs = new ClosestSum();
        cs.run();
    }

    private void run() {
        int[] numbers = {10, 22, 28, 29, 30, 40};
        int x = 54;

        findClosestPair(numbers, x);
    }

    private void findClosestPair(int[] numbers, int x) {
        if (numbers == null || numbers.length < 2) {
            return;
        }

        int i = 0;
        int j = numbers.length - 1;
        int resultI = 0;
        int resultJ = 0;
        int lastDiff = Integer.MAX_VALUE;

        while (i < j) {
            if (numbers[j] > x) {
                j--;
                continue;
            }

            int diff = x - (numbers[i] + numbers[j]);

            if (diff == 0) {
                System.out.println(numbers[i] + " and " + numbers[j]);
                return;
            } else {
                if (Math.abs(diff) < Math.abs(lastDiff)) {
                    resultI = i;
                    resultJ = j;
                    lastDiff = diff;
                }

                if (diff > 0) {
                    i++;
                } else {
                    j--;
                }
            }
        }

        System.out.println(numbers[resultI] + " and " + numbers[resultJ]);
    }
}
