package me.bega;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        BarberQueue queue = new BarberQueue();
        Barber barber = new Barber(queue);
        barber.start();

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer(i, queue);
            customer.start();
            Thread.sleep(800);
        }
    }


}
