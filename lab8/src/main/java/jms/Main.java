package jms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import jms.client.Interface;
import jms.common.Group;
import jms.common.Operation;
import jms.common.Worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class Main {
    public static final String SERVER_TO_CLIENT = "to_client";
    public static final String CLIENT_TO_SERVER = "to_server";

    public static void main(String[] args) throws SQLException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection mqConnection = factory.newConnection();
             Channel channel = mqConnection.createChannel()) {
            channel.queueDeclare(SERVER_TO_CLIENT, false, false, false, null);
            channel.queueDeclare(CLIENT_TO_SERVER, false, false, false, null);

            Interface inter = new Interface(channel);
            Thread thread = new Thread(()-> {
                try {
                    inter.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            while (!Thread.interrupted()) {
                channel.basicConsume(SERVER_TO_CLIENT, true, (String callbackTag, Delivery delivery) -> {
                    ByteArrayInputStream bytesIn = new ByteArrayInputStream(delivery.getBody());
                    ObjectInputStream in = new ObjectInputStream(bytesIn);
                    int messageType = in.readInt();

                    switch (messageType) {
                        case Operation.RECEIVE_GROUPS_LIST -> {
                            int count = in.readInt();
                            ArrayList<Group> groups = new ArrayList<>(count);
                            for (int i = 0; i < count; i++) {
                                try {
                                    groups.add((Group) in.readObject());
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            inter.receiveGroupsList(groups);
                        }
                        case Operation.RECEIVE_WORKERS_LIST -> {
                            int count = in.readInt();
                            ArrayList<Worker> workers = new ArrayList<>(count);
                            for (int i = 0; i < count; i++) {
                                try {
                                    workers.add((Worker) in.readObject());
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            inter.receiveWorkersList(workers);
                        }
                    }
                }, callbackTag -> { });
                Thread.sleep(100);
            }
        }
    }
}
