package me.bega.a;

import java.io.*;

public class DeletePerson extends Thread{
    private ReadWriteLock lock;
    private String key;
    DeletePerson(ReadWriteLock lock, String key) {
        this.lock = lock;
        this.key = key;
    }
    public void run() {
        try {
            lock.lockWrite();
            DeletePerson();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void DeletePerson() throws InterruptedException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));
            String line = "f";
            while ((line = reader.readLine()) != null) {
                String[] person = line.split(" ");
                if (!person[0].equals(key)) {
                    writer.write(line);
                    writer.write("\n");
                }
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlockWrite();
        }
    }
}
