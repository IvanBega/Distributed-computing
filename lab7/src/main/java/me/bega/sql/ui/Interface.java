package me.bega.sql.ui;

import me.bega.sql.controller.GroupDao;
import me.bega.sql.controller.WorkerDao;
import me.bega.sql.model.Group;
import me.bega.sql.model.Worker;
import org.xml.sax.SAXException;
import java.sql.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Interface {
    private Connection connection;
    private GroupDao groupDao;
    private WorkerDao workerDao;
    private Scanner scanner = new Scanner(System.in);
    public void start() throws SQLException {
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FACTORY", "user", "bananas");
            groupDao = new GroupDao(connection);
            workerDao = new WorkerDao(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e); }

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
                    getAllWorkersFromGroupFromGroup();
                    break;
                case 8:
                    printAllGroups();
                    break;
                case 9:
                    connection.close();
                    flag = false;
                    break;
            }
        }
    }
    private void printOptions() {
        System.out.println("1 - add worker\n2 - add group\n3 - delete worker\n4 - delete group\n" +
                "5 - update group\n6 - update worker\n7 - get a\n8 - get all groups" +
                "\n9 - exit");
    }
    private void addWorker() {
        System.out.println("Enter worker id and group id");
        int id = scanner.nextInt();
        int groupID = scanner.nextInt();
        Worker worker = new Worker(id, groupID, "Ivan", LocalDate.now(), true);

        workerDao.addWorker(worker);
        System.out.println("Added worker id " + id + ", groupID " + groupID);
    }
    private void deleteWorker() {
        System.out.println("Enter worker is to delete: ");
        int id = scanner.nextInt();
        workerDao.deleteWorker(id);
    }
    private void updateWorker() {
        System.out.println("Enter new worker id, group and name");
        int id = scanner.nextInt();
        int group = scanner.nextInt();
        String name = scanner.nextLine();
        Worker worker = new Worker(id, group, name,LocalDate.now(), true);
        workerDao.updateWorker(worker);

        System.out.println("Updated worker!");
    }
    private void getAllWorkersFromGroupFromGroup() {
        System.out.println("Enter group id: ");
        int id = scanner.nextInt();
        List<Worker> workers = workerDao.getAllWorkersFromGroup(id);
        for (Worker w : workers) {
            System.out.println(w);
        }
    }
    private void printAllGroups() {
        List<Group> groups = groupDao.getAllGroups();
        for (Group g : groups) {
            System.out.println(g);
        }
    }
    private void addGroup() {
        System.out.println("Enter group id and name");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);

        groupDao.addGroup(group);
        System.out.println("Added group id " + id + ", name " + name);
    }
    private void updateGroup() {
        System.out.println("Enter group id and name:");
        int id = scanner.nextInt();
        String name = scanner.nextLine();
        Group group = new Group(id, name);
        groupDao.updateGroup(group);
    }
    private void deleteGroup() {
        System.out.println("Enter group id to delete:");
        int id = scanner.nextInt();
        groupDao.deleteGroup(id);
    }
}
