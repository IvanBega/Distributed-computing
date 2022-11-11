package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Group;
import jms.common.Operation;

import java.io.IOException;

public class UpdateGroup extends ClientOperation {
    public UpdateGroup(Channel channel) throws IOException {
        super(Operation.UPDATE_GROUP, channel);
    }

    public void performQuery(Group group) throws IOException {
        writeCode(out);

        out.writeObject(group);
        send();
    }
}