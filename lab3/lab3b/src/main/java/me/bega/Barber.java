package me.bega;

public class Barber extends Thread{
    private BarberQueue barberQueue;

    public Barber(BarberQueue barberqueue) {
        barberQueue = barberqueue;
    }

    private synchronized void nextCustomer () throws InterruptedException {
        Customer customer = barberQueue.getCustomer();

        synchronized (customer) {
            customer.notify();
        }
        Thread.sleep(1200);

        long id = customer.getId();
        System.out.println("Barber finished cutting hair of client " + id);
    }

    @Override
    public void run() {
        while (true) {
            try {
                nextCustomer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
