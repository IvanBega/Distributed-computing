package me.bega;

public class TThread1 extends Thread{
    @Override
    public void run(){
        while (!this.isInterrupted()) {
            Main.slider.setValue(10);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
