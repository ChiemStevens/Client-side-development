package com.chiem.hueapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.Network.ApiManager;

import java.util.ArrayList;


public class SingleLightActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private Light light;

    private ArrayList<Integer> averageBrightnessList;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_light);

        this.apiManager = new ApiManager(this);

        this.light = (Light)getIntent().getSerializableExtra("LIGHT");

        TextView lblLightName = findViewById(R.id.lblLightName);
        Switch lightSwitch = findViewById(R.id.switchOnOff)  ;
        SeekBar brightNessBar = findViewById(R.id.brightnessBar);
        brightNessBar.setMax(254);
        final TextView txtBrigntnessValue = findViewById(R.id.txtBrightnessValue);

        brightNessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtBrigntnessValue.setText(progress + "");

                if(averageBrightnessList != null) {
                    averageBrightnessList.add(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                averageBrightnessList = new ArrayList<>();
                countDownTimer = new CountDownTimer(100000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        changeBrightness(averageBrightnessList);
                    }

                    @Override
                    public void onFinish() {
                        countDownTimer.cancel();
                    }
                }.start();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                changeBrightness(seekBar.getProgress());
            }
        });

        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                turnLightOnOff(isChecked);
            }
        });

        lblLightName.setText(light.getName());
        brightNessBar.setProgress((int)light.getLightState().getBri());
        txtBrigntnessValue.setText((int)light.getLightState().getBri() + "");

        if(light.getLightState().isOn()) {
            lightSwitch.setChecked(true);
        }
    }

    private void changeBrightness(int value) {
        light.getLightState().setBri(value);
        apiManager.changeLightBri(light.getSendKey(), value);
    }

    private void changeBrightness(ArrayList<Integer> brightNesses) {

        if(brightNesses.size() > 0) {
            int average = 0;

            for(int i = 0; i < brightNesses.size(); i++) {
                average += brightNesses.get(i);
            }
            average /= brightNesses.size();

            light.getLightState().setBri(average);
            apiManager.changeLightBri(light.getSendKey(), average);

            averageBrightnessList = new ArrayList<>();
        }
    }

    private void turnLightOnOff(boolean state) {

        this.light.getLightState().setOn(state);
        apiManager.changeLight(light);
    }
}
