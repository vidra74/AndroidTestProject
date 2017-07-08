package com.example.korisnik.androidtestproject;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentBoards {
    private static TournamentBoards sTournament;
    private List<Board> mBoardList;
    private UUID mTournamentID;

    public static TournamentBoards get(Context pContext){
        if (sTournament == null){
            sTournament = new TournamentBoards(pContext);
        }
        return sTournament;
    }

    private TournamentBoards(Context pContext){
        mBoardList = new ArrayList<Board>();
        mTournamentID = UUID.randomUUID();
        for(int i = 0; i < 28; i++){
            Board lBoard = new Board(mTournamentID, 1);
            lBoard.setNS(true);
            lBoard.setContract("1NT");
            // Par broj 1 igra protiv ostalih parova po 4 borda, počevši od broja 2 do broja 8
            lBoard.setOppsPairId(8 - (i/4));
            mBoardList.add(lBoard);
        }
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
}
