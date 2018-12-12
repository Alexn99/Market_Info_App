package com.example.android.recyclerview;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class API {
    public String toReturn;
    private static final String TAG = "API_Call:";
    public void setToReturn(String string) {
        Log.d(TAG, "setToReturn");
        toReturn = string;
        Log.d(TAG, toReturn);
    }
    public void startAPICall(RequestQueue requestQueue, final String symbol) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/" + symbol + "/batch?types=quote",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                Log.d(TAG, (response.getJSONObject("quote")).getString("changePercent"));
                                //(response.getJSONObject("quote").getString("changePercent"));
                                //Log.d(TAG, toReturn);
                            } catch (Exception e) {
                                Log.d(TAG + "ERROR!", e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
