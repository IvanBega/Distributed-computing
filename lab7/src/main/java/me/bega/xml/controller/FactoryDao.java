package me.bega.xml.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import me.bega.xml.model.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class FactoryDao implements Dao {
    private final HashMap<Integer, Worker> workers = new HashMap<>();
    private final HashMap<Integer, Group> groups = new HashMap<>();

    // element types
    private static final String FACTORY = "factory";
    private static final String GROUP = "group";
    private static final String WORKER = "worker";

    // attributes
    private static final String ID = "id";
    private static final String GROUP_ID = "group";
    private static final String NAME = "name";
    private static final String DATE_OF_BIRTH = "birthdate";
    private static final String SALARY = "salary";
    private static final String HAS_BONUS = "bonus";

    private final Transformer transformer;
    private final DocumentBuilder documentBuilder;

    public FactoryDao() {
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            schema = schemaFactory.newSchema(new File("factory.xsd"));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setSchema(schema);
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        documentBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException e) {
                System.err.println("Warning");
                e.printStackTrace();
            }

            @Override
            public void error(SAXParseException e) throws SAXException {
                throw e;
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
                throw e;
            }
        });

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Worker getWorker(int id) {
        return workers.get(id).clone();
    }

    @Override
    public synchronized void addWorker(Worker worker) {
        if (workers.containsKey(worker.getId()))
            throw new IllegalArgumentException("Already have a worker with ID " + worker.getId());
        if (!groups.containsKey(worker.getGroupId()))
            throw new IllegalArgumentException("No group with ID " + worker.getGroupId());
        workers.put(worker.getId(), worker);
    }

    @Override
    public synchronized int getFreeWorkerId() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!workers.containsKey(i))
                return i;
        }
        throw new IndexOutOfBoundsException("No free index!");
    }

    @Override
    public synchronized void updateWorker(Worker worker) {
        if (!workers.containsKey(worker.getId()))
            throw new IllegalArgumentException("No worker with ID " + worker.getId());
        if (!groups.containsKey(worker.getGroupId()))
            throw new IllegalArgumentException("No group with ID " + worker.getGroupId());
        workers.put(worker.getId(), worker);
    }

    @Override
    public synchronized boolean deleteWorker(int id) {
        return workers.remove(id) != null;
    }

    @Override
    public synchronized ArrayList<Worker> getAllWorkers() {
        return new ArrayList<>(workers.values());
    }

    @Override
    public synchronized ArrayList<Worker> getAllWorkersFromGroup(int groupId) {
        return workers.values().stream().filter(worker ->
                worker.getGroupId() == groupId).collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public synchronized Group getGroup(int id) {
        return groups.get(id).clone();
    }

    @Override
    public synchronized void addGroup(Group group) {
        if (groups.containsKey(group.getId()))
            throw new IllegalArgumentException("Already have a group wtih ID " + group.getId());
        groups.put(group.getId(), group);
    }

    public synchronized int getFreeGroupId() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!groups.containsKey(i))
                return i;
        }
        throw new IndexOutOfBoundsException("No free index!");
    }

    @Override
    public synchronized void updateGroup(Group group) {
        if (!groups.containsKey(group.getId()))
            throw new IllegalArgumentException("No group with ID " + group.getId());
        groups.put(group.getId(), group);
    }

    @Override
    public synchronized boolean deleteGroup(int id) {
        boolean deleted = groups.remove(id) != null;
        if (deleted)
            workers.values().removeIf(worker -> worker.getGroupId() == id);
        return deleted;
    }

    @Override
    public synchronized ArrayList<Group> getAllGroups() {
        return new ArrayList<>(groups.values());
    }

    @Override
    public synchronized void saveToFile(String fileName) throws IOException {
        Document document = documentBuilder.newDocument();

        Element root = document.createElement(FACTORY);
        groups.values().forEach(group ->
                root.appendChild(makeGroupElement(document, group)));
        document.appendChild(root);

        Source domSource = new DOMSource(document);
        Result fileResult = new StreamResult(new File(fileName));

        transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.toString());
        try {
            transformer.transform(domSource, fileResult);
        } catch (TransformerException e) {
            if (e.getCause() instanceof IOException)
                throw (IOException) e.getCause();
            else
                throw new RuntimeException(e);
        }
    }

    private Element makeStudentElement(Document doc, Worker worker) {
        Element element = doc.createElement(WORKER);
        element.setAttribute(ID, Integer.toString(worker.getId()));

        element.setAttribute(NAME, worker.getName());
        element.setAttribute(GROUP_ID, Integer.toString(worker.getGroupId()));
        element.setAttribute(DATE_OF_BIRTH, worker.getBirthDate().toString());
        element.setAttribute(SALARY, Float.toString(worker.getSalary()));
        element.setAttribute(HAS_BONUS, Boolean.toString(worker.hasScholarship()));

        return element;
    }

    private Element makeGroupElement(Document doc, Group group) {
        Element element = doc.createElement(GROUP);
        element.setAttribute(ID, Integer.toString(group.getId()));
        element.setAttribute(NAME, group.getName());

        workers.values().forEach(worker -> {
            if (worker.getGroupId() == group.getId())
                element.appendChild(makeStudentElement(doc, worker));
        });

        return element;
    }

    @Override
    public synchronized void readFromFile(String fileName) throws IOException, SAXException {
        groups.clear();
        workers.clear();

        Document document;
        try {
            document = documentBuilder.parse(fileName);
        } catch (SAXException e) {
            if (e.getException() instanceof IOException)
                throw (IOException) e.getException();
            else
                throw e;
        }

        Element root = document.getDocumentElement();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element)
                parseGroup((Element) node);
        }
    }
    private void parseGroup(Element element) {
        Group group = new Group(
                Integer.parseInt(element.getAttribute(ID)),
                element.getAttribute(NAME)
        );
        groups.put(group.getId(), group);

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node item = children.item(i);
            if (item instanceof Element)
                parseStudent((Element) item);
        }
    }

    private void parseStudent(Element element) {
        Worker worker = new Worker(
                Integer.parseInt(element.getAttribute(ID)),
                Integer.parseInt(element.getAttribute(GROUP_ID)),
                element.getAttribute(NAME),
                LocalDate.parse(element.getAttribute(DATE_OF_BIRTH)),
                Boolean.parseBoolean(element.getAttribute(HAS_BONUS))
        );
        worker.setSalary(Float.parseFloat(element.getAttribute(SALARY)));
        addWorker(worker);
    }
}