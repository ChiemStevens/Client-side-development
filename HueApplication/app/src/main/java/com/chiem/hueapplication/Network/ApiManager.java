package com.chiem.hueapplication.Network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chiem.hueapplication.ConnectionReminder;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ApiManager {

    private Context context;
    private IApiObserver observer;
    private RequestQueue queue;

    private String baseUrl;

    public ApiManager(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
        if(ConnectionReminder.getInstance().isHasSavedConnection()) {
            makeBaseUrl(ConnectionReminder.getInstance().getSavedConnection());
        }
    }

    public ApiManager(Context context, IApiObserver iApiObserver, Connection connection) {
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
        this.observer = iApiObserver;
        makeBaseUrl(connection);
    }

    private void makeBaseUrl(Connection connection) {
        this.baseUrl = "http://" + connection.getIp();

        if(connection.isEmulator()) {
            this.baseUrl += ":" + connection.getPort();
        }

        this.baseUrl += "/api/" + connection.getKey();
    }


    public void getLights() {

        String url = baseUrl + "/lights";

        final JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Iterator<String> keys = response.keys();

                            while(keys.hasNext()) {
                                String key = keys.next();
                                if (response.get(key) instanceof JSONObject) {

                                    Light light = new Light(key, response.getJSONObject(key));
                                    observer.addLight(light);
                                }
                            }
                        }
                        catch (JSONException ex) {

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                    }
                }
        );

        queue.add(request);
    }

    public void changeLight(Light light) {
        String url = baseUrl + "/lights/" + light.getSendKey() + "/state/";

        Gson gson = new Gson();
        String json = gson.toJson(light.getLightState());
        JsonObject jsonObjectToParse = JsonParser.parseString(json).getAsJsonObject();
        JSONObject jsonToUse = new JSONObject();
        try {
            jsonToUse = new JSONObject(jsonObjectToParse.toString());
        }
        catch (Exception ex) {

        }

        final JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.PUT,
                url,
                jsonToUse,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                    }
                }
        );

        queue.add(request);
    }

    public void changeLightBri(String lightSendKey ,int briValue) {
        String url = baseUrl + "/lights/" + lightSendKey + "/state/";

        String json = "{\"bri\":" +  briValue +"}";
        JsonObject jsonObjectToParse = JsonParser.parseString(json).getAsJsonObject();
        JSONObject jsonToUse = new JSONObject();
        try {
            jsonToUse = new JSONObject(jsonObjectToParse.toString());
        }
        catch (Exception ex) {

        }

        final JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.PUT,
                url,
                jsonToUse,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                    }
                }
        );

        queue.add(request);
    }
}