package me.bega;

public class Petrov implements Runnable{
    @Override
    public void run() {
        while (true){
            try{
                String item = ProducerConsumerPattern.queue1.take();
                System.out.println("Petrov loaded " + item);
                ProducerConsumerPattern.queue2.put(item);
            } catch (InterruptedException e){

            }
        }
    }
}
