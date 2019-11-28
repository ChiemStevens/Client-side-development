package com.chiem.hueapplication.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.chiem.hueapplication.Adapters.LightAdapter;
import com.chiem.hueapplication.Helpers.ConnectionReminder;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.Network.ApiManager;
import com.chiem.hueapplication.Network.IApiObserver;
import com.chiem.hueapplication.R;

import java.util.ArrayList;

public class ConnectedActivity extends AppCompatActivity implements IApiObserver {

    private RecyclerView recyclerView;
    private LightAdapter adapter;
    private ArrayList<Light> lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected);

        lights = new ArrayList<>();

        recyclerView = findViewById(R.id.recylceView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LightAdapter(lights);
        recyclerView.setAdapter(adapter);

        Connection connection = (Connection) getIntent().getSerializableExtra("CONNECTION");
        ConnectionReminder.getInstance().setConnectionToSave(connection);
        ApiManager apiManager = new ApiManager(this, this, connection);
        apiManager.getLights();
    }

    @Override
    public void addLight(Light light) {
        lights.add(light);
        adapter.notifyDataSetChanged();
    }
}
