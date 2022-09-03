package me.bega;

public class TThread2 extends Thread{
    @Override
    public void run(){
        while (true) {
            synchronized (Main.slider) {
                Main.slider.setValue(10);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e){
                }
            }
        }
    }
}
