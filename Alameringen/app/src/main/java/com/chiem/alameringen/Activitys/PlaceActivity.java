package com.chiem.alameringen.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.chiem.alameringen.Adapters.EmergencyAdapter;
import com.chiem.alameringen.Adapters.PlaceAdapter;
import com.chiem.alameringen.Helpers.DatabaseManager;
import com.chiem.alameringen.Helpers.NavigationHelper;
import com.chiem.alameringen.Helpers.PreferenceHelper;
import com.chiem.alameringen.Models.Place;
import com.chiem.alameringen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private PlaceAdapter adapter;
    private ArrayList<Place> savedPlaces;
    private AutoCompleteTextView autoCompleteTextView;
    private TextView lblNoPlaces;
    private Place currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        databaseManager = new DatabaseManager(this);

        NavigationBinder();


        ArrayList<String> places = readLines();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, places);
        autoCompleteTextView = findViewById(R.id.txtPlace);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);


        String placeName = PreferenceHelper.getDefaults("CURRENT-PLACE", this);
        currentPlace = new Place(placeName, null);

        savedPlaces = databaseManager.getPlaces();
        savedPlaces.add(0, currentPlace);

        lblNoPlaces = findViewById(R.id.txtNoPlaces);
        if(savedPlaces.size() > 0) {
            lblNoPlaces.setVisibility(View.INVISIBLE);
        }
        else {
            lblNoPlaces.setVisibility(View.INVISIBLE);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new PlaceAdapter(savedPlaces);
        recyclerView.setAdapter(this.adapter);
    }

    public void onBtnSearchClick(View view) {

        String text = autoCompleteTextView.getText().toString();

        if(text.isEmpty()) {
            return;
        }

        databaseManager.insertPlace(new Place(text, null));

        savedPlaces =  databaseManager.getPlaces();
        savedPlaces.add(0, currentPlace);
        this.adapter.notifyDataSetChanged();

        autoCompleteTextView.setText("");

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


    private ArrayList<String> readLines() {

        ArrayList<String> places = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.places);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            while ((readLine = br.readLine()) != null) {

                places.add(readLine);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return places;
    }
}
