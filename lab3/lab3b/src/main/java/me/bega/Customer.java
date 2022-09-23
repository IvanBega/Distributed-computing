package me.bega;

public class Customer extends Thread {
    private BarberQueue barberQueue;
    private int id;
    public Customer(int _id, BarberQueue _barberQueue) {
        id = _id;
        barberQueue = _barberQueue;
    }
    @Override
    public void run() {
        goToBarber();
    }

    public synchronized void goToBarber() {
        barberQueue.addCustomer(this);
        try {
            wait();
        } catch (InterruptedException e) {

        }
        System.out.println("Customer " + id + " is getting haircut");
    }

    public long getId() {
        return id;
    }
}
