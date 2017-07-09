package com.example.korisnik.androidtestproject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class BoardActivity extends SingleFragmentActivity {

    public static String EXTRA_BOARD_NO = "com.example.korisnik.androidtestproject.board_no";
    public static String EXTRA_BOARD_UUID = "com.example.korisnik.androidtestproject.board_uuid";

    public static Intent newIntent(Context packageContext, UUID pBoardUUID){
        Intent intent = new Intent(packageContext, BoardActivity.class);
        intent.putExtra(EXTRA_BOARD_UUID, pBoardUUID);
        return intent;
    }

    protected Fragment createFragment(){
        UUID lBoardUUID = (UUID)getIntent().getSerializableExtra(EXTRA_BOARD_UUID);
        return BoardFragment.newInstance(lBoardUUID);
    }
}
