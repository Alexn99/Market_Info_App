/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ViewAnimator;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.common.activities.SampleActivityBase;

import org.json.JSONObject;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";
    public static String[] techBundleWins = new String[15];
    public static int techWinPosition = 0;
    public static String[] techBundleLosses = new String[15];
    public static int techLossesPosition = 0;
    public static String[] healthBundleWins = new String[15];
    public static int healthWinPosition = 0;
    public static String[] healthBundleLosses = new String[15];
    public static int healthLossesPosition = 0;
    public static String[] financialBundleWins = new String[15];
    public static int financialWinsPosition = 0;
    public static String[] financialBundleLosses = new String[15];
    public static int financialLossesPosition = 0;
    public static String[] searchInfo = {"symbol", "changePercent", "companyName",
            "sector",
            "high",
            "low",
            "latest_price",
            "week52High",
            "week52Low"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] wins = SplashScreen.wins;
        String[] losses = SplashScreen.losses;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button tech = (Button)findViewById(R.id.tech);
        Button health = (Button)findViewById(R.id.health);
        Button financials = (Button)findViewById(R.id.financials);
        final SearchView search = (SearchView)findViewById(R.id.sSearch);
        search.setQueryHint("Enter Stock Symbol");
        final String[] techSymbols = {"GOOGL", "JCOM", "MELI", "SINA", "BCOR", "CHRS", "CHKP",
                "CSGP", "FB", "FTCH", "GRUB", "IAC", "SPOT", "TWTR", "VRSN"};
        final String[] healthSymbols = {"EHC", "HCA", "PINC", "SEM", "SSY", "TDOC", "THC",
                "VAS", "BKD", "NHC", "SGRY", "GEN", "QHC", "CYH", "CIVI"};
        final String[] financialSymbols = {"AER", "UHAL", "AGO", "CACC", "FCFS", "GATX", "GDOT",
                "HTZ", "TREE", "NAVI", "OMF", "SC", "SLM", "URI", "PFSI"};
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        techWinPosition = 0;
        techLossesPosition = 0;
        for (int i = 0; i < techSymbols.length; ++i) {
            startAPICall(requestQueue, techSymbols[i], i, "tech");
        }
        healthWinPosition = 0;
        healthLossesPosition = 0;
        for (int i = 0; i < healthSymbols.length; ++i) {
            startAPICall(requestQueue, healthSymbols[i], i, "health");
        }
        financialWinsPosition = 0;
        financialLossesPosition = 0;
        for (int i = 0; i < financialSymbols.length; ++i) {
            startAPICall(requestQueue, financialSymbols[i], i, "financials");
        }
        Log.d(TAG + "!!!!!!!", searchInfo[8]);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle test = new Bundle();
            Bundle testL = new Bundle();
            test.putStringArray("changes", wins);
            testL.putStringArray("changesD", losses);
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            fragment.setArguments(test);
            RecyclerViewFragment fragment2 = new RecyclerViewFragment();
            fragment2.setArguments(testL);
            //fragment.setData(api, requestQueue, symbols);
            //fragment2.setData(api, requestQueue, symbols);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.replace(R.id.sample_content_fragment2, fragment2);
            transaction.commit();
        }

        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Tech.class));
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Health.class));
            }
        });
        financials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Financials.class));
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG + "B4_API_CALL", query);
                searchAPICall(requestQueue, query);
                startActivity(new Intent(MainActivity.this, StockSearch.class));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void searchChanges(String[] data) {
        searchInfo = data;
    }
    private void addChanges(String change, int position, String type) {
        if (type.equals("tech")) {
            Log.d(TAG, "addingChangesTech");
            if (change.contains(": -")) {
                techBundleLosses[techLossesPosition++] = change;
            } else {
                techBundleWins[techWinPosition++] = change;
            }

            //Log.d(TAG, techBundle[position]);
        }
        if (type.equals("health")) {
            Log.d(TAG, "addingChangesHealth");
            if (change.contains(": -")) {
                healthBundleLosses[healthLossesPosition++] = change;
            } else {
                healthBundleWins[healthWinPosition++] = change;
            }
            //Log.d(TAG, healthBundle[position]);
        }
        if (type.equals("financials")) {
            Log.d(TAG, "addingChangesFinancials");
            if (change.contains(": -")) {
                financialBundleLosses[financialLossesPosition++] = change;
            } else {
                financialBundleWins[financialWinsPosition++] = change;
            }
            //Log.d(TAG, financialBundle[position]);
        }
    }
    public void searchAPICall(RequestQueue requestQueue, final String symbol) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/" + symbol + "/batch?types=quote",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                //Log.d(TAG + "Search", (response.getJSONObject("quote")).getString("changePercent"));
                                searchInfo[1] = "Symbol: " + symbol;
                                Log.d(TAG + "Symbol", searchInfo[0]);
                                searchInfo[3] = "Today's change: " + String.format("%.3g%%", 100*Float.parseFloat(response.getJSONObject("quote").getString("changePercent")), "%");
                                Log.d(TAG + "change%", searchInfo[1]);
                                searchInfo[0] = "Company Name: " + response.getJSONObject("quote").getString("companyName");
                                Log.d(TAG + "companyName", searchInfo[2]);
                                searchInfo[2] = "Sector: " + response.getJSONObject("quote").getString("sector");
                                Log.d(TAG + "Sector", searchInfo[3]);
                                searchInfo[4] = "Today's High: $" + response.getJSONObject("quote").getString("high");
                                Log.d(TAG + "High", searchInfo[4]);
                                searchInfo[5] = "Today's Low: $" + response.getJSONObject("quote").getString("low");
                                Log.d(TAG + "Low", searchInfo[5]);
                                searchInfo[6] = "Latest Price: $" + response.getJSONObject("quote").getString("latestPrice");
                                Log.d(TAG + "latestPrice", searchInfo[6]);
                                searchInfo[7] = "52-Week High: $" + response.getJSONObject("quote").getString("week52High");
                                Log.d(TAG + "Week52High", searchInfo[7]);
                                searchInfo[8] = "52-Week Low: $" + response.getJSONObject("quote").getString("week52Low");
                                Log.d(TAG + "Week52Low", searchInfo[8]);
                                //searchChanges(data);
                                //Log.d(TAG, toReturn);
                            } catch (Exception e) {
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

    public void startAPICall(RequestQueue requestQueue, final String symbol, final int position, final String type) {
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
                                addChanges(response.getJSONObject("quote").getString("companyName") + "\n(" + symbol + "): " + String.format("%.3g%%", 100*Float.parseFloat(response.getJSONObject("quote").getString("changePercent")), "%"), position, type);
                                //addChanges(symbol + ": " + response.getJSONObject("quote").getString("changePercent") + "%", position, type);
                                //Log.d(TAG, toReturn);
                            } catch (Exception e) {
                                addChanges(symbol, position, type);
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
