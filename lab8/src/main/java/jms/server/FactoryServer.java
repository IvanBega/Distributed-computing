package jms.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import jms.common.Operation;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class FactoryServer {
    public static final String SERVER_TO_CLIENT = "to_client";
    public static final String CLIENT_TO_SERVER = "to_server";

    public static void main(String[] args) throws SQLException, IOException, TimeoutException, InterruptedException {
        java.sql.Connection sqlConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/FACTORY", "user", "bananas");

        GroupDao groupDao = new GroupDao(sqlConnection);
        WorkerDao workerDao = new WorkerDao(sqlConnection);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (com.rabbitmq.client.Connection mqConnection = factory.newConnection();
             Channel channel = mqConnection.createChannel()) {
            channel.queueDeclare(SERVER_TO_CLIENT, false, false, false, null);
            channel.queueDeclare(CLIENT_TO_SERVER, false, false, false, null);

            ServerOperations operations = new ServerOperations(groupDao, workerDao, channel);

            while (!Thread.interrupted()) {
                channel.basicConsume(CLIENT_TO_SERVER, true, (String callbackTag, Delivery delivery) -> {
                    System.err.println("Consumed");
                    ByteArrayInputStream bytesIn = new ByteArrayInputStream(delivery.getBody());
                    ObjectInputStream in = new ObjectInputStream(bytesIn);
                    int messageType = in.readInt();
                    ServerOperation op = operations.getOperation(Operation.get(messageType));

                    ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(bytesOut);

                    op.handleQuery(in, out);
                    out.flush();

                    channel.basicPublish("", SERVER_TO_CLIENT, null, bytesOut.toByteArray());
                }, callbackTag -> { });
                Thread.sleep(100);
            }
        }
    }
}