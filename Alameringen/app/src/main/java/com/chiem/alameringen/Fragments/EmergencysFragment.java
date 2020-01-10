package com.chiem.alameringen.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.alameringen.Adapters.EmergencyAdapter;
import com.chiem.alameringen.Helpers.PreferenceHelper;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmergencysFragment extends Fragment {

    private RecyclerView recyclerView = null;
    private EmergencyAdapter adapter;
    private ArrayList<Emergency> emergencies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String connectionsJSONString = PreferenceHelper.getDefaults("EMERGENCIES", getContext());
        Type type = new TypeToken<List<Emergency>>() {}.getType();
        emergencies = new Gson().fromJson(connectionsJSONString, type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.emergencies_fragment, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recylceView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EmergencyAdapter(emergencies);
        recyclerView.setAdapter(adapter);
    }
}
