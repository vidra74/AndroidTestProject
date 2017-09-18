package com.example.korisnik.androidtestproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.korisnik.androidtestproject.R;

import java.util.UUID;

/**
 * Created by Korisnik on 5.8.2017..
 */

public class BoardShowFragment extends Fragment {
    private static final String EXTRA_BOARD_UUID = "com.example.korisnik.androidtestproject.board_uuid";
    private static final String EXTRA_TOURNEY_UUID = "com.example.korisnik.androidtestproject.tourney_uuid";
    private Board mBoard;
    private TextView mBoardDealer;
    private TextView mBoardZone;
    private TextView mNS;
    private TextView mNH;
    private TextView mND;
    private TextView mNC;
    private TextView mES;
    private TextView mEH;
    private TextView mED;
    private TextView mEC;
    private TextView mSS;
    private TextView mSH;
    private TextView mSD;
    private TextView mSC;
    private TextView mWS;
    private TextView mWH;
    private TextView mWD;
    private TextView mWC;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID lTournamnentUUID = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_TOURNEY_UUID);
        UUID lUUID = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_BOARD_UUID);
        mBoard = TournamentBoards.get(getActivity(), lTournamnentUUID).getBoard(lUUID);
        mBoard.setDLM("bidaglpieabbpngfdlnkggclon");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cards, container, false);
        mBoardDealer = (TextView)v.findViewById(R.id.tvBoard);
        mBoardZone = (TextView)v.findViewById(R.id.tvZone);

        mNS = (TextView)v.findViewById(R.id.tvNS);
        mNH = (TextView)v.findViewById(R.id.tvNH);
        mND = (TextView)v.findViewById(R.id.tvND);
        mNC = (TextView)v.findViewById(R.id.tvNC);
        mES = (TextView)v.findViewById(R.id.tvES);
        mEH = (TextView)v.findViewById(R.id.tvEH);
        mED = (TextView)v.findViewById(R.id.tvED);
        mEC = (TextView)v.findViewById(R.id.tvEC);
        mSS = (TextView)v.findViewById(R.id.tvSS);
        mSH = (TextView)v.findViewById(R.id.tvSH);
        mSD = (TextView)v.findViewById(R.id.tvSD);
        mSC = (TextView)v.findViewById(R.id.tvSC);
        mWS = (TextView)v.findViewById(R.id.tvWS);
        mWH = (TextView)v.findViewById(R.id.tvWH);
        mWD = (TextView)v.findViewById(R.id.tvWD);
        mWC = (TextView)v.findViewById(R.id.tvWC);

        if (mBoard != null) {
            mBoardDealer.setText(Integer.toString(mBoard.getTournamentBoardId()));
            mBoardZone.setText("NS");
            mNS.setText(mBoard.getCards("N", "S"));
            mNH.setText(mBoard.getCards("N", "H"));
            mND.setText(mBoard.getCards("N", "D"));
            mNC.setText(mBoard.getCards("N", "C"));
            mES.setText(mBoard.getCards("E", "S"));
            mEH.setText(mBoard.getCards("E", "H"));
            mED.setText(mBoard.getCards("E", "D"));
            mEC.setText(mBoard.getCards("E", "C"));
            mSS.setText(mBoard.getCards("S", "S"));
            mSH.setText(mBoard.getCards("S", "H"));
            mSD.setText(mBoard.getCards("S", "D"));
            mSC.setText(mBoard.getCards("S", "C"));
            mWS.setText(mBoard.getCards("W", "S"));
            mWH.setText(mBoard.getCards("W", "H"));
            mWD.setText(mBoard.getCards("W", "D"));
            mWC.setText(mBoard.getCards("W", "C"));
        }

        return v;
    }
}
