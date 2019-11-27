package com.chiem.hueapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class ConnectActivity extends AppCompatActivity {

    private RadioButton rdoEmulator;
    private RadioButton rdoBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

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
}
