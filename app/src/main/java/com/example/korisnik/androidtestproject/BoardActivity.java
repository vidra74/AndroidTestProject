package com.example.korisnik.androidtestproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoardActivity extends SingleFragmentActivity {

    protected Fragment createFragment(){
        return new BoardFragment();
    }
}
