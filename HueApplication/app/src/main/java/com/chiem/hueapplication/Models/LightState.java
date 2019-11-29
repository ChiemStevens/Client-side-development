package com.chiem.hueapplication.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LightState implements Serializable {

    private int x;
    private int y;
    private int ct;
    private String alert;
    private int sat;
    private String effect;
    private int bri;
    private int hue;
    private String colormode;
    private boolean reachable;
    private boolean on;

    public LightState() {

    }

    public LightState(JSONObject jsonObject) {
        try {
            this.x = jsonObject.getJSONArray("xy").getInt(0);
            this.x = jsonObject.getJSONArray("xy").getInt(1);
            this.ct = jsonObject.getInt("ct");
            this.alert = jsonObject.getString("alert");
            this.sat = jsonObject.getInt("sat");
            this.effect = jsonObject.getString("effect");
            this.bri = jsonObject.getInt("bri");
            this.hue = jsonObject.getInt("hue");
            this.colormode = jsonObject.getString("colormode");
            this.reachable = jsonObject.getBoolean("reachable");
            this.on = jsonObject.getBoolean("on");
         }
        catch (JSONException ex) {
            Log.e("JSONException", ex.getMessage());
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
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
