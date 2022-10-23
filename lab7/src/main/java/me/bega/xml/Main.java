package me.bega.xml;

import me.bega.xml.ui.Interface;
import org.xml.sax.SAXException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException {
        Interface i = new Interface();
        i.start();
    }
}
