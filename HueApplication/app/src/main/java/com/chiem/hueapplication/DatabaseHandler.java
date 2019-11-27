package com.chiem.hueapplication;

import com.chiem.hueapplication.Models.Connection;

import java.util.ArrayList;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    private ArrayList<Connection> connections;

    private DatabaseHandler() {
        this.connections = new ArrayList<>();
    }

    public ArrayList<Connection> GetPrevConnections() {
        this.connections.add(new Connection("Test", "192.168.2.143", "1234", true, "newdeveloper"));
        return this.connections;
    }

    public static DatabaseHandler getInstance() {

        if(instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;

    }
}
