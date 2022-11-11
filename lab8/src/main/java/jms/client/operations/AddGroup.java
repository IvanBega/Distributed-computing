package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Group;
import jms.common.Operation;

import java.io.IOException;
public class AddGroup extends ClientOperation {
    public AddGroup(Channel channel) throws IOException {
        super(Operation.ADD_GROUP, channel);
    }

    public void performQuery(Group group) throws IOException {
        writeCode(out);

        out.writeObject(group);

        send();
    }
}