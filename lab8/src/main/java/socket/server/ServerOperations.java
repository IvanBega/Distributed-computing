package socket.server;

import socket.common.Group;
import socket.common.Operation;
import socket.common.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class ServerOperations {
    private final GroupDao groupDao;
    private final WorkerDao workerDao;

    public ServerOperations(GroupDao groupDao, WorkerDao workerDao) {
        this.groupDao = groupDao;
        this.workerDao = workerDao;
    }

    public ServerOperation getOperation(Operation op) {
        return switch (op) {
            case ADD_GROUP -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Group group = (Group) in.readObject();


                        groupDao.addGroup(group);

                        out.writeInt(group.getId());
                        out.flush();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case DELETE_GROUP -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    int groupId = in.readInt();


                    groupDao.deleteGroup(groupId);

                    out.writeInt(0);
                    out.flush();
                }
            };
            case UPDATE_GROUP -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Group group = (Group) in.readObject();


                        groupDao.updateGroup(group);

                        out.writeInt(0);
                        out.flush();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case GET_ALL_GROUPS -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    ArrayList<Group> groups = groupDao.getAllGroups();

                    out.writeInt(groups.size());
                    for (Group group : groups)
                        out.writeObject(group);
                    out.flush();
                }
            };
            case GET_WORKER_FROM_GROUP -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    int groupId = in.readInt();


                    ArrayList<Worker> workers = workerDao.getAllWorkersFromGroup(groupId);

                    out.writeInt(workers.size());
                    for (Worker w : workers)
                        out.writeObject(w);
                    out.flush();
                }
            };
            case ADD_WORKER -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Worker worker = (Worker) in.readObject();


                        workerDao.addWorker(worker);

                        out.writeInt(worker.getId());
                        out.flush();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case UPDATE_WORKER -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    try {
                        Worker worker = (Worker) in.readObject();


                        workerDao.updateWorker(worker);

                        out.writeInt(0);
                        out.flush();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            case DELETE_WORKER -> new ServerOperation(groupDao, workerDao) {
                @Override
                public void handleQuery(ObjectInputStream in, ObjectOutputStream out) throws IOException {
                    int studentId = in.readInt();


                    workerDao.deleteWorker(studentId);

                    out.writeInt(0);
                    out.flush();
                }
            };
        };
    }
}