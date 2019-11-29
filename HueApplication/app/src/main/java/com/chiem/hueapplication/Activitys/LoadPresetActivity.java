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
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoadPresetActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private PresetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_preset);

        Light light = (Light)getIntent().getSerializableExtra("LIGHT");

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        ArrayList<Light> presets = databaseHandler.getPresets();

        for(int i = 0; i < presets.size(); i++) {
            Gson gson= new Gson();
            String tmp = gson.toJson(presets.get(i));
            Light tempPresetLight = gson.fromJson(tmp, Light.class);

            tmp = gson.toJson(light);
            Light tempLight = gson.fromJson(tmp, Light.class);

            presets.set(i, tempLight);

            presets.get(i).setType(tempPresetLight.getName());
            presets.get(i).getLightState().setSat(tempPresetLight.getLightState().getSat());
            presets.get(i).getLightState().setHue(tempPresetLight.getLightState().getHue());
            presets.get(i).getLightState().setBri(tempPresetLight.getLightState().getBri());
        }

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
