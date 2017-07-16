package com.example.korisnik.androidtestproject;

import android.support.v4.app.Fragment;

/**
 * Created by Korisnik on 15.7.2017..
 */

public class TournamentListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return TournamentListFragment.newInstance();
    }
}
