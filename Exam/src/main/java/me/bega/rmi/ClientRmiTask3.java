package me.bega.rmi;

import me.bega.model.Patient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientRmiTask3 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String url = "//localhost:1612/PatientOperations";
        PatientOperations operations = (PatientOperations) Naming.lookup(url);
        System.out.println("Patients with diagnosis cholera:");
        ArrayList<Patient> patients = operations.getPatientsWithDiagnosis("cholera");
        for (Patient p : patients){
            System.out.println(p);
        }
        System.out.println("Patients with card in range [100,1000]");
        patients = operations.getPatientsWithCardNumberInRange(100,1000);
        for (Patient p : patients){
            System.out.println(p);
        }
    }
}
