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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.chiem.alameringen.Helpers.OnRouteCallback;
import com.chiem.alameringen.Helpers.RouteManager;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class EmergencyDetailFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnPolylineClickListener,
        LocationSource, LocationListener, GoogleMap.OnMyLocationButtonClickListener, OnRouteCallback {

    private Emergency emergency;
    private RouteManager routeManager;
    private ArrayList<Polyline> routeLines;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private Location currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLocation();

        routeLines = new ArrayList<>();
        routeManager = new RouteManager(this, getContext());

        Bundle args = getArguments();
        emergency = (Emergency)args.getSerializable("EMERGENCY");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.emergency_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.imgIcon);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtPlace = view.findViewById(R.id.txtPlace);
        TextView txtDate = view.findViewById(R.id.txtDate);

        imageView.setBackgroundResource(R.drawable.ic_emergency_icon);
        txtTitle.setText(emergency.getText());
        txtPlace.setText(emergency.getPlace());
        txtDate.setText(emergency.getDate());
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

                    LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    LatLng emergencyLocation = new LatLng(Double.parseDouble(emergency.getLat()), Double.parseDouble(emergency.getLon()));
                    routeManager.fetchRoute(myLocation, emergencyLocation);

                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(EmergencyDetailFragment.this);
                }
            }
        });
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
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        googleMap.setMyLocationEnabled(true);


        LatLng latLng = new LatLng(Double.parseDouble(emergency.getLat()), Double.parseDouble(emergency.getLon()));

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng));
        marker.setTag(0);


        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

    }

    @Override
    public void OnRouteLoaded(PolylineOptions options, LatLngBounds bounds, int padding) {
        routeLines.add(googleMap.addPolyline(options));
    }
}