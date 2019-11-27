package com.chiem.hueapplication.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LightState implements Serializable {

    private double x;
    private double y;
    private double ct;
    private String alert;
    private double sat;
    private String effect;
    private double bri;
    private double hue;
    private String colormode;
    private boolean reachable;
    private boolean on;

    public LightState(JSONObject jsonObject) {
        try {
            this.x = jsonObject.getJSONArray("xy").getDouble(0);
            this.x = jsonObject.getJSONArray("xy").getDouble(1);
            this.ct = jsonObject.getDouble("ct");
            this.alert = jsonObject.getString("alert");
            this.sat = jsonObject.getDouble("sat");
            this.effect = jsonObject.getString("effect");
            this.bri = jsonObject.getDouble("bri");
            this.hue = jsonObject.getDouble("hue");
            this.colormode = jsonObject.getString("colormode");
            this.reachable = jsonObject.getBoolean("reachable");
            this.on = jsonObject.getBoolean("on");
         }
        catch (JSONException ex) {
            Log.e("JSONException", ex.getMessage());
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCt() {
        return ct;
    }

    public void setCt(double ct) {
        this.ct = ct;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public double getSat() {
        return sat;
    }

    public void setSat(double sat) {
        this.sat = sat;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public double getBri() {
        return bri;
    }

    public void setBri(double bri) {
        this.bri = bri;
    }

    public double getHue() {
        return hue;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public String getColormode() {
        return colormode;
    }

    public void setColormode(String colormode) {
        this.colormode = colormode;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
