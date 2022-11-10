package rmi.client;


import rmi.common.Group;
import rmi.common.IGroupDao;
import rmi.common.IWorkerDao;
import rmi.common.Worker;
import rmi.server.GroupDao;
import rmi.server.WorkerDao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Interface {
    private final IWorkerDao workerDao;
    private final IGroupDao groupDao;

    public Interface(IWorkerDao workerDao, IGroupDao groupDao) {
        this.workerDao = workerDao;
        this.groupDao = groupDao;
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
        workerDao.addWorker(worker);
        System.out.println("Added worker id " + id + ", groupID " + groupID);
    }
    private void deleteWorker() throws IOException {
        System.out.println("Enter worker is to delete: ");
        int id = scanner.nextInt();
        workerDao.deleteWorker(id);
    }
    private void updateWorker() throws IOException {
        System.out.println("Enter new worker id, group and name");
        int id = scanner.nextInt();
        int group = scanner.nextInt();
        String name = scanner.nextLine();
        Worker worker = new Worker(id, group, name,LocalDate.now(), true);
        workerDao.updateWorker(worker);

        System.out.println("Updated worker!");
    }
    private void getAllWorkersFromGroup() throws IOException {
        System.out.println("Enter group id: ");
        int id = scanner.nextInt();
        List<Worker> workers = workerDao.getAllWorkersFromGroup(id);
        for (Worker w : workers) {
            System.out.println(w);
        }
    }
    private void printAllGroups() throws IOException {
        List<Group> groups = groupDao.getAllGroups();
        for (Group g : groups) {
            System.out.println(g);
        }
    }
    private void addGroup() throws IOException {
        System.out.println("Enter group id and name");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        groupDao.addGroup(group);
        System.out.println("Added group id " + id + ", name " + name);
    }
    private void updateGroup() throws IOException {
        System.out.println("Enter group id and name:");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        groupDao.updateGroup(group);
    }
    private void deleteGroup() throws IOException {
        System.out.println("Enter group id to delete:");
        int id = scanner.nextInt();

        groupDao.deleteGroup(id);
    }
}
