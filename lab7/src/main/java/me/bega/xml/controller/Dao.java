package me.bega.xml.controller;

import me.bega.xml.model.Group;
import me.bega.xml.model.Worker;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

public interface Dao {
    Worker getWorker(int id);

    void addWorker(Worker worker);

    int getFreeWorkerId();

    void updateWorker(Worker worker);

    boolean deleteWorker(int id);

    ArrayList<Worker> getAllWorkers();

    ArrayList<Worker> getAllWorkersFromGroup(int groupId);

    Group getGroup(int id);

    void addGroup(Group group);

    void updateGroup(Group group);

    boolean deleteGroup(int id);

    ArrayList<Group> getAllGroups();

    void saveToFile(String fileName) throws IOException;

    void readFromFile(String fileName) throws IOException, SAXException;
}
