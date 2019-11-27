package com.chiem.hueapplication.Models;

import java.io.Serializable;

public class Connection implements Serializable {

    private String name;
    private String ip;
    private String port;
    private boolean isEmulator;
    private String key;

    public Connection(String name, String ip, String port, boolean isEmulator, String key) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.isEmulator = isEmulator;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isEmulator() {
        return isEmulator;
    }

    public void setEmulator(boolean emulator) {
        isEmulator = emulator;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
