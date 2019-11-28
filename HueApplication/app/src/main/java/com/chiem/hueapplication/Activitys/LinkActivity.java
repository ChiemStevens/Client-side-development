package com.chiem.hueapplication.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.chiem.hueapplication.DatabaseHandler;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.Network.ApiManager;
import com.chiem.hueapplication.Network.IApiObserver;
import com.chiem.hueapplication.R;

public class LinkActivity extends AppCompatActivity implements IApiObserver {

    private CountDownTimer countDownTimer;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        this.connection = (Connection) getIntent().getSerializableExtra("CONNECTION");
        final ApiManager apiManager = new ApiManager(this, this, connection);

        final TextView txtTimer = findViewById(R.id.lblTimer);
        this.countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("00:" + millisUntilFinished / 1000);
                apiManager.tryLink(connection);
            }

            public void onFinish() {
                txtTimer.setText("Link failed!");
            }
        }.start();
    }


    @Override
    public void addLight(Light light) {
        //DO NOT IMPLEMENT HERE
    }

    @Override
    public void addLink(String key) {
        connection.setKey(key);

        this.countDownTimer.cancel();

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.AddConnection(connection);

        Intent intent = new Intent(this, ConnectedActivity.class);
        intent.putExtra("CONNECTION", connection);
        startActivity(intent);
    }
}
