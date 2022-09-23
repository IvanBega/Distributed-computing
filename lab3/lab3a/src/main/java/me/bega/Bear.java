package me.bega;

public class Bear {
    private boolean locked = false;
    private Pot pot;
    public Bear(Pot _pot) {
        pot = _pot;
    }
    public void WakeUp(int beeId) {
        if (!locked) return;

        if (pot.getCapacity() == 0) {
            System.out.println("Error! Bear tried to eat from empty pot! " + beeId);
            return;
        }
        pot.setCapacity(0);
        pot.setFull(false);
        System.out.println("Bear was woken up by bee #" + beeId + " and ate all honey!");
    }
    public void Lock() {
        locked = true;
    }
    public void Unlock() {
        locked = false;
    }
    public boolean IsAvailable() {
        return !locked;
    }
}
