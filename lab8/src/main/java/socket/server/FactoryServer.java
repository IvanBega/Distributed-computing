package socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryServer {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FACTORY", "user", "bananas");

        GroupDao groupDao = new GroupDao(connection);
        WorkerDao workerDao = new WorkerDao(connection);

        ServerSocket serverSocket = new ServerSocket(12345);

        int threads = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                while (!Thread.interrupted()) {
                    System.out.println("Started listening");
                    try (Socket socket = serverSocket.accept()) {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ClientHandler handler = new ClientHandler(in, out, groupDao, workerDao);
                        handler.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}