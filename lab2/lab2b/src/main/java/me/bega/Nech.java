package me.bega;

public class Nech implements Runnable{
    public void run() {
        while (true){
            try{
                String item = ProducerConsumerPattern.queue2.take();
                System.out.println("Nech sold " + item);
            } catch (InterruptedException e){

            }
        }
    }
}
