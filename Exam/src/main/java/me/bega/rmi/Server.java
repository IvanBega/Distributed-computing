package me.bega.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        ServerRmiTask3 server = new ServerRmiTask3();
        Registry registry = LocateRegistry.createRegistry(1612);
        registry.rebind("PatientOperations", server);
        System.out.println("Successfully started server!");
    }
}
