package com.chiem.alameringen.Models;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    private String name;
    private LatLng position;

    public Place(String name, LatLng position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
