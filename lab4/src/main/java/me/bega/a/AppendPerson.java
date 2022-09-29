package me.bega.a;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AppendPerson extends Thread{
    private ReadWriteLock lock;
    private String nameToAppend;
    private String numberToAppend;
    public AppendPerson(ReadWriteLock lock, String nameToAppend, String numberToAppend) {
        this.lock = lock;
        this.nameToAppend = nameToAppend;
        this.numberToAppend = numberToAppend;
    }

    @Override
    public void run() {
        try {
            lock.lockWrite();
            writeToFile();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void writeToFile() throws InterruptedException {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("input.txt", true));
            writer.append(nameToAppend);
            writer.append(' ');
            writer.append(numberToAppend);
            writer.append("\n");
            writer.close();

        }

        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlockWrite();
        }
    }
}
