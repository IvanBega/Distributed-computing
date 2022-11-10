package rmi.common;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWorkerDao extends Remote {
    void deleteWorker(int id) throws RemoteException;
    void updateWorker(Worker worker) throws RemoteException;
    ArrayList<Worker> getAllWorkersFromGroup(int groupId) throws RemoteException;
    void addWorker(Worker worker) throws RemoteException;
}
