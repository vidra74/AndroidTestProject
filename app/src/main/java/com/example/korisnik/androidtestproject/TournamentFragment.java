package com.example.korisnik.androidtestproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentFragment extends Fragment {
    private RecyclerView mTournamentRecyclerView;
    private BoardAdapter mBoardAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tournament_boards, container, false);

        mTournamentRecyclerView = (RecyclerView)v.findViewById(R.id.tournamet_recycler_view);
        mTournamentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tournament_boards_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_board:
                Board lBoard = new Board(TournamentBoards.get(getActivity()).mTournamentID, 1);
                TournamentBoards.get(getActivity()).addBoard(lBoard);
                Intent intent = BoardPagerActivity.newIntent(getActivity(), lBoard.getBoardId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TournamentHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        private TextView mBoardNo;
        private TextView mContract;
        private ImageView mNS;
        private Board mBoard;

        public TournamentHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_board, parent, false));

            itemView.setOnClickListener(this);

            mBoardNo = (TextView)itemView.findViewById(R.id.board_number);
            mContract = (TextView)itemView.findViewById(R.id.board_contract);
            mNS = (ImageView)itemView.findViewById(R.id.isNSPair);
        }

        public void bind(Board pBoard){
            mBoard = pBoard;
            // ako uđe int onda Andorid traži R.id sa intom i to puca pa zato int u setText treba castati
            mBoardNo.setText(Integer.toString(mBoard.getTournamentBoardId()));
            mContract.setText(mBoard.getContract());
            mNS.setVisibility(mBoard.isNS() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View pView) {
            Intent intent = BoardPagerActivity.newIntent(getActivity(), mBoard.getBoardId());
            startActivity(intent);
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
            Board lBoard = mBoards.get(position);
            holder.bind(lBoard);
        }

        @Override
        public int getItemCount() {
            return mBoards.size();
        }

        public void setBoards(List<Board> pBoards){
            mBoards = pBoards;
        }
    }

    private void updateUI(){
        TournamentBoards lTournamentBoards = TournamentBoards.get(getActivity());
        List<Board> lBoards = lTournamentBoards.getTournamentBoards();

        if (mBoardAdapter == null) {
            mBoardAdapter = new BoardAdapter(lBoards);
            mTournamentRecyclerView.setAdapter(mBoardAdapter);
        } else {
            mBoardAdapter.setBoards(lBoards);
            mBoardAdapter.notifyDataSetChanged();
        }

    }
}
