package com.example.android.recyclerview;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

import com.example.android.common.activities.SampleActivityBase;

public class Health extends SampleActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] healthBundleWins = MainActivity.healthBundleWins;
        String[] healthBundleLosses = MainActivity.healthBundleLosses;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle test = new Bundle();
            Bundle testL = new Bundle();
            test.putStringArray("changes", healthBundleWins);
            testL.putStringArray("changesD", healthBundleLosses);
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            fragment.setArguments(test);
            RecyclerViewFragment fragment2 = new RecyclerViewFragment();
            fragment2.setArguments(testL);
            transaction.replace(R.id.health_wins, fragment);
            transaction.replace(R.id.health_losses, fragment2);
            transaction.commit();
        }
    }

}
