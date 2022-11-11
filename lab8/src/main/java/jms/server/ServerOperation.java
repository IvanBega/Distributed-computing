package jms.server;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class ServerOperation {
    protected final GroupDao groupDao;
    protected final WorkerDao workerDao;
    protected final Channel channel;

    public ServerOperation(GroupDao groupDao, WorkerDao workerDao, Channel channel) {
        this.groupDao = groupDao;
        this.workerDao = workerDao;
        this.channel = channel;
    }

    public abstract void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException;
}