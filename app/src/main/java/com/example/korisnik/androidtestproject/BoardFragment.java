package com.example.korisnik.androidtestproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

import static android.widget.CompoundButton.*;

/**
 * Created by Korisnik on 7.7.2017..
 */

public class BoardFragment extends Fragment{
    private static final String ARG_BOARD_UUID = "board_uuid";

    private Board mBoard;
    private EditText mBoardNo;
    private EditText mContract;
    private CheckBox mIsNS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID lUUID = (UUID)getArguments().getSerializable(ARG_BOARD_UUID);
        mBoard = TournamentBoards.get(getActivity()).getBoard(lUUID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        mBoardNo = (EditText) v.findViewById(R.id.etBoardNo);
        mBoardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setTournamentBoardId(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        mContract = (EditText) v.findViewById(R.id.edtTextContract);

        mContract.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setContract(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        mIsNS = (CheckBox) v.findViewById(R.id.checkedNS);

        mIsNS.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBoard.setNS(isChecked);
            }
        });

        if (mBoard != null) {
            mBoardNo.setText(Integer.toString(mBoard.getTournamentBoardId()));
            mIsNS.setChecked(mBoard.isNS());
            mContract.setText(mBoard.getContract());
        }

        return v;
    }

    public static BoardFragment newInstance(UUID pBoardUUID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOARD_UUID, pBoardUUID);

        BoardFragment lBoardFragment = new BoardFragment();
        lBoardFragment.setArguments(args);
        return lBoardFragment;
    }
}
