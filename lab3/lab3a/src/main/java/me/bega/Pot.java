package me.bega;

public class Pot {
    private int capacity = 20;
    private int currentCapacity = 0;
    private boolean full = false;

    public int getCapacity() {
        return capacity;
    }

    public synchronized void setCapacity(int cap) {
        currentCapacity = cap;
    }
    public synchronized void addHoneyPiece(int beeId) {
        if (currentCapacity < capacity) {
            currentCapacity++;
            System.out.println("Bee #" + beeId + " added honey. Total: " + currentCapacity);
        } else
            full = true;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}
