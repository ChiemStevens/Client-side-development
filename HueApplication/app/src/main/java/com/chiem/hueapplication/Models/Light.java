package com.chiem.hueapplication.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Light implements Serializable {

    private String sendKey;
    private String modelId;
    private String name;
    private String swVersion;
    private LightState lightState;
    private String type;
    private String uniqueid;

    public Light(String sendKey, JSONObject object) {
        this.sendKey = sendKey;

        try {
            this.modelId = object.getString("modelid");
            this.name = object.getString("name");
            this.swVersion = object.getString("swversion");
            this.lightState = new LightState(object.getJSONObject("state"));
            this.type = object.getString("type");
            this.uniqueid = object.getString("uniqueid");
        }
        catch (JSONException ex) {
            Log.e("JSONException", ex.getMessage());
        }
    }

    public Light(String name, int brightness, int hue, int sat) {
        this.name = name;
        this.setLightState(new LightState());
        this.getLightState().setBri(brightness);
        this.getLightState().setHue(hue);
        this.getLightState().setSat(sat);
    }

    public String getSendKey() {
        return sendKey;
    }

    public void setSendKey(String sendKey) {
        this.sendKey = sendKey;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(String swVersion) {
        this.swVersion = swVersion;
    }

    public LightState getLightState() {
        return lightState;
    }

    public void setLightState(LightState lightState) {
        this.lightState = lightState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
}
