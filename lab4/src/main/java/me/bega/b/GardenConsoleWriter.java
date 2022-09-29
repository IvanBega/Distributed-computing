package me.bega.b;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GardenConsoleWriter extends Thread {
    private ReentrantReadWriteLock lock;
    private byte[][] array;
    public GardenConsoleWriter(ReentrantReadWriteLock lock, byte[][] array) {
        this.array = array;
        this.lock = lock;
    }
    @Override
    public void run() {
        while (true) {
            lock.readLock().lock();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.print(array[i][j] + " ");
                }
                System.out.print("\n");
            }
            lock.readLock().unlock();
            System.out.println("\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

