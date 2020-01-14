package com.chiem.alameringen.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chiem.alameringen.API.APIManager;
import com.chiem.alameringen.API.EmergencyApiListener;
import com.chiem.alameringen.Activitys.MainActivity;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.Models.Place;
import com.chiem.alameringen.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class EmergencyLoader implements EmergencyApiListener {

    private ArrayList<Emergency> emergencies;
    private APIManager apiManager;
    private int allEmergenciesCounter = 0;
    private int currentEmergenciesRecievedCounter = 0;
    private Context context;


    public EmergencyLoader(Context context) {
        this.context = context;
    }

    public void loadEmergencies() {
        String currentPlace = PreferenceHelper.getDefaults("CURRENT-PLACE", context);

        emergencies = new ArrayList<>();
        apiManager = new APIManager(context, this);

        DatabaseManager databaseManager = new DatabaseManager(context);
        ArrayList<Place> places = databaseManager.getPlaces();
        places.add(new Place(currentPlace, null));

        for (Place place : places) {
            apiManager.getEmergencys(place);
            allEmergenciesCounter++;
        }
    }

    @Override
    public void onEmergencySucces(Emergency emergency) {

        emergencies.add(emergency);

    }

    @Override
    public void onAllEmergencysRecieved() {

        currentEmergenciesRecievedCounter++;

        if (currentEmergenciesRecievedCounter == allEmergenciesCounter) {

            Collections.sort(emergencies, Collections.reverseOrder());

            String connectionsJSONString = new Gson().toJson(emergencies);
            PreferenceHelper.setDefaults("EMERGENCIES", connectionsJSONString, context);
        }

    }

    @Override
    public void onPlaceRecieved(Place place) {

    }
}