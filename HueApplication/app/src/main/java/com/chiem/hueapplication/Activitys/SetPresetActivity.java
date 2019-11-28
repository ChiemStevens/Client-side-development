package com.chiem.hueapplication.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chiem.hueapplication.DatabaseHandler;
import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.R;

public class SetPresetActivity extends AppCompatActivity {

    private Light light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_preset);

        light = (Light)getIntent().getSerializableExtra("LIGHT");

        Button btn = findViewById(R.id.btnAddPreset);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddClick();
            }
        });
    }

    public void btnAddClick() {

        TextView textView = findViewById(R.id.txtName);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        boolean nameExists = databaseHandler.addPreset(textView.getText().toString(), light);

        if(!nameExists) {
            Intent intent = new Intent(this, SingleLightActivity.class);
            intent.putExtra("LIGHT", light);
            startActivity(intent);
        }
        else {
            //TODO: SHOW ERROR MESSAGE
        }


    }
}
