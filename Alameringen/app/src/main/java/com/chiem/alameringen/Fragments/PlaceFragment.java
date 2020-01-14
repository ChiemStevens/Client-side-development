package com.chiem.alameringen.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.alameringen.Adapters.PlaceAdapter;
import com.chiem.alameringen.Helpers.DatabaseManager;
import com.chiem.alameringen.Helpers.EmergencyLoader;
import com.chiem.alameringen.Helpers.LandscapeHelper;
import com.chiem.alameringen.Helpers.PreferenceHelper;
import com.chiem.alameringen.Models.Place;
import com.chiem.alameringen.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlaceFragment extends Fragment {

    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private PlaceAdapter adapter;
    private ArrayList<Place> savedPlaces;
    private AutoCompleteTextView autoCompleteTextView;
    private TextView lblNoPlaces;
    private Place currentPlace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> places = readLines();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, places);
        autoCompleteTextView = view.findViewById(R.id.txtPlace);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);

        String placeName = PreferenceHelper.getDefaults("CURRENT-PLACE", getContext());
        currentPlace = new Place(placeName, null);

        savedPlaces = databaseManager.getPlaces();
        savedPlaces.add(0, currentPlace);

        lblNoPlaces = view.findViewById(R.id.txtNoPlaces);
        if(savedPlaces.size() > 0) {
            lblNoPlaces.setVisibility(View.INVISIBLE);
        }
        else {
            lblNoPlaces.setVisibility(View.INVISIBLE);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new PlaceAdapter(savedPlaces);
        recyclerView.setAdapter(this.adapter);

        Button btn = view.findViewById(R.id.btnSearch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSearchClick(v);
            }
        });
    }

    public void onBtnSearchClick(View view) {

        String text = autoCompleteTextView.getText().toString();

        if(text.isEmpty()) {
            return;
        }
        autoCompleteTextView.setText("");


        databaseManager.insertPlace(new Place(text, null));
        savedPlaces = databaseManager.getPlaces();
        savedPlaces.add(0, currentPlace);

        this.adapter = new PlaceAdapter(savedPlaces);
        recyclerView.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();

        EmergencyLoader emergencyLoader = new EmergencyLoader(getContext());
        emergencyLoader.loadEmergencies();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        LandscapeHelper.getInstance().setTypeOfFragment("Place");
    }
}
