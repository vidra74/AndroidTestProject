package com.example.korisnik.androidtestproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Korisnik on 9.7.2017..
 */

public class BoardPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Board> mBoards;

    private static final String EXTRA_BOARD_UUID = "com.example.korisnik.androidtestproject.board_uuid";
    private static final String EXTRA_TOURNEY_UUID = "com.example.korisnik.androidtestproject.tourney_uuid";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_pager);

        UUID lBoardUUID = (UUID)getIntent().getSerializableExtra(EXTRA_BOARD_UUID);
        UUID lTourneyUUID = (UUID)getIntent().getSerializableExtra(EXTRA_TOURNEY_UUID);
        mViewPager = (ViewPager)findViewById(R.id.board_view_pager);

        mBoards = TournamentBoards.get(this, lTourneyUUID).getTournamentBoards();
        FragmentManager lFM = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(lFM) {
            @Override
            public Fragment getItem(int position) {
                Board lBoard = mBoards.get(position);
                return BoardFragment.newInstance(lBoard.getBoardId(), lBoard.getTournamentId());
            }

            @Override
            public int getCount() {
                return mBoards.size();
            }
        });



        for(int i = 0; i < mBoards.size(); i++){
            if(mBoards.get(i).getBoardId().equals(lBoardUUID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID pBoardUUID, UUID pTourneyUUID){
        Intent intent = new Intent(packageContext, BoardPagerActivity.class);
        intent.putExtra(EXTRA_BOARD_UUID, pBoardUUID);
        intent.putExtra(EXTRA_TOURNEY_UUID, pTourneyUUID);
        return intent;
    }
}
