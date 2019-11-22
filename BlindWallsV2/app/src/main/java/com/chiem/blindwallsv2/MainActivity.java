package com.chiem.blindwallsv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chiem.blindwallsv2.Model.BlindWall;
import com.chiem.blindwallsv2.Model.BlindWallsBreda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BlindwallsApiListener {

    private ArrayList<BlindWall> walls = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private WallsAdapter adapter;

    private BlindwallsApiManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String json = JsonUtil.loadJSONFromAsset(this);
        Log.d("test", json);

        BlindWallsBreda blindWallsBreda = BlindWallsBreda.createFromJson(json);
        walls = (ArrayList<BlindWall>) blindWallsBreda.getAllWalls();
        Log.d("Tag", blindWallsBreda.printAllWalls());*/

        manager = new BlindwallsApiManager(this, this);
        manager.getWalls();

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WallsAdapter(walls);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWallAvailable(BlindWall wallsInfo) {
        walls.add(wallsInfo);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onWallError(Error error) {
        Log.e("Wall error", error.getMessage());
    }
}
