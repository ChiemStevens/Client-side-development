package com.chiem.alameringen.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chiem.alameringen.Helpers.Debug;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.Models.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIManager  {

    private Context context;
    private RequestQueue queue;
    private EmergencyApiListener listener;

    private final String placeURL = "https://api.opencagedata.com/geocode/v1/json?q=";
    private final String alarmeringURL = "https://www.alarmeringdroid.nl/api/livesearch?search=";

    // Constructor
    public APIManager(Context context, EmergencyApiListener listener) {
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
        this.listener = listener;
    }

    public void getEmergencys(Place place) {

        String newUrl = alarmeringURL + place.getName() + "&id=" + 0;

        final JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.GET,
                newUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(Debug.TAG, "JsonResponse: " + response.toString());

                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            for( int i = 0; i < jsonArray.length(); i++) {
                                Emergency emergency = new Emergency(jsonArray.getJSONObject(i));

                                listener.onEmergencySucces(emergency);
                            }

                            listener.onAllEmergencysRecieved();
                        } catch (JSONException e) {
                            Log.e(Debug.TAG, "ApiManager, getEmergencys, jsonException: " + e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Debug.TAG, "ApiManager, getEmergencys, response error: " +  error.toString());
                    }
                }
        );

        queue.add(request);
    }

    public void getCurrentPlace(double lat, double lon) {

        final LatLng latLng = new LatLng(lat, lon);
        String key = "2bfeac81de1e490fba34250b5807d5a1";
        String newUrl = placeURL + lat + "+" + lon + "&key=" + key;

        final JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.GET,
                newUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for( int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                jsonObject = jsonObject.getJSONObject("components");

                                String city = "";
                                try {
                                    city = jsonObject.getString("city");
                                }
                                catch (JSONException e) {
                                    Log.e(Debug.TAG, "ApiManager, getCurrentPlace, jsonException: " + e.getMessage());
                                }

                                if(city.isEmpty()) {
                                    try {
                                        city = jsonObject.getString("suburb");
                                    }
                                    catch (JSONException e) {
                                        Log.e(Debug.TAG, "ApiManager, getCurrentPlace, jsonException: " + e.getMessage());
                                    }
                                }

                                if(city.isEmpty()) {
                                    try {
                                        city = jsonObject.getString("village");
                                    }
                                    catch (JSONException e) {
                                        Log.e(Debug.TAG, "ApiManager, getCurrentPlace, jsonException: " + e.getMessage());
                                    }
                                }

                                Place place = new Place(city, latLng);
                                listener.onPlaceRecieved(place);
                            }


                        } catch (JSONException e) {
                            Log.e(Debug.TAG, "ApiManager, getCurrentPlace, jsonException: " + e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Debug.TAG, "ApiManager, getCurrentPlace, response error: " +  error.toString());
                    }
                }
        );

        queue.add(request);

    }
}
