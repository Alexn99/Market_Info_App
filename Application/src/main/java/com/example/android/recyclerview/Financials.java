package com.example.android.recyclerview;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

import com.example.android.common.activities.SampleActivityBase;

public class Financials extends SampleActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] financialBundleWins = MainActivity.financialBundleWins;
        String[] financialBundleLosses = MainActivity.financialBundleLosses;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financials);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle test = new Bundle();
            Bundle testL = new Bundle();
            test.putStringArray("changes", financialBundleWins);
            testL.putStringArray("changesD", financialBundleLosses);
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            fragment.setArguments(test);
            RecyclerViewFragment fragment2 = new RecyclerViewFragment();
            fragment2.setArguments(testL);
            transaction.replace(R.id.financial_wins, fragment);
            transaction.replace(R.id.financial_losses, fragment2);
            transaction.commit();
        }
    }

}
