package me.bega.model;

import java.io.Serializable;

public class Patient implements Serializable {
    private int id;
    private String name;
    private String diagnosis;
    private int cardNumber;
    public Patient(int id, String name, String diagnosis, int cardNumber)
    {
        this.id = id;
        this.name = name;
        this.diagnosis = diagnosis;
        this.cardNumber = cardNumber;
    }
    public String toString(){
        return "Id: " + id + ", name: " + name + ", diagnosis: " + diagnosis + ", card Number: " + cardNumber;
    }
    public String getDiagnosis(){
        return diagnosis;
    }
    public int getCardNumber(){
        return cardNumber;
    }
}
