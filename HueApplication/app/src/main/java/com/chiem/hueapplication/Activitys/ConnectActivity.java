package com.chiem.hueapplication.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.chiem.hueapplication.DatabaseHandler;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.R;

public class ConnectActivity extends AppCompatActivity {

    private RadioButton rdoEmulator;
    private RadioButton rdoBridge;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        databaseHandler = new DatabaseHandler(this);

        this.rdoBridge = findViewById(R.id.rdoBridge);
        this.rdoEmulator = findViewById(R.id.rdoEmulator);
        this.rdoEmulator.setChecked(true);

        rdoBridge.setOnClickListener(rdoBridgeListener);
        rdoEmulator.setOnClickListener(rdoEmulatorListener);
    }

    View.OnClickListener rdoEmulatorListener = new View.OnClickListener(){
        public void onClick(View v) {

            if(rdoEmulator.isChecked()) {
                rdoEmulator.setChecked(true);
                rdoBridge.setChecked(false);
                EditText portText = findViewById(R.id.txtPort);
                portText.setEnabled(true);
            }

        }
    };

    View.OnClickListener rdoBridgeListener = new View.OnClickListener(){
        public void onClick(View v) {

            if(rdoBridge.isChecked()) {

                rdoBridge.setChecked(true);
                rdoEmulator.setChecked(false);
                EditText portText = findViewById(R.id.txtPort);
                portText.setEnabled(false);

            }

        }
    };

    public void OnConnectClick(View v) {

        EditText txtName = findViewById(R.id.txtName);
        EditText txtIp = findViewById(R.id.txtIpAdress);
        EditText txtPort = findViewById(R.id.txtPort);

        boolean emulator = true;
        if(rdoBridge.isChecked()) {
            emulator = false;
        }

        Connection connection = new Connection(txtName.getText().toString(), txtIp.getText().toString(),
                txtPort.getText().toString(), emulator, "newdeveloper");
        databaseHandler.AddConnection(connection);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
