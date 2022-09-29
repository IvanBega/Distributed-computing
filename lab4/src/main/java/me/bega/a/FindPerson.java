package me.bega.a;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FindPerson extends Thread{
    private String key;
    private ReadWriteLock lock;
    private int type;
    FindPerson(ReadWriteLock lock, String key, int type) {
        this.key = key;
        this.lock = lock;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            lock.lockRead();
            findPerson();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void findPerson() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String line ="a";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] person = line.split(" ");
                if (person[type].equals(key)) {
                    System.out.println("[FindPerson]: Found " +person[0] + ", phone " + person[1]);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlockRead();
        }
    }

}
