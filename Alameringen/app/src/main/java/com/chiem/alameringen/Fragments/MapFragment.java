package com.chiem.alameringen.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chiem.alameringen.Activitys.MainActivity;
import com.chiem.alameringen.Helpers.PreferenceHelper;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnPolylineClickListener,
        LocationSource, LocationListener, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private ArrayList<Emergency> emergencies;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String connectionsJSONString = PreferenceHelper.getDefaults("EMERGENCIES", getContext());
        Type type = new TypeToken<List< Emergency >>() {}.getType();
        this.emergencies = new Gson().fromJson(connectionsJSONString, type);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.map_fragment, parent, false);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Emergency emergency = (Emergency)marker.getTag();

        EmergencyDetailFragment emergencyDetailFragment = new EmergencyDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("EMERGENCY", emergency);
        emergencyDetailFragment.setArguments(bundle);

        MainActivity activity = (MainActivity) getContext();
        activity.openFragment(emergencyDetailFragment);

        return false;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);

        for(int i = 0; i < emergencies.size(); i++) {

            LatLng latLng = new LatLng(Double.parseDouble(emergencies.get(i).getLat()), Double.parseDouble(emergencies.get(i).getLon()));

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(emergencies.get(i).getText()));
            marker.setTag(emergencies.get(i));

        }

        boolean hasPermission = checkLocationPermission(getActivity());
        if(hasPermission) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

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
        if(!checkLocationPermission(getActivity())) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapFragment.this);
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
