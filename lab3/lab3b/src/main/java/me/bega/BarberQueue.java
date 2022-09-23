package me.bega;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BarberQueue {
    private BlockingQueue<Customer> queue = new LinkedBlockingQueue<>();

    public void addCustomer(Customer customer) {
        queue.add(customer);
    }

    public Customer getCustomer() throws InterruptedException {
        Customer customer = queue.take();
        return customer;
    }
}
