package me.bega;

public class Bee implements Runnable{
    private Pot pot;
    private Bear bear;
    private int id = 0;
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (bear.IsAvailable()) pot.addHoneyPiece(id);
            if (pot.isFull() && bear.IsAvailable()) {
                bear.Lock();
                bear.WakeUp(id);
                bear.Unlock();
            }
        }
    }
    public Bee(int _id, Pot _pot, Bear _bear) {
        id = _id;
        pot = _pot;
        bear = _bear;
    }
}
