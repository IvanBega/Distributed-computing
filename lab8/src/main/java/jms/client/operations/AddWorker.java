package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Operation;
import jms.common.Worker;

import java.io.IOException;

public class AddWorker extends ClientOperation {
    public AddWorker(Channel channel) throws IOException {
        super(Operation.ADD_WORKER, channel);
    }

    public void performQuery(Worker worker) throws IOException {
        writeCode(out);

        out.writeObject(worker);
        send();
    }
}