package me.bega.xml.ui;

import me.bega.xml.controller.FactoryDao;
import me.bega.xml.model.Group;
import me.bega.xml.model.Worker;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Interface {
    private FactoryDao factoryDao = new FactoryDao();
    private Scanner scanner = new Scanner(System.in);
    public void start() throws IOException, SAXException {
        factoryDao.readFromFile("workers.xml");
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
                    printAllWorkers();
                    break;
                case 8:
                    printAllGroups();
                    break;
                case 9:
                    factoryDao.saveToFile("workers.xml");
                    flag = false;
                    break;
            }
        }
    }
    private void printOptions() {
        System.out.println("1 - add worker\n2 - add group\n3 - delete worker\n4 - delete group\n" +
                "5 - update group\n6 - update worker\n7 - get all workers\n8 - get all groups" +
                "\n9 - exit");
    }
    private void addWorker() {
        System.out.println("Enter worker id and group id");
        int id = scanner.nextInt();
        int groupID = scanner.nextInt();
        Worker worker = new Worker(id, groupID, "Ivan", LocalDate.now(), true);

        factoryDao.addWorker(worker);
        System.out.println("Added worker id " + id + ", groupID " + groupID);
    }
    private void addGroup() {
        System.out.println("Enter group id and name");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        factoryDao.addGroup(group);
        System.out.println("Added group id " + id + ", name " + name);
    }

    private void printAllWorkers() {
        List<Worker> workers = factoryDao.getAllWorkers();
        for (Worker w : workers) {
            System.out.println(w);
        }
    }
    private void updateWorker() {
        System.out.println("Enter new worker id and group");
        int id = scanner.nextInt();
        int group = scanner.nextInt();
        Worker worker = new Worker(id, group, "Ivan",LocalDate.now(), true);
        factoryDao.updateWorker(worker);

        System.out.println("Updated worker!");
    }
    private void deleteWorker() {
        System.out.println("Enter workers id: ");
        int id = scanner.nextInt();
        factoryDao.deleteWorker(id);
        System.out.println("Deleted worker!");
    }
    private void getAllStudentsFromGroup() {
        System.out.println("Enter group id: ");
        int id = scanner.nextInt();
        List<Worker> workers = factoryDao.getAllWorkersFromGroup(id);
        for (Worker w : workers) {
            System.out.println(w);
        }
    }
    private void updateGroup() {
        System.out.println("Enter group id and name:");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);
        factoryDao.updateGroup(group);
    }
    private void deleteGroup() {
        System.out.println("Enter group id to delete:");
        int id = scanner.nextInt();
        factoryDao.deleteGroup(id);
    }
    private void printAllGroups() {
        List<Group> groups = factoryDao.getAllGroups();
        for (Group g : groups) {
            System.out.println(g);
        }
    }

}
