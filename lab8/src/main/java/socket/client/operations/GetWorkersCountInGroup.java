package socket.client.operations;

import socket.common.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GetWorkersCountInGroup extends ClientOperation{
    public GetWorkersCountInGroup() {
        super(Operation.GET_WORKETS_COUNT_IN_GROUP);
    }
    public int performQuery(ObjectInputStream in, ObjectOutputStream out, int groupId) throws IOException {
        writeCode(out);
        out.writeInt(groupId);
        out.flush();

        int count = in.readInt();
        return count;
    }
}
