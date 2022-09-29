package me.bega.b;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Gardener extends Thread{
    ReentrantReadWriteLock lock;
    byte[][] array;

    public Gardener(ReentrantReadWriteLock lock, byte[][] array) {
        this.lock = lock;
        this.array = array;
    }
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            lock.writeLock().lock();
            int row = random.nextInt(10);
            for (int i = row; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    array[i][j] = 1;
                }
            }
            lock.writeLock().unlock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void printArray() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

}
