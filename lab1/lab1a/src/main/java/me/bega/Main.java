package me.bega;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

public class Main {
    public static JSlider slider = new JSlider(0, 100,50);
    public static void main(String args[]){

        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(400, 500);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        slider.setLocation(20,10);
        slider.setSize(200, 30);
        JButton button = new JButton();
        button.setSize(100,20);
        button.setLocation(50,130);
        Thread thread1 = new TThread1();
        Thread thread2 = new TThread2();
        button.addActionListener((ActionEvent e) -> { thread1.start(); thread2.start();} );
        panel.add(slider);
        panel.add(button);


        SpinnerModel model = new SpinnerNumberModel(0,0,10,1);
        SpinnerModel model2 = new SpinnerNumberModel(0,0,10,1);
        JSpinner spinner1 = new JSpinner();
        spinner1.setModel(model);
        spinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (Integer) spinner1.getValue();
                thread1.setPriority(value);
            }
        });
        JSpinner spinner2 = new JSpinner();
        spinner2.setModel(model2);
        spinner2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (Integer) spinner2.getValue();
                thread2.setPriority(value);
            }
        });
        spinner1.setSize(50,50);
        spinner2.setSize(50,50);
        spinner1.setLocation(50,200);
        spinner2.setLocation(150,200);
        panel.add(spinner1);
        panel.add(spinner2);

        win.add(panel);
        win.setContentPane(panel);
        win.setVisible(true);
    }
}
