package jms.client;

import com.rabbitmq.client.Channel;
import jms.client.operations.*;
import jms.common.Group;
import jms.common.Worker;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interface {
    private final Channel channel;
    public Interface(Channel channel) {
        this.channel = channel;
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
                    flag = false;
                    break;
            }
        }
    }
    private void printOptions() {
        System.out.println("1 - add worker\n2 - add group\n3 - delete worker\n4 - delete group\n" +
                "5 - update group\n6 - update worker\n7 - get all workers from group\n8 - get all groups" +
                "\n9 - exit");
    }
    private void addWorker() throws IOException {
        System.out.println("Enter worker id and group id");
        int id = scanner.nextInt();
        int groupID = scanner.nextInt();
        Worker worker = new Worker(id, groupID, "Ivan", LocalDate.now(), true);
        AddWorker op = new AddWorker(channel);
        op.performQuery(worker);
        System.out.println("Added worker id " + id + ", groupID " + groupID);
    }
    private void deleteWorker() throws IOException {
        System.out.println("Enter worker is to delete: ");
        int id = scanner.nextInt();
        DeleteWorker op = new DeleteWorker(channel);
        op.performQuery(id);
    }
    private void updateWorker() throws IOException {
        System.out.println("Enter new worker id, group and name");
        int id = scanner.nextInt();
        int group = scanner.nextInt();
        String name = scanner.nextLine();
        Worker worker = new Worker(id, group, name,LocalDate.now(), true);
        UpdateWorker op = new UpdateWorker(channel);
        op.performQuery(worker);

        System.out.println("Updated worker!");
    }
    private void getAllWorkersFromGroup() throws IOException {
        System.out.println("Enter group id: ");
        int id = scanner.nextInt();
        GetWorkersFromGroup op = new GetWorkersFromGroup(channel);
        op.performQuery(id);
    }
    private void printAllGroups() throws IOException {
        GetAllGroups op = new GetAllGroups(channel);op.performQuery();
        op.performQuery();
    }
    private void addGroup() throws IOException {
        System.out.println("Enter group id and name");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        AddGroup op = new AddGroup(channel);
        op.performQuery(group);
        System.out.println("Added group id " + id + ", name " + name);
    }
    private void updateGroup() throws IOException {
        System.out.println("Enter group id and name:");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        UpdateGroup op = new UpdateGroup(channel);
        op.performQuery(group);
    }
    private void deleteGroup() throws IOException {
        System.out.println("Enter group id to delete:");
        int id = scanner.nextInt();

        DeleteGroup op = new DeleteGroup(channel);
        op.performQuery(id);
    }

    public void receiveGroupsList(ArrayList<Group> groups) {
        for (Group g : groups) {
            System.out.println(g);
        }
    }

    public void receiveWorkersList(ArrayList<Worker> workers) {
        for (Worker w : workers) {
            System.out.println(w);
        }
    }
}
