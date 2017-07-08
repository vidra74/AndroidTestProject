package com.example.korisnik.androidtestproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Korisnik on 8.7.2017..
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment lFragment = fm.findFragmentById(R.id.fragment_container);

        if (lFragment == null){
            lFragment = new BoardFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, lFragment)
                    .commit();
        }
    }
}
