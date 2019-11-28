package com.chiem.hueapplication.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chiem.hueapplication.Adapters.ConnectionAdapter;
import com.chiem.hueapplication.DatabaseHandler;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private ConnectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        ArrayList<Connection> connections = databaseHandler.GetPrevConnections();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ConnectionAdapter(connections);
        recyclerView.setAdapter(adapter);

        if(connections.size() > 0) {
            TextView lblNoConnections = findViewById(R.id.lblNoPreviousConnections);
            lblNoConnections.setVisibility(View.INVISIBLE);
        }

        //For emulator
        //http://<ip>:<port>/api/newdeveloper
        //Example:
        //http://192.168.2.143:1234/api/newdeveloper
    }

    public void onNewConnectionClick(View v) {

        Intent intent = new Intent(this, ConnectActivity.class);
        startActivity(intent);

    }
}
