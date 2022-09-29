package me.bega.b;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GardenFileWriter extends Thread{
    private ReentrantReadWriteLock lock;
    private byte[][] array;
    public GardenFileWriter(ReentrantReadWriteLock lock, byte[][] array) {
        this.array = array;
        this.lock = lock;
    }
    @Override
    public void run() {
        while (true) {
            lock.readLock().lock();
            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("garden.txt", true));
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        writer.write(array[i][j] + " ");
                    }
                    writer.write("\n");
                }
                writer.write("\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            lock.readLock().unlock();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
