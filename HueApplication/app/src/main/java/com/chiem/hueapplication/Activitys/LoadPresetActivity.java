package com.chiem.hueapplication.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chiem.hueapplication.Adapters.ConnectionAdapter;
import com.chiem.hueapplication.Adapters.PresetAdapter;
import com.chiem.hueapplication.DatabaseHandler;
import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.R;

import java.util.ArrayList;

public class LoadPresetActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private PresetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_preset);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        ArrayList<Light> presets = databaseHandler.getPresets();

        if(presets.size() > 0) {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PresetAdapter(presets);
            recyclerView.setAdapter(adapter);
        }
        else {
            TextView txtNoPresets = findViewById(R.id.lblNoPresets);
            txtNoPresets.setVisibility(View.VISIBLE);
        }

    }
}
