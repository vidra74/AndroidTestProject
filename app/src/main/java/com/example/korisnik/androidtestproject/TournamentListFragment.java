package com.example.korisnik.androidtestproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 16.7.2017..
 */

public class TournamentListFragment extends Fragment {
    private static final String TAG = "TournamentListFragment";

    private RecyclerView mTournamentRecycleList;
    private List<Tournament> mItems = new ArrayList<>();
    private TournamentAdapater mTournamentAdapter;

    public static TournamentListFragment newInstance() {
        return new TournamentListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        new FetchItemsTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_device_info:

                startActivity(new Intent(getContext(), PlayerDeviceActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tournament, container, false);

        mTournamentRecycleList = (RecyclerView) v.findViewById(R.id.tournament_recycler_view);
        mTournamentRecycleList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {


        if (mTournamentAdapter == null) {
            mTournamentAdapter = new TournamentAdapater(mItems);
            mTournamentRecycleList.setAdapter(mTournamentAdapter);
        } else {
            mTournamentAdapter.setTournaments(mItems);
            mTournamentAdapter.notifyDataSetChanged();
        }
    }

    private class TournamentListHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        private TextView mTournamentDate;
        private TextView mTournamentTime;
        private ImageView mEditable;
        private Tournament mTournament;

        public TournamentListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_tournament, parent, false));

            itemView.setOnClickListener(this);

            mTournamentDate = (TextView)itemView.findViewById(R.id.tournament_date);
            mTournamentTime = (TextView)itemView.findViewById(R.id.tournament_time);
            mEditable = (ImageView)itemView.findViewById(R.id.isEditable);
        }

        public void bindTournamentItem(Tournament item) {

            mTournament = item;
            if (mTournament != null) {
                mTournamentDate.setText(mTournament.getDate());
                mTournamentTime.setText(mTournament.getTime());
                mEditable.setVisibility(mTournament.isEditable() ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public void onClick(View pView) {
            if (mTournament.getTurnirUUID() == null) {
                Toast newToast = Toast.makeText(getContext(), (mTournament.getDate() + " is not proper tournament"), Toast.LENGTH_SHORT);
                newToast.show();
            } else {
                // OPERACIJA POCETNI PROZOR
                Intent intent = TournamentFragment.newIntent(getContext(), mTournament.getTurnirUUID().toString());
                startActivity(intent);
            }
        }
    }

    private class TournamentAdapater extends RecyclerView.Adapter<TournamentListHolder> {

        private List<Tournament> mTournamentItems;

        public TournamentAdapater(List<Tournament> tournamentItems) {
            mTournamentItems = tournamentItems;
        }

        @Override
        public TournamentListHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater lLayoutInflater = LayoutInflater.from(getActivity());
            return new TournamentListFragment.TournamentListHolder(lLayoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(TournamentListHolder tournamentHolder, int position) {
            Tournament tournamentItem = mTournamentItems.get(position);
            tournamentHolder.bindTournamentItem(tournamentItem);
        }

        @Override
        public int getItemCount() {
            return mTournamentItems.size();
        }

        public void setTournaments(List<Tournament> pTournaments){
            mTournamentItems = pTournaments;
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Tournament>> {
        @Override
        protected List<Tournament> doInBackground(Void... params) {
            return new TournamentFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Tournament> items) {
            mItems = items;
            updateUI();
        }
    }
}
