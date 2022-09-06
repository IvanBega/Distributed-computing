package me.bega;

public class Ivanov implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < ProducerConsumerPattern.objects.length; i++){
            try{
                System.out.println("Ivanov stole " + ProducerConsumerPattern.objects[i]);
                ProducerConsumerPattern.queue1.put(ProducerConsumerPattern.objects[i]);
            } catch (InterruptedException e){

            }
        }
    }
}
