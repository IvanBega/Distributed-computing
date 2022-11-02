package socket.client.operations;

import socket.common.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteWorker extends ClientOperation {
    public DeleteWorker() {
        super(Operation.DELETE_WORKER);
    }

    public void performQuery(ObjectInputStream in, ObjectOutputStream out, int studentId) throws IOException {
        writeCode(out);

        out.writeInt(studentId);
        out.flush();


        int success = in.readInt();
        if (success != 0)
            throw new RuntimeException("Something went wrong on the server");
    }
}