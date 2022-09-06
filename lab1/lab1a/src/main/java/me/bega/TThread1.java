package me.bega;

public class TThread1 extends Thread{
    @Override
    public void run(){
        while (true) {
            synchronized (Main.slider) {
                if (Main.slider.getValue() > 10) {
                    Main.slider.setValue(Main.slider.getValue()-1);
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e){
                }
            }
        }
    }
}
