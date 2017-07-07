package com.example.korisnik.androidtestproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

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
