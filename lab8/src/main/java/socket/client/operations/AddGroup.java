package socket.client.operations;

import socket.common.Group;
import socket.common.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddGroup extends ClientOperation{
    public AddGroup() {
        super(Operation.ADD_GROUP);
    }

    public int performQuery(ObjectInputStream in, ObjectOutputStream out, Group group) throws IOException {
        writeCode(out);

        out.writeObject(group);
        out.flush();


        int id = in.readInt();
        group.setId(id);
        return id;
    }
}
