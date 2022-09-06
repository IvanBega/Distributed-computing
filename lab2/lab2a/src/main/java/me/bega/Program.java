package me.bega;

import java.util.Random;

public class Program {
    private static int currentRow = 0;
    private static int workers = 4;
    private static int rowCount = 100;
    private static int rowLength = 10;
    public static boolean[][] forest = new boolean[rowCount][rowLength];
    private static boolean bearFound = false;
    private static int bearX;
    private static int bearY;

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < rowLength; j++)
                forest[i][j] = false;

        forest[r.nextInt(rowCount-1)][r.nextInt(rowLength-1)] = true;

        Thread[] threads = new Thread[workers];
        for (int i = 0; i < workers; i++) {
            threads[i] = new Worker();
            threads[i].start();
        }
    }
    public static synchronized int allocateRow(){
        if (currentRow + 1 >= rowCount)
            return -1;

        currentRow += 1;
        return currentRow - 1;
    }
    public static synchronized boolean IsBearFound(){
        return bearFound;
    }

    public static int getBearY() {
        return bearY;
    }

    public static int getBearX() {
        return bearX;
    }
    public static int getRowLength(){
        return rowLength;
    }

    public static void setBearFound(int currentRow, int i) {
        bearFound = true;
        System.out.println("Bear was found at " + currentRow + " " + i);
    }
}
