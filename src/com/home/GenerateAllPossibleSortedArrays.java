package com.home;

public class GenerateAllPossibleSortedArrays {
    public static void main(String[] args) {
        GenerateAllPossibleSortedArrays c = new GenerateAllPossibleSortedArrays();
        c.run();
    }

    private void run() {
        int[] a = {4, 10, 15, 20};
        int[] b = {3, 8, 12, 16, 21};
        generateArray(a, b, 0, 0);
    }

    private void print(int[] array, int h) {
        for (int i = 0; i < h; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("");
    }

    private void generateArray(int[] a, int[] b, int i, int i1) {
        int[] c = new int[a.length + b.length];
        generateArray(a, b, c, 0, 0, 0, true);
    }

    private void generateArray(int[] a, int[] b, int[] c, int i1, int i2, int pos, boolean flag) {
        if (flag) {
            if (pos != 0) {
                print(c, pos + 1);
            }
            for (int i = i1; i < a.length; i++) {
                if (pos == 0) {
                    c[pos] = a[i];
                    generateArray(a, b, c, i + 1, i2, pos, !flag);
                } else if (a[i] > c[pos]) {
                    c[pos + 1] = a[i];
                    generateArray(a, b, c, i + 1, i2, pos + 1, !flag);
                }
            }
        } else {
            for (int j = i2; j < b.length; j++) {
                if (b[j] > c[pos]) {
                    c[pos + 1] = b[j];
                    generateArray(a, b, c, i1, j + 1, pos + 1, !flag);
                }
            }
        }
    }
}
