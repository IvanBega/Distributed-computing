package jms.client.operations;

import com.rabbitmq.client.Channel;
import jms.common.Operation;

import java.io.IOException;


public class GetAllGroups extends ClientOperation {
    public GetAllGroups(Channel channel) throws IOException {
        super(Operation.GET_ALL_GROUPS, channel);
    }

    public void performQuery() throws IOException {
        writeCode(out);
        send();
    }
}