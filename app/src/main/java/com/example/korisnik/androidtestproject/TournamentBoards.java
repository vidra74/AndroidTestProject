package com.example.korisnik.androidtestproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.korisnik.androidtestproject.database.BridgeBoardsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentBoards {
    private static TournamentBoards sTournament;
    private List<Board> mBoardList;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public UUID mTournamentID;

    public static TournamentBoards get(Context pContext){
        if (sTournament == null){
            sTournament = new TournamentBoards(pContext);
        }
        return sTournament;
    }

    public void addBoard(Board pBoard){
        if (pBoard.getBoardId() == null){
            pBoard.setBoardId(UUID.randomUUID());
        }
        mBoardList.add(pBoard);
    }

    private TournamentBoards(Context pContext){
        mContext = pContext.getApplicationContext();
        mDatabase = new BridgeBoardsHelper(mContext).getWritableDatabase();
        mBoardList = new ArrayList<Board>();
        mTournamentID = UUID.randomUUID();
    }

    public List<Board> getTournamentBoards(){
        return mBoardList;
    }

    public Board getBoard(int id){
        for (Board lBoard: mBoardList){
            if (lBoard.getTournamentBoardId() == id){
                return lBoard;
            }
        }
        return null;
    }

    public Board getBoard(UUID id){
        for (Board lBoard: mBoardList){
            if (lBoard.getBoardId().equals(id)){
                return lBoard;
            }
        }
        return null;
    }
}
