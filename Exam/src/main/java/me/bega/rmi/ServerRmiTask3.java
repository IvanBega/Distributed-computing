package me.bega.rmi;

import me.bega.model.Patient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerRmiTask3 extends UnicastRemoteObject implements PatientOperations {
    private ArrayList<Patient> patients;
    public ServerRmiTask3() throws RemoteException {
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

    }

    @Override
    public ArrayList<Patient> getPatientsWithDiagnosis(String diagnosis) throws RemoteException {
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient p : patients){
            if (p.getDiagnosis().equals(diagnosis)){
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Patient> getPatientsWithCardNumberInRange(int min, int max) throws RemoteException {
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient p : patients){
            if (p.getCardNumber() >= min && p.getCardNumber() <= max){
                result.add(p);
            }
        }
        return result;
    }
}
