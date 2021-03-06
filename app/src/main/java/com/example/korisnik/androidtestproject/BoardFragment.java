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
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import static android.widget.CompoundButton.*;

/**
 * Created by Korisnik on 7.7.2017..
 */

public class BoardFragment extends Fragment{
    private static final String ARG_BOARD_UUID = "board_uuid";
    private static final String ARG_TOURNEY_UUID = "tourney_uuid";

    private Board mBoard;
    private EditText mBoardNo;
    private EditText mContract;
    private EditText mOppsPairs;
    private EditText mDeclarer;
    private EditText mDeclarerTricks;
    private EditText mNSResult;
    private EditText mLead;
    private CheckBox mIsNS;
    private CheckBox mIsBye;
    private TextView mTournamentUUID;
    private TextView mBoardUUID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID lTournamnentUUID = (UUID)getArguments().getSerializable(ARG_TOURNEY_UUID);
        UUID lUUID = (UUID)getArguments().getSerializable(ARG_BOARD_UUID);
        mBoard = TournamentBoards.get(getActivity(), lTournamnentUUID).getBoard(lUUID);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board2, container, false);

        mBoardNo = (EditText) v.findViewById(R.id.etBoardNo);
        mBoardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mBoard.setTournamentBoardId(tryStrToInt(s.toString()));
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
                mBoard.setContract(s.toString().toUpperCase());
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
                mBoard.setOppsPairId(tryStrToInt(s.toString()));
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
                mBoard.setDeclarerTricksToContract(tryStrToInt(s.toString()));
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
                mBoard.setNSResult(tryStrToInt(s.toString()));
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
                mBoard.setDeclarer(s.toString().toUpperCase());
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
                mBoard.setLead(s.toString().toUpperCase());
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

        mIsBye = (CheckBox) v.findViewById(R.id.checkedBye);

        mIsBye.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBoard.setBye(isChecked);
            }
        });

        mTournamentUUID = (TextView)v.findViewById(R.id.tvTournamentUUID);
        mBoardUUID = (TextView)v.findViewById(R.id.tvBoardUUID);

        if (mBoard != null) {
            mBoardNo.setText(Integer.toString(mBoard.getTournamentBoardId()));
            mIsNS.setChecked(mBoard.isNS());
            mContract.setText(mBoard.getContract());
            mOppsPairs.setText(Integer.toString(mBoard.getOppsPairId()));
            mDeclarerTricks.setText(Integer.toString(mBoard.getDeclarerTricksToContract()));
            mNSResult.setText(Integer.toString(mBoard.getNSResult()));
            mDeclarer.setText(mBoard.getDeclarer());
            mLead.setText(mBoard.getLead());
            mTournamentUUID.setText(mBoard.getTournamentId().toString());
            mBoardUUID.setText(mBoard.getBoardId().toString());
            mIsBye.setChecked(mBoard.isBye());
        }

        return v;
    }

    private int tryStrToInt(String pNumber){
        if ((pNumber == "") || (pNumber == "-")){
            return 0;
        }
        int lNumber;
        try{
            lNumber = Integer.parseInt(pNumber);
        } catch(Exception e1) {
            lNumber = 0;
        }
        return lNumber;

    }

    public static BoardFragment newInstance(UUID pBoardUUID, UUID pTourneyUUID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOARD_UUID, pBoardUUID);
        args.putSerializable(ARG_TOURNEY_UUID, pTourneyUUID);
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
                TournamentBoards.get(getActivity(), mBoard.getTournamentId()).deleteBoard(mBoard.getBoardId());
                getActivity().finish();
                return true;
            case R.id.show_board:
                Toast toastShow = Toast.makeText(getContext(), "Board showed", Toast.LENGTH_SHORT);
                toastShow.show();
                Intent intent = BoardShowActivity.newIntent(getActivity(), mBoard.getBoardId(), mBoard.getTournamentId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // update when fragment is done
        TournamentBoards.get(getActivity(), mBoard.getTournamentId()).updateBoard(mBoard);
    }
}
