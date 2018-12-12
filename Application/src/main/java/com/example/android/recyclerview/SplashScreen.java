package com.example.android.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.common.activities.SampleActivityBase;

import org.json.JSONObject;

public class SplashScreen extends SampleActivityBase {
    public static String[] wins = new String[6];
    public static int winPosition = 0;
    public static String[] losses = new String[6];
    public static int lossesPosition = 0;
    private static String[] symbols = {"RF", "CIA", "FB", "T", "AAPL", "GOOGL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Button button = (Button)findViewById(R.id.toMain);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        for (int i = 0; i < symbols.length; ++i) {
            startAPICall(requestQueue, symbols[i], i);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        });
    }
    private void addChanges(String change) {
        if (change.contains(": \n-")) {
            losses[lossesPosition++] = change;
        } else {
            wins[winPosition++] = change;
        }
    }

    public void startAPICall(RequestQueue requestQueue, final String symbol, final int position) {
        /*RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(
                "https://api.iextrading.com/1.0/stock/" + symbol + "/batch?types=quote",
                new JSONObject(),
                future,
                future
        );
        try {
            JSONObject response = future.get(10, TimeUnit.SECONDS);
            Log.d(TAG, "Before Changes");
            addChanges(response.getJSONObject("quote").getString("changePercent"), position);
        } catch (Exception e) {

        }*/
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/" + symbol + "/batch?types=quote",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                Log.d(TAG + "!", (response.getJSONObject("quote")).getString("changePercent"));
                                addChanges(response.getJSONObject("quote").getString("companyName") + "\n(" + symbol + "): " + response.getJSONObject("quote").getString("changePercent") + "%");
                                //Log.d(TAG, toReturn);
                            } catch (Exception e) {
                                addChanges(symbol);
                                Log.d(TAG, e.toString());
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
