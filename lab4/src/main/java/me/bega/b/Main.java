package me.bega.b;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main");
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        byte[][] array = new byte[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = 1;
            }
        }
        Thread gardener = new Gardener(lock, array);
        Thread gcw = new GardenConsoleWriter(lock, array);
        Thread nature = new Nature(lock, array);
        Thread gfw = new GardenFileWriter(lock, array);
        gardener.start();
        gcw.start();
        nature.start();
        gfw.start();

    }
}
