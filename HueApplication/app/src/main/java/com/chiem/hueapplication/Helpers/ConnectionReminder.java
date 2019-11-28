package com.chiem.hueapplication.Helpers;

import com.chiem.hueapplication.Models.Connection;

public class ConnectionReminder {

    private static ConnectionReminder instance;
    private Connection savedConnection;
    private boolean hasSavedConnection;

    private ConnectionReminder() {

    }

    public Connection getSavedConnection() {
        return this.savedConnection;
    }

    public void setConnectionToSave(Connection c) {
        this.savedConnection = c;
        this.hasSavedConnection = true;
    }

    public boolean isHasSavedConnection() {
        return hasSavedConnection;
    }

    public static ConnectionReminder getInstance() {
        if(instance == null) {
            instance = new ConnectionReminder();
        }
        return instance;
    }
}
