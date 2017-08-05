package com.example.korisnik.androidtestproject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Korisnik on 5.8.2017..
 */

public class BoardShowActivity extends SingleFragmentActivity {

    private static final String EXTRA_BOARD_UUID = "com.example.korisnik.androidtestproject.board_uuid";
    private static final String EXTRA_TOURNEY_UUID = "com.example.korisnik.androidtestproject.tourney_uuid";

    protected Fragment createFragment(){
        return new BoardShowFragment();
    }

    public static Intent newIntent(Context packageContext, UUID pBoardUUID, UUID pTourneyUUID){
        Intent intent = new Intent(packageContext, BoardShowActivity.class);
        intent.putExtra(EXTRA_BOARD_UUID, pBoardUUID);
        intent.putExtra(EXTRA_TOURNEY_UUID, pTourneyUUID);
        return intent;
    }
}
