package com.home;

public class Boggle {
    private String word = "";

    private void findWord(char[][] boggle, boolean[][] visited, int i, int j) {
        visited[i][j] = true;

        word += boggle[i][j];

        if (isWord(word)) {
            System.out.println(word);
        }

        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k >= 0 && k < boggle.length && l >= 0 && l < boggle[k].length && !visited[k][l]) {
                    findWord(boggle, visited, k, l);
                }
            }
        }

        word = word.substring(0, word.length() - 1);
        visited[i][j] = false;
    }

    private static boolean isWord(String word) {
        String[] words = {"fox", "rim", "fra"};

        for (String w : words) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        char[][] boggle = {
                {'f', 'o', 'x'},
                {'r', 'i', 'm'},
                {'a', 'b', 'x'}
        };

        boolean[][] visited = new boolean[boggle.length][boggle[0].length];

        for (int i = 0; i < boggle.length; i++) {
            for (int j = 0; j < boggle[i].length; j++) {
                findWord(boggle, visited, i, j);
            }
        }
    }

    public static void main(String[] args) {
        Boggle b = new Boggle();
        b.run();
    }
}
