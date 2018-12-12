package com.example.android.recyclerview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.android.common.activities.SampleActivityBase;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Tech extends SampleActivityBase {
    public static final String TAG = "TECH";
    public static String tester = "";
    private String[] changes;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        String[] techBundleWins = MainActivity.techBundleWins;
        String[] techBundleLosses = MainActivity.techBundleLosses;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        /*final String[] symbols = {"AAPL", "VNET", "EGHT", "ATVI"};
        final API api = new API();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);*/
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle test = new Bundle();
            Bundle testL = new Bundle();
            //Log.d(TAG, techBundle[1]);
            test.putStringArray("changes", techBundleWins);
            testL.putStringArray("changesD", techBundleLosses);
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            fragment.setArguments(test);
            RecyclerViewFragment fragment2 = new RecyclerViewFragment();
            fragment2.setArguments(testL);
            transaction.replace(R.id.tech_wins, fragment);
            transaction.replace(R.id.tech_losses, fragment2);
            transaction.commit();

        }
    }
}
