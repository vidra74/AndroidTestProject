package com.example.korisnik.androidtestproject;

import android.support.v4.app.Fragment;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentActivity extends SingleFragmentActivity {
    protected Fragment createFragment(){
        return new TournamentFragment();
    }
}
