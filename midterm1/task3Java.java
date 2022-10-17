import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Parking parking = new Parking();
        Random rnd = new Random();
        for (int i = 0; i < 30; i++) {
            Thread car = new Car(i, parking);
            car.start();
            Thread.sleep(rnd.nextInt(1000));
        }
    }
}

public class Parking {
    private final int PARKING_SIZE = 5;
    private boolean slots[] = new boolean[PARKING_SIZE];
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Semaphore semaphore = new Semaphore(1);
    private int freeSlots = PARKING_SIZE;
    public int occupyFreeSlot(int id) throws InterruptedException {
        if (semaphore.tryAcquire(3000, TimeUnit.MICROSECONDS)) {
            lock.writeLock().lock();
            for (int i = 0; i < PARKING_SIZE; i++) {
                if (slots[i]) {
                    slots[i] = false;
                    System.out.println("Car " + id + " parked at place " + i);
                    freeSlots--;
                    lock.writeLock().unlock();
                    if (freeSlots != 0)
                        semaphore.release();
                    return i;
                }
            }
            lock.writeLock().unlock();
            semaphore.release();
        }
        else {
            System.out.println("Car " + id + " didn't park!");
        }

        return -1;

    }
    public void leaveSlot(int id, int slotId) {
        lock.writeLock().lock();
        slots[slotId] = true;
        freeSlots++;
        if (freeSlots == 1) {
            semaphore.release();
        }
        lock.writeLock().unlock();
        System.out.println("Car " + id + " has left slot " + slotId);
    }
    public Parking() {
        for (int i = 0; i < PARKING_SIZE; i++) {
            slots[i] = true;
        }
    }

}

public class Car extends Thread{
    private int id;
    private Parking parking;
    public Car(int id, Parking parking) {
        this.id = id;
        this.parking = parking;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        try {
            int slotId = parking.occupyFreeSlot(id);
            if (slotId != -1) {
                Thread.sleep(rnd.nextInt(5000));
                parking.leaveSlot(id, slotId);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}