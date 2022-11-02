package socket.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Interface {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public Interface(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }
}
