package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Operation;

import java.io.IOException;

public class DeleteWorker extends ClientOperation {
    public DeleteWorker(Channel channel) throws IOException {
        super(Operation.DELETE_WORKER, channel);
    }

    public void performQuery(int workerId) throws IOException {
        writeCode(out);

        out.writeInt(workerId);
        send();
    }
}