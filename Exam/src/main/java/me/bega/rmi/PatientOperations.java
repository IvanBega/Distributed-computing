package me.bega.rmi;

import me.bega.model.Patient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface PatientOperations extends Remote {
    ArrayList<Patient> getPatientsWithDiagnosis(String diagnosis) throws RemoteException;
    ArrayList<Patient> getPatientsWithCardNumberInRange(int min, int max) throws RemoteException;
}
