package socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class ServerOperation {
    protected final GroupDao groupDao;
    protected final WorkerDao workerDao;

    public ServerOperation(GroupDao groupDao, WorkerDao workerDao) {
        this.groupDao = groupDao;
        this.workerDao = workerDao;
    }

    public abstract void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException;
}