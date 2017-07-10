package com.example.korisnik.androidtestproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.korisnik.androidtestproject.database.BridgeBoardsCursorWrapper;
import com.example.korisnik.androidtestproject.database.BridgeBoardsHelper;
import com.example.korisnik.androidtestproject.database.BridgeBoardsSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.BUUID;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.TUUID;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.PAIRID;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.OPPSPAIRID;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.ISNS;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.CONTRACT;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable.Cols.BOARDNO;

/**
 * Created by Korisnik on 8.7.2017..
 */

public class TournamentBoards {
    private static TournamentBoards sTournament;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static UUID mTournamentID;
    private static int mNumberOfBoards;

    public static TournamentBoards get(Context pContext){
        if (sTournament == null){
            sTournament = new TournamentBoards(pContext);
        }
        return sTournament;
    }

    private BridgeBoardsCursorWrapper queryBoards(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BridgeBoardsSchema.BoardsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new BridgeBoardsCursorWrapper(cursor);
    }


    public void addBoard(Board pBoard){
        if (pBoard.getBoardId() == null){
            pBoard.setBoardId(UUID.randomUUID());
        }
        if (pBoard.getTournamentId() == null){
            pBoard.setTournamentId(mTournamentID);
        }
        if (pBoard.getTournamentBoardId() == 0){
            BridgeBoardsCursorWrapper cursor = queryBoards(null, null);
            mNumberOfBoards = cursor.getCount() + 1;
            cursor.close();
            pBoard.setTournamentBoardId(mNumberOfBoards);
        }
        ContentValues values = getContentValues(pBoard);
        mDatabase.insert(BridgeBoardsSchema.BoardsTable.NAME, null, values);
    }

    public void updateBoard(Board pBoard){
        if (pBoard.getBoardId() == null){
            pBoard.setBoardId(UUID.randomUUID());
        }
        String uuidString = pBoard.getBoardId().toString();
        ContentValues values = getContentValues(pBoard);

        mDatabase.update(BridgeBoardsSchema.BoardsTable.NAME,
                            values,
                            BridgeBoardsSchema.BoardsTable.Cols.BUUID + " = ?",
                            new String[] {uuidString});
    }

    public void deleteBoard(UUID pBoardUUID){
        if (pBoardUUID == null){
            return;
        }
        mDatabase.delete(BridgeBoardsSchema.BoardsTable.NAME,
                            BridgeBoardsSchema.BoardsTable.Cols.BUUID + " = ?",
                            new String[] {pBoardUUID.toString()});
    }

    private TournamentBoards(Context pContext){
        mContext = pContext.getApplicationContext();
        mDatabase = new BridgeBoardsHelper(mContext).getWritableDatabase();
        mTournamentID = UUID.randomUUID();
    }

    public List<Board> getTournamentBoards(){
        List<Board> lBoards = new ArrayList<>();
        BridgeBoardsCursorWrapper cursor = queryBoards(null, null);

        try{
            mNumberOfBoards = cursor.getCount();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                lBoards.add(cursor.getBoard());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return lBoards;
    }

    public Board getBoard(int pId){
        BridgeBoardsCursorWrapper cursor = queryBoards(BridgeBoardsSchema.BoardsTable.Cols.BOARDNO + " = ?", new String[]{Integer.toString(pId)});

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBoard();
        } finally {
            cursor.close();
        }
    }

    public Board getBoard(UUID pUUId){
        BridgeBoardsCursorWrapper cursor = queryBoards(BridgeBoardsSchema.BoardsTable.Cols.BUUID + " = ?", new String[]{pUUId.toString()});

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBoard();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Board pBoard) {
        ContentValues values = new ContentValues();
        values.put(BUUID, pBoard.getBoardId().toString());
        values.put(TUUID, pBoard.getTournamentId().toString());
        values.put(PAIRID, pBoard.getPairId());
        values.put(OPPSPAIRID, pBoard.getOppsPairId());
        values.put(ISNS, pBoard.isNS());
        values.put(CONTRACT, pBoard.getContract());
        values.put(BOARDNO, pBoard.getTournamentBoardId());

        return values;
    }
}
