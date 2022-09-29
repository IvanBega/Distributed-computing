package me.bega.b;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Nature extends Thread{
    private ReentrantReadWriteLock lock;
    private byte[][] array;
    public Nature(ReentrantReadWriteLock lock, byte[][] array) {
        this.array = array;
        this.lock = lock;
    }
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            lock.writeLock().lock();
            for (int k = 0; k < 20; k++) {
                int i = random.nextInt(10);
                int j = random.nextInt(10);
                array[i][j] = 0;
            }
            lock.writeLock().unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
