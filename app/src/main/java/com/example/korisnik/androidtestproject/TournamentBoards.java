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
    private UUID mTournamentID;

    public static TournamentBoards get(Context pContext){
        if (sTournament == null){
            sTournament = new TournamentBoards(pContext);
        }
        return sTournament;
    }

    private TournamentBoards(Context pContext){
        mContext = pContext.getApplicationContext();
        mDatabase = new BridgeBoardsHelper(mContext).getWritableDatabase();
        mBoardList = new ArrayList<Board>();
        mTournamentID = UUID.randomUUID();
        for(int i = 0; i < 28; i++){
            Board lBoard = new Board(mTournamentID, 1);

            int nivo = (i % 7) + 1;
            String lContract = "" + nivo;
            nivo = (i+1) % 5;
            switch (nivo){
                case 0: lContract += "C";
                        break;
                case 1: lContract += "D";
                    break;
                case 2: lContract += "H";
                    break;
                case 3: lContract += "S";
                    break;
                case 4: lContract += "NT";
                    break;
                default: lContract += "C";
                    break;
            }
            lBoard.setContract(lContract);
            // Par broj 1 igra protiv ostalih parova po 4 borda, počevši od broja 2 do broja 8
            lBoard.setOppsPairId(8 - (i/4));
            lBoard.setNS(lBoard.getOppsPairId() % 2 == 0);
            lBoard.setTournamentBoardId(i+1);
            lBoard.setBoardId(UUID.randomUUID());
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

    public Board getBoard(UUID id){
        for (Board lBoard: mBoardList){
            if (lBoard.getBoardId().equals(id)){
                return lBoard;
            }
        }
        return null;
    }
}
