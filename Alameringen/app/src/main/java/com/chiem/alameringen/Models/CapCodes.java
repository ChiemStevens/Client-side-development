package com.chiem.alameringen.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CapCodes implements Serializable {

    private String capcode;
    private String description;

    public CapCodes(JSONObject jsonObject) {

        try {
            this.capcode = jsonObject.getString("capcode");
            this.description = jsonObject.getString("omschrijving");
        }
        catch (JSONException ex) {
            Log.e("Alarmeringen", ex.getMessage());
        }

    }

    public String getCapcode() {
        return capcode;
    }

    public void setCapcode(String capcode) {
        this.capcode = capcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
