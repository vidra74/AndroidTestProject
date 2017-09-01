package com.example.korisnik.androidtestproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentFragment extends Fragment {
    private RecyclerView mTournamentRecyclerView;
    private BoardAdapter mBoardAdapter;
    private UUID mTournamentSelectedUUID;
    private static final String EXTRA_TOURNAMENT_UUID = "com.example.korisnik.androidtestproject.tournament_uuid";

    public static Intent newIntent(Context packageContext, String tournamentUUID){
        Intent intent = new Intent(packageContext, TournamentActivity.class);
        intent.putExtra(EXTRA_TOURNAMENT_UUID, tournamentUUID);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTournamentSelectedUUID = UUID.fromString(getActivity().getIntent().getStringExtra(EXTRA_TOURNAMENT_UUID));
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
                Board lBoard = new Board(TournamentBoards.get(getActivity(), mTournamentSelectedUUID).mTournamentID, 1);
                TournamentBoards.get(getActivity(), lBoard.getTournamentId()).addBoard(lBoard);
                Intent intent = BoardPagerActivity.newIntent(getActivity(), lBoard.getBoardId(), mTournamentSelectedUUID);
                startActivity(intent);
                return true;
            case R.id.sync_tournament:
                if (mBoardAdapter.getItemCount() == 0){
                    Toast.makeText(getContext(), "No boards for tournament UUID " + mTournamentSelectedUUID.toString(), Toast.LENGTH_SHORT).show();
                    new FetchBoardsTask().execute(mTournamentSelectedUUID.toString());
                } else {
                    Toast.makeText(getContext(), "Uploading boards for tournament UUID " + mTournamentSelectedUUID.toString(), Toast.LENGTH_SHORT).show();
                }
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
            mContract.setText(Integer.toString(mBoard.getNSResult()));
            if (mBoard.isBye()){
                mNS.setImageResource(R.drawable.ic_solved);
                mNS.setVisibility(View.VISIBLE);
            } else {
                mNS.setImageResource(android.R.drawable.ic_menu_compass);
                mNS.setVisibility(mBoard.isNS() ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public void onClick(View pView) {
            Intent intent = BoardPagerActivity.newIntent(getActivity(), mBoard.getBoardId(), mBoard.getTournamentId());
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
        TournamentBoards lTournamentBoards = TournamentBoards.get(getActivity(), mTournamentSelectedUUID);
        List<Board> lBoards = lTournamentBoards.getTournamentBoards();

        if (mBoardAdapter == null) {
            mBoardAdapter = new BoardAdapter(lBoards);
            mTournamentRecyclerView.setAdapter(mBoardAdapter);
        } else {
            mBoardAdapter.setBoards(lBoards);
            mBoardAdapter.notifyDataSetChanged();
        }

    }

    private class FetchBoardsTask extends AsyncTask<String,Void,List<Board>> {
        @Override
        protected List<Board> doInBackground(String... params) {
            return new TournamentFetcher().fetchBoards(params[0]);
        }

        @Override
        protected void onPostExecute(List<Board> items) {
            mBoardAdapter.setBoards(items);

            for (int i = 0; i < mBoardAdapter.getItemCount(); i++){
                TournamentBoards.get(getActivity(), mBoardAdapter.mBoards.get(i).getTournamentId()).addBoard(mBoardAdapter.mBoards.get(i));
            }

            mBoardAdapter.notifyDataSetChanged();
        }
    }
}
