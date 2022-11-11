package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Operation;
import jms.common.Worker;

import java.io.IOException;
import java.util.List;

public class GetWorkersFromGroup extends ClientOperation {
    public GetWorkersFromGroup(Channel channel) throws IOException {
        super(Operation.GET_WORKERS_FROM_GROUP, channel);
    }

    public void performQuery(int groupId) throws IOException {
        writeCode(out);

        out.writeInt(groupId);
        send();
    }
}