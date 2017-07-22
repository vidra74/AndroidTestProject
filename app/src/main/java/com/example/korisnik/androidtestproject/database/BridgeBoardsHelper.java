package com.example.korisnik.androidtestproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.BoardsTable;

public class BridgeBoardsHelper extends SQLiteOpenHelper {
    private static final int VERSION = 4;
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
                BoardsTable.Cols.ISBYE  + ", " +
                BoardsTable.Cols.NSRESULT +
                ")"
        );

        db.execSQL("create table " + BridgeBoardsSchema.Tournaments.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BridgeBoardsSchema.Tournaments.Cols.ID + ", " +
                BridgeBoardsSchema.Tournaments.Cols.DATE + ", " +
                BridgeBoardsSchema.Tournaments.Cols.TIME + ", " +
                BridgeBoardsSchema.Tournaments.Cols.CLUB + ", " +
                BridgeBoardsSchema.Tournaments.Cols.SCORING  + ", " +
                BridgeBoardsSchema.Tournaments.Cols.BOARDNUMBER  + ", " +
                BridgeBoardsSchema.Tournaments.Cols.STATUS + ", " +
                BridgeBoardsSchema.Tournaments.Cols.TUUID +
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

        if (oldVersion < 3) {
            db.execSQL("create table " + BridgeBoardsSchema.Tournaments.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    BridgeBoardsSchema.Tournaments.Cols.ID + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.DATE + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.TIME + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.CLUB + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.SCORING  + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.BOARDNUMBER  + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.STATUS + ", " +
                    BridgeBoardsSchema.Tournaments.Cols.TUUID +
                    ")"
            );
        }

        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE " + BoardsTable.NAME + "ADD COLUMN " + BoardsTable.Cols.ISBYE + " integer");
        }

    }
}

