package me.bega;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerPattern {
    public static BlockingQueue<String> queue1 = new LinkedBlockingQueue<String>();
    public static BlockingQueue<String> queue2 = new LinkedBlockingQueue<String>();
    public static String[] objects = {"AK-47", "Bayraktar", "Mine", "Grenade", "Bullets", "Pistol"};

    public static void main(String[] args) {
        Thread ivanovTh = new Thread(new Ivanov());
        Thread petrovTh = new Thread(new Petrov());
        Thread nechTh = new Thread(new Nech());

        ivanovTh.start();
        petrovTh.start();
        nechTh.start();
    }
}
