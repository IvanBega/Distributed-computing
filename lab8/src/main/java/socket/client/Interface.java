package socket.client;

import socket.client.operations.*;
import socket.common.Group;
import socket.common.Worker;
import socket.server.GroupDao;
import socket.server.WorkerDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Interface {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public Interface(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    private Scanner scanner = new Scanner(System.in);
    public void start() throws IOException {
        int option = 0;
        boolean flag = true;
        while (flag) {

            printOptions();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addWorker();
                    break;
                case 2:
                    addGroup();
                    break;
                case 3:
                    deleteWorker();
                    break;
                case 4:
                    deleteGroup();
                    break;
                case 5:
                    updateGroup();
                    break;
                case 6:
                    updateWorker();
                    break;
                case 7:
                    getAllWorkersFromGroup();
                    break;
                case 8:
                    printAllGroups();
                    break;
                case 9:
                    getWorkersCountInGroup();
                    break;
                case 10:
                    flag = false;
                    break;
            }
        }
    }
    private void printOptions() {
        System.out.println("1 - add worker\n2 - add group\n3 - delete worker\n4 - delete group\n" +
                "5 - update group\n6 - update worker\n7 - get all workers from group\n8 - get all groups" +
                "\n9 - workers count from a group\n10 - exit");
    }
    private void addWorker() throws IOException {
        System.out.println("Enter worker id and group id");
        int id = scanner.nextInt();
        int groupID = scanner.nextInt();
        Worker worker = new Worker(id, groupID, "Ivan", LocalDate.now(), true);
        AddWorker op = new AddWorker();
        op.performQuery(in, out, worker);
        System.out.println("Added worker id " + id + ", groupID " + groupID);
    }
    private void deleteWorker() throws IOException {
        System.out.println("Enter worker is to delete: ");
        int id = scanner.nextInt();
        DeleteWorker op = new DeleteWorker();
        op.performQuery(in, out, id);
    }
    private void updateWorker() throws IOException {
        System.out.println("Enter new worker id, group and name");
        int id = scanner.nextInt();
        int group = scanner.nextInt();
        String name = scanner.nextLine();
        Worker worker = new Worker(id, group, name,LocalDate.now(), true);
        UpdateWorker op = new UpdateWorker();
        op.performQuery(in, out, worker);

        System.out.println("Updated worker!");
    }
    private void getAllWorkersFromGroup() throws IOException {
        System.out.println("Enter group id: ");
        int id = scanner.nextInt();
        GetWorkersFromGroup op = new GetWorkersFromGroup();
        List<Worker> workers = op.performQuery(in, out, id);
        for (Worker w : workers) {
            System.out.println(w);
        }
    }
    private void printAllGroups() throws IOException {
        GetAllGroups op = new GetAllGroups();
        List<Group> groups = op.performQuery(in, out);
        for (Group g : groups) {
            System.out.println(g);
        }
    }
    private void addGroup() throws IOException {
        System.out.println("Enter group id and name");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        AddGroup op = new AddGroup();
        op.performQuery(in, out, group);
        System.out.println("Added group id " + id + ", name " + name);
    }
    private void updateGroup() throws IOException {
        System.out.println("Enter group id and name:");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        UpdateGroup op = new UpdateGroup();
        op.performQuery(in, out, group);
    }
    private void deleteGroup() throws IOException {
        System.out.println("Enter group id to delete:");
        int id = scanner.nextInt();

        DeleteGroup op = new DeleteGroup();
        op.performQuery(in, out, id);
    }
    private void getWorkersCountInGroup() throws  IOException {
        System.out.println("Enter group id");
        int id = scanner.nextInt();
        GetWorkersCountInGroup op = new GetWorkersCountInGroup();
        int count = op.performQuery(in, out, id);
        System.out.println("There are " + count + " workers in group +" +id);
    }
}
