package me.bega;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Pot pot = new Pot();
        Bear bear = new Bear(pot);
        List<Bee> bees = new ArrayList<Bee>();
        for (int i = 0; i < 7; i++) {
            Bee bee = new Bee(i, pot, bear);
            bees.add(bee);
        }

        for (Bee bee : bees) {
            Thread threadBee = new Thread(bee);
            threadBee.start();
        }
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
    }
}
