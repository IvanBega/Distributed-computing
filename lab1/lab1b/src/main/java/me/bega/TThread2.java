package me.bega;

public class TThread2 extends Thread{
    @Override
    public void run(){
        while (!this.isInterrupted()) {
            Main.slider.setValue(90);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
