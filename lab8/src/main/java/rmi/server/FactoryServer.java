package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryServer {
    public static void main(String[] args) throws SQLException, RemoteException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FACTORY", "user", "bananas");


        Registry registry = LocateRegistry.createRegistry(12347);

        GroupDao groupDao = new GroupDao(connection);
        WorkerDao workerDao = new WorkerDao(connection);

        System.err.println("Binding...");
        registry.rebind("//127.0.0.1/GroupDAO", groupDao);
        System.err.println("Bound group DAO.");
        registry.rebind("//127.0.0.1/WorkerDAO", workerDao);
        System.err.println("Bound worker DAO.");
    }
}
