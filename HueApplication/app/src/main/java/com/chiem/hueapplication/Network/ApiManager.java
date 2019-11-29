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
import com.chiem.hueapplication.Helpers.ConnectionReminder;
import com.chiem.hueapplication.Helpers.JsonArrayRequestWithJsonObject;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
    private boolean hasBaseUrl = false;

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

        hasBaseUrl = true;
    }

    public void tryLink(Connection connection) {

        String url = "http://" + connection.getIp() + ":" + connection.getPort() + "/api/";

        String json = "{\"devicetype\":\"HueApp#" + connection.getName() + "\"}";
        JsonObject jsonObjectToParse = JsonParser.parseString(json).getAsJsonObject();
        JSONObject jsonToUse = new JSONObject();
        try {
            jsonToUse = new JSONObject(jsonObjectToParse.toString());
        }
        catch (Exception ex) {

        }

        final JsonArrayRequestWithJsonObject request = new JsonArrayRequestWithJsonObject(
                Request.Method.POST,
                url,
                jsonToUse,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String key = response.getJSONObject(0).getJSONObject("success").getString("username");
                            observer.addLink(key);
                        }
                        catch (Exception ex) {
                            Log.e("Json-error", ex.getMessage());
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


    public void getLights() {

        if(hasBaseUrl) {

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
    }

    public void changeLight(Light light) {

        if(hasBaseUrl) {

            String url = baseUrl + "/lights/" + light.getSendKey() + "/state/";

            String json = "{\"hue\": " + light.getLightState().getHue() + ", " +
                    "\"sat\": " + light.getLightState().getSat() + ", " +
                    "\"bri\": " + light.getLightState().getBri() + ", " +
                    "\"on\": " + light.getLightState().isOn() + "}";

            JsonObject jsonObjectToParse = JsonParser.parseString(json).getAsJsonObject();
            JSONObject jsonToUse = new JSONObject();
            try {
                jsonToUse = new JSONObject(jsonObjectToParse.toString());
            }
            catch (Exception ex) {

            }

            final JsonArrayRequestWithJsonObject request = new JsonArrayRequestWithJsonObject(

                    Request.Method.PUT,
                    url,
                    jsonToUse,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

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

    public void changeLightBri(String lightSendKey ,int briValue) {

        if(hasBaseUrl) {

            String url = baseUrl + "/lights/" + lightSendKey + "/state/";

            String json = "{\"bri\":" +  briValue +"}";
            JsonObject jsonObjectToParse = JsonParser.parseString(json).getAsJsonObject();
            JSONObject jsonToUse = new JSONObject();
            try {
                jsonToUse = new JSONObject(jsonObjectToParse.toString());
            }
            catch (Exception ex) {

            }

            final JsonArrayRequestWithJsonObject request = new JsonArrayRequestWithJsonObject(

                    Request.Method.PUT,
                    url,
                    jsonToUse,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

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

    public void changeHue(String lightSendKey, int hue, int sateration) {

        if(hasBaseUrl) {

            String url = baseUrl + "/lights/" + lightSendKey + "/state/";

            String json = "{\"hue\":" +  hue +", \"sat\":" + sateration + "}";
            JsonObject jsonObjectToParse = JsonParser.parseString(json).getAsJsonObject();
            JSONObject jsonToUse = new JSONObject();
            try {
                jsonToUse = new JSONObject(jsonObjectToParse.toString());
            }
            catch (Exception ex) {

            }

            final JsonArrayRequestWithJsonObject request = new JsonArrayRequestWithJsonObject(

                    Request.Method.PUT,
                    url,
                    jsonToUse,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

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
}
