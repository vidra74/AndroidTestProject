package com.example.korisnik.androidtestproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentFragment extends Fragment {
    private RecyclerView mTournamentRecyclerView;
    private BoardAdapter mBoardAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tournaments, container, false);

        mTournamentRecyclerView = (RecyclerView)v.findViewById(R.id.tournamet_recycler_view);
        mTournamentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private class TournamentHolder extends RecyclerView.ViewHolder{
        public TournamentHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_board, parent, false));
        }
    }

    private class BoardAdapter extends RecyclerView.Adapter<TournamentHolder>
    {
        private List<Board> mBoards;
        public BoardAdapter(List<Board> pBoards){
            mBoards = pBoards;
        }


        @Override
        public TournamentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater lLayoutInflater = LayoutInflater.from(getActivity());
            return new TournamentHolder(lLayoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TournamentHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mBoards.size();
        }
    }

    private void updateUI(){
        TournamentBoards lTournamentBoards = TournamentBoards.get(getActivity());
        List<Board> lBoards = lTournamentBoards.getTournamentBoards();

        mBoardAdapter = new BoardAdapter(lBoards);
        mTournamentRecyclerView.setAdapter(mBoardAdapter);

    }
}
