package jms.server;

import com.rabbitmq.client.Channel;
import jms.common.Group;
import jms.common.Operation;
import jms.common.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class ServerOperations {
    private final GroupDao groupDao;
    private final WorkerDao workerDao;
    private final Channel channel;

    public ServerOperations(GroupDao groupDao, WorkerDao workerDao, Channel channel) {
        this.groupDao = groupDao;
        this.workerDao = workerDao;
        this.channel = channel;
    }

    public ServerOperation getOperation(Operation op) {
        return switch (op) {
            case ADD_GROUP -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Group group = (Group) in.readObject();

                        groupDao.addGroup(group);

                        out.writeInt(Operation.UPDATE_GROUPS_LIST);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case DELETE_GROUP -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    int groupId = in.readInt();

                    groupDao.deleteGroup(groupId);

                    out.writeInt(Operation.UPDATE_GROUPS_LIST);
                }
            };
            case UPDATE_GROUP -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Group group = (Group) in.readObject();

                        groupDao.updateGroup(group);

                        out.writeInt(Operation.UPDATE_GROUPS_LIST);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case GET_ALL_GROUPS -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    ArrayList<Group> groups = groupDao.getAllGroups();

                    out.writeInt(Operation.RECEIVE_GROUPS_LIST);
                    out.writeInt(groups.size());
                    for (Group group : groups)
                        out.writeObject(group);
                    out.flush();
                }
            };
            case GET_WORKERS_FROM_GROUP -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    int groupId = in.readInt();


                    ArrayList<Worker> workers = ServerOperations.this.workerDao.getAllWorkersFromGroup(groupId);

                    out.writeInt(Operation.RECEIVE_WORKERS_LIST);
                    out.writeInt(workers.size());
                    for (Worker worker : workers)
                        out.writeObject(worker);
                }
            };
            case ADD_WORKER -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Worker student = (Worker) in.readObject();


                        ServerOperations.this.workerDao.addWorker(student);

                        out.writeInt(Operation.UPDATE_WORKERS_LIST);
                        out.writeInt(student.getId());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case UPDATE_WORKER -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Worker worker = (Worker) in.readObject();


                        ServerOperations.this.workerDao.updateWorker(worker);

                        out.writeInt(Operation.UPDATE_WORKERS_LIST);
                        out.flush();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case DELETE_WORKER -> new ServerOperation(groupDao, workerDao, channel) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    int studentId = in.readInt();


                    ServerOperations.this.workerDao.deleteWorker(studentId);

                    out.writeInt(Operation.UPDATE_WORKERS_LIST);
                    out.flush();
                }
            };
        };
    }
}