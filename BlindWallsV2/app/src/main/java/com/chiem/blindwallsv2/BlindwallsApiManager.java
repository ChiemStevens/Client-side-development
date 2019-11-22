package com.chiem.blindwallsv2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.chiem.blindwallsv2.Model.BlindWall;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

public class BlindwallsApiManager {

    private Context context;
    private RequestQueue queue;
    private BlindwallsApiListener listener;

    private final String url = "https://api.blindwalls.gallery/apiv2/murals";

    // Constructor
    public BlindwallsApiManager(Context context, BlindwallsApiListener listener) {
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
        this.listener = listener;
    }

    public void getWalls() {
        final JsonArrayRequest request = new JsonArrayRequest(

                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VOLLEY_TAG", response.toString());

                        try {
                            // JSONArray jsonArray = new JSONArray(response);
                            for( int idx = 0; idx < response.length(); idx++) {
                                BlindWall wallsInfo = new BlindWall(response.getJSONObject(idx));

                                listener.onWallAvailable(wallsInfo);
                            }
                        } catch (JSONException e) {
                            Log.e("VOLLEY_TAG", e.getLocalizedMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG", error.toString());
                        listener.onWallError( new Error("Fout bij ophalen murals") );
                    }
                }
        );

        queue.add(request);
    }

}
