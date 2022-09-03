package me.bega;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

public class Main {
    public static JSlider slider = new JSlider(0, 100,50);
    public static JButton start1 = new JButton();
    public static JButton start2 = new JButton();
    public static JButton stop1 = new JButton();
    public static JButton stop2 = new JButton();
    public static Thread thread1 = new TThread1();
    public static Thread thread2 = new TThread2();
    public static int Semaphor = 0;
    public static JLabel label = new JLabel();
    public static void main(String args[]){

        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(400, 500);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        slider.setLocation(20,10);
        slider.setSize(200, 30);
        label.setBounds(50,100,200,400);
        start1.setBounds(100, 50, 80,80);
        start2.setBounds(100, 150, 80,80);
        stop1.setBounds(200, 50, 80,80);
        stop2.setBounds(200, 150, 80,80);
        start1.setText("start 1");
        start2.setText("start 2");
        stop1.setText("stop 1");
        stop2.setText("stop 2");
        start1.addActionListener((ActionEvent e) -> { if (Semaphor == 1) return; thread1.start(); stop2.setEnabled(false); setSemaphor(1); thread1.setPriority(9); });
        start2.addActionListener((ActionEvent e) -> { if (Semaphor == 1) return; thread2.start(); stop1.setEnabled(false); setSemaphor(1); thread2.setPriority(1); });
        stop1.addActionListener((ActionEvent e) -> { thread1.interrupt(); stop2.setEnabled(true); setSemaphor(0); });
        stop2.addActionListener((ActionEvent e) -> { thread2.interrupt(); stop1.setEnabled(true); setSemaphor(0); });
        panel.add(slider);
        //panel.add(button);
        panel.add(start1);
        panel.add(start2);
        panel.add(stop1);
        panel.add(stop2);
        panel.add(label);
        win.add(panel);
        win.setContentPane(panel);
        win.setVisible(true);
    }
    public static void setSemaphor(int id){
        if (id == 1) {
            Semaphor = 1;
            label.setText("busy");
        }
        else {
            Semaphor = 0;
            label.setText("free");
        }
    }
}
