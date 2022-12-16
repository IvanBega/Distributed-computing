package me.bega.socket;

import me.bega.model.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerSocketTask3 {
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private static ArrayList<Patient> patients;

    public void start(int port) {
        try {

            server = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client connected");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (processQuery()) ;
            }
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
        }
    }

    private boolean processQuery() {
        int compCode = 0;
        try {
            String query = in.readLine();

            if (query == null)
                return false;

            String[] requestData = query.split(",");

            String response = compCode + "#";

            switch (requestData[0])
            {
                case "diagnosis":
                    for (Patient p : patients) {
                        if (p.getDiagnosis().equals(requestData[1])) {
                            response += query + "#" + p.toString() + "\n";
                        }
                    }
                    break;
                case "cholera":
                    for (Patient p : patients) {
                        if (p.getCardNumber() >= Integer.parseInt(requestData[1]) &&
                        p.getCardNumber() <= Integer.parseInt(requestData[2])) {
                            response += query + "#" + p.toString() + "\n";
                        }
                    }
                    break;
            }


            out.println(response);
            return true;
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        patients = new ArrayList<>(Arrays.asList(
                new Patient(0, "Ivan", "cholera", 34),
                new Patient(1, "John", "asthma", 78),
                new Patient(2, "Sam", "flu", 4),
                new Patient(3, "Mindy", "cholera", 8),
                new Patient(4, "Alex", "cholera", 10),
                new Patient(5, "Richard", "covid", 20),
                new Patient(6, "William", "cholera", 950),
                new Patient(7, "Sandy", "cholera", 340),
                new Patient(8, "Mortis", "cholera", 35),
                new Patient(9, "Jelly", "cholera", 89)
        ));

        ServerSocketTask3 srv = new ServerSocketTask3();
        srv.start(21160);
    }
}