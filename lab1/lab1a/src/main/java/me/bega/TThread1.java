package me.bega;

public class TThread1 extends Thread{
    @Override
    public void run(){
        while (true) {
            synchronized (Main.slider) {
                Main.slider.setValue(90);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e){
                }
            }
        }
    }
}
