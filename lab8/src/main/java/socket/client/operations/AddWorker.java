package socket.client.operations;

import socket.common.Operation;
import socket.common.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddWorker extends ClientOperation {
    public AddWorker() {
        super(Operation.ADD_WORKER);
    }

    public int performQuery(ObjectInputStream in, ObjectOutputStream out, Worker worker) throws IOException {
        writeCode(out);

        out.writeObject(worker);
        out.flush();


        int id = in.readInt();
        worker.setId(id);
        return id;
    }
}