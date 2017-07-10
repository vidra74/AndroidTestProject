package com.example.korisnik.androidtestproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

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
    private EditText mOppsPairs;
    private EditText mDeclarer;
    private EditText mDeclarerTricks;
    private EditText mNSResult;
    private EditText mLead;
    private CheckBox mIsNS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID lUUID = (UUID)getArguments().getSerializable(ARG_BOARD_UUID);
        mBoard = TournamentBoards.get(getActivity()).getBoard(lUUID);
        setHasOptionsMenu(true);
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

        mOppsPairs = (EditText) v.findViewById(R.id.edtOppsPairs);
        mOppsPairs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setOppsPairId(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        mDeclarerTricks = (EditText) v.findViewById(R.id.edtDeclarerTricks);
        mDeclarerTricks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setDeclarerTricksToContract(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        mNSResult = (EditText) v.findViewById(R.id.edtNSResult);
        mNSResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setNSResult(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        mDeclarer = (EditText) v.findViewById(R.id.edtDeclarer);
        mDeclarer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setDeclarer(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        mLead = (EditText) v.findViewById(R.id.edtLead);
        mLead.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoard.setLead(s.toString());
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
            mOppsPairs.setText(Integer.toString(mBoard.getOppsPairId()));
            mDeclarerTricks.setText(Integer.toString(mBoard.getDeclarerTricksToContract()));
            mNSResult.setText(Integer.toString(mBoard.getNSResult()));
            mDeclarer.setText(mBoard.getDeclarer());
            mLead.setText(mBoard.getLead());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.board_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_board:
                Toast toast = Toast.makeText(getContext(), "Board deleted", Toast.LENGTH_SHORT);
                toast.show();
                TournamentBoards.get(getActivity()).deleteBoard(mBoard.getBoardId());
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // update when fragment is done
        TournamentBoards.get(getActivity()).updateBoard(mBoard);
    }
}
