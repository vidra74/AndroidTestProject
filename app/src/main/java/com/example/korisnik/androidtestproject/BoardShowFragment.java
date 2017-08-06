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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID lTournamnentUUID = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_TOURNEY_UUID);
        UUID lUUID = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_BOARD_UUID);
        mBoard = TournamentBoards.get(getActivity(), lTournamnentUUID).getBoard(lUUID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cards, container, false);
        mBoardDealer = (TextView)v.findViewById(R.id.tvBoard);
        mBoardZone = (TextView)v.findViewById(R.id.tvZone);

        if (mBoard != null) {
            mBoardDealer.setText(Integer.toString(mBoard.getTournamentBoardId()));
            mBoardZone.setText("NS");
        }

        return v;
    }
}
