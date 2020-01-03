package com.chiem.alameringen.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.chiem.alameringen.Adapters.EmergencyAdapter;
import com.chiem.alameringen.Helpers.PreferenceHelper;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.Helpers.NavigationHelper;
import com.chiem.alameringen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmergencyActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private EmergencyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent = NavigationHelper.NavigateMenuItemClick(getApplicationContext(), item);
                startActivity(intent);
                return true;
            }
        });

        String connectionsJSONString = PreferenceHelper.getDefaults("EMERGENCIES", this);
        Type type = new TypeToken<List< Emergency >>() {}.getType();
        ArrayList<Emergency> emergencies = new Gson().fromJson(connectionsJSONString, type);


        recyclerView = findViewById(R.id.recylceView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmergencyAdapter(emergencies);
        recyclerView.setAdapter(adapter);
    }
}
