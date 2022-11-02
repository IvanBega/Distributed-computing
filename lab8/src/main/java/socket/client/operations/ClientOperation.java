package socket.client.operations;

import socket.common.Operation;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class ClientOperation {
    private final Operation op;

    public ClientOperation(Operation op) {
        this.op = op;
    }

    protected void writeCode(ObjectOutputStream out) throws IOException {
        out.writeInt(op.id);
    }
}
