package me.bega.a;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock lock = new ReadWriteLock();
        AppendPerson writer = new AppendPerson(lock, "Gaby", "1234777");
        DeletePerson deleter = new DeletePerson(lock, "Sally");
        FindPerson finder = new FindPerson(lock, "Newton",0);
        writer.start();
        finder.start();
        deleter.start();

    }
}
