package com.chiem.alameringen.Helpers;

import com.chiem.alameringen.Models.Emergency;

public class LandscapeHelper {

    private static LandscapeHelper instance;
    private String typeOfFragment;
    private Emergency currentEmergency;

    private LandscapeHelper() {

    }

    public String getTypeOfFragment() {
        return typeOfFragment;
    }

    public void setTypeOfFragment(String typeOfFragment) {
        this.typeOfFragment = typeOfFragment;
    }

    public Emergency getCurrentEmergency() {
        return currentEmergency;
    }

    public void setCurrentEmergency(Emergency currentEmergency) {
        this.currentEmergency = currentEmergency;
    }

    public static LandscapeHelper getInstance() {
        if(instance == null) {
            instance = new LandscapeHelper();
        }
        return instance;
    }
}
