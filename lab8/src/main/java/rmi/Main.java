package rmi;

import rmi.common.IGroupDao;
import rmi.common.IWorkerDao;
import rmi.client.Interface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws NotBoundException, IOException {
        Registry registry = LocateRegistry.getRegistry(12347);
        IGroupDao groupDao =
                (IGroupDao) registry.lookup("//127.0.0.1/GroupDAO");
        IWorkerDao workerDao =
                (IWorkerDao) registry.lookup("//127.0.0.1/WorkerDAO");
        Interface i = new Interface(workerDao, groupDao);
        i.start();
    }
}
