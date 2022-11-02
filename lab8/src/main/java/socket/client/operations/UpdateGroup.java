package socket.client.operations;


import socket.common.Group;
import socket.common.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UpdateGroup extends ClientOperation {
    public UpdateGroup() {
        super(Operation.UPDATE_GROUP);
    }

    public void performQuery(ObjectInputStream in, ObjectOutputStream out, Group group) throws IOException {
        writeCode(out);

        out.writeObject(group);
        out.flush();


        int success = in.readInt();
        if (success != 0)
            throw new RuntimeException("Something wrong happened on the server: " + success);
    }
}