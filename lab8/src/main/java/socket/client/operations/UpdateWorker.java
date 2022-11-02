package socket.client.operations;

import socket.common.Operation;
import socket.common.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UpdateWorker extends ClientOperation {
    public UpdateWorker() {
        super(Operation.UPDATE_WORKER);
    }

    public void performQuery(ObjectInputStream in, ObjectOutputStream out, Worker worker) throws IOException {
        writeCode(out);

        out.writeObject(worker);
        out.flush();


        int success = in.readInt();
        if (success != 0)
            throw new RuntimeException("Something went wrong on the server");
    }
}