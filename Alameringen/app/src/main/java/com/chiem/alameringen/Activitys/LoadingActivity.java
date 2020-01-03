package com.chiem.alameringen.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.chiem.alameringen.API.APIManager;
import com.chiem.alameringen.API.EmergencyApiListener;
import com.chiem.alameringen.Helpers.DatabaseManager;
import com.chiem.alameringen.Helpers.PreferenceHelper;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.Models.Place;
import com.chiem.alameringen.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity implements EmergencyApiListener {

    private ArrayList<Emergency> emergencies;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private APIManager apiManager;
    private int allEmergenciesCounter = 0;
    private int currentEmergenciesRecievedCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        emergencies = new ArrayList<>();
        apiManager = new APIManager(getApplicationContext(), this);

        if(checkLocationPermission(this)) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fetchLocation();
        }

        DatabaseManager databaseManager = new DatabaseManager(this);
        ArrayList<Place> places = databaseManager.getPlaces();

        for(Place place : places) {
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

        if(currentEmergenciesRecievedCounter == allEmergenciesCounter) {

            String connectionsJSONString = new Gson().toJson(emergencies);
            PreferenceHelper.setDefaults("EMERGENCIES", connectionsJSONString, this);

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    }

    @Override
    public void onPlaceRecieved(Place place) {
        allEmergenciesCounter++;
        PreferenceHelper.setDefaults("CURRENT-PLACE", place.getName(), this);
        apiManager.getEmergencys(place);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void fetchLocation() {
        if(!checkLocationPermission(this)) {
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    apiManager.getCurrentPlace(currentLocation.getLatitude(), currentLocation.getLongitude());
                }
            }
        });
    }
}
