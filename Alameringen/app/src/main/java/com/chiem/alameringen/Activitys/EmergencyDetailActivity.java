package com.chiem.alameringen.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiem.alameringen.Helpers.NavigationHelper;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class EmergencyDetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnPolylineClickListener,
        LocationSource, LocationListener, GoogleMap.OnMyLocationButtonClickListener {

    private Emergency emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_detail);

        emergency = (Emergency)getIntent().getSerializableExtra("EMERGENCY");


        ImageView imageView = findViewById(R.id.imgIcon);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtPlace = findViewById(R.id.txtPlace);
        TextView txtDate = findViewById(R.id.txtDate);

        imageView.setBackgroundResource(R.drawable.ic_emergency_icon);
        txtTitle.setText(emergency.getText());
        txtPlace.setText(emergency.getPlace());
        txtDate.setText(emergency.getDate());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NavigationBinder();
    }

    private void NavigationBinder() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent = NavigationHelper.NavigateMenuItemClick(getApplicationContext(), item);
                startActivity(intent);
                return true;
            }
        });

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

        googleMap.setLocationSource(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);


        LatLng latLng = new LatLng(Double.parseDouble(emergency.getLat()), Double.parseDouble(emergency.getLon()));

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng));
        marker.setTag(0);


        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

    }
}
