package rmi.server;

import rmi.common.IWorkerDao;
import rmi.common.Worker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.ArrayList;

public class WorkerDao extends UnicastRemoteObject implements IWorkerDao {
    private final Connection connection;
    protected WorkerDao(Connection connection) throws RemoteException {
        super();
        this.connection = connection;
    }

    @Override
    public void deleteWorker(int id) throws RemoteException {

    }

    @Override
    public void updateWorker(Worker worker) throws RemoteException {

    }

    @Override
    public ArrayList<Worker> getAllWorkersFromGroup(int groupId) throws RemoteException {
        return null;
    }

    @Override
    public void addWorker(Worker worker) throws RemoteException {

    }
}
