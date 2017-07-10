package com.example.korisnik.androidtestproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.korisnik.androidtestproject.Board;
import com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable;

public class BridgeBoardsHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "bridgeBoards.db";

    public BridgeBoardsHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BoardsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BoardsTable.Cols.BUUID + ", " +
                BoardsTable.Cols.TUUID + ", " +
                BoardsTable.Cols.PAIRID + ", " +
                BoardsTable.Cols.OPPSPAIRID + ", " +
                BoardsTable.Cols.ISNS  + ", " +
                BoardsTable.Cols.CONTRACT  + ", " +
                BoardsTable.Cols.BOARDNO + ", " +
                BoardsTable.Cols.DECLARER + ", " +
                BoardsTable.Cols.DECTRICKS + ", " +
                BoardsTable.Cols.LEAD + ", " +
                BoardsTable.Cols.NSRESULT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + BoardsTable.NAME + "ADD COLUMN " + BoardsTable.Cols.DECLARER + " string");
            db.execSQL("ALTER TABLE " + BoardsTable.NAME + "ADD COLUMN " + BoardsTable.Cols.LEAD + " string");
            db.execSQL("ALTER TABLE " + BoardsTable.NAME + "ADD COLUMN " + BoardsTable.Cols.DECTRICKS + " integer");
            db.execSQL("ALTER TABLE " + BoardsTable.NAME + "ADD COLUMN " + BoardsTable.Cols.NSRESULT + " integer");
        }
    }
}

