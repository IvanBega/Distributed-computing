package jms.common;

import java.util.HashMap;

public enum Operation {
    GET_ALL_GROUPS(0),
    ADD_GROUP(1),
    UPDATE_GROUP(2),
    DELETE_GROUP(3),
    GET_WORKERS_FROM_GROUP(4),
    ADD_WORKER(5),
    UPDATE_WORKER(6),
    DELETE_WORKER(7);

    //Responses
    public static final int UPDATE_GROUPS_LIST = 0;
    public static final int UPDATE_WORKERS_LIST = 1;
    public static final int RECEIVE_GROUPS_LIST = 2;
    public static final int RECEIVE_WORKERS_LIST = 3;

    public final int id;
    private static final HashMap<Integer, Operation> operations = new HashMap<>();

    Operation(int id) {
        this.id = id;
    }

    public static Operation get(int id) {
        return operations.get(id);
    }

    static {
        for (Operation type : Operation.values())
            operations.put(type.id, type);
    }
}