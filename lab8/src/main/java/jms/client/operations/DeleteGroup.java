package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Operation;

import java.io.IOException;

public class DeleteGroup extends ClientOperation {
    public DeleteGroup(Channel channel) throws IOException {
        super(Operation.DELETE_GROUP, channel);
    }

    public void performQuery(int groupId) throws IOException {
        writeCode(out);

        out.writeInt(groupId);
        send();
    }
}