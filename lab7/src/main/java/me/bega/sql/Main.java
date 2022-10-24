package me.bega.sql;

import me.bega.sql.ui.Interface;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Interface i = new Interface();
        i.start();
    }
}
