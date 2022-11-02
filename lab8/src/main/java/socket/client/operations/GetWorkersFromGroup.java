package socket.client.operations;

import socket.common.Operation;
import socket.common.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GetWorkersFromGroup extends ClientOperation {
    public GetWorkersFromGroup() {
        super(Operation.GET_WORKER_FROM_GROUP);
    }

    public ArrayList<Worker> performQuery(ObjectInputStream in, ObjectOutputStream out, int groupId) throws IOException {
        writeCode(out);

        out.writeInt(groupId);
        out.flush();


        int count = in.readInt();
        ArrayList<Worker> workers = new ArrayList<>(count);
        try {
            for (int i = 0; i < count; i++)
                workers.add((Worker) in.readObject());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return workers;
    }
}