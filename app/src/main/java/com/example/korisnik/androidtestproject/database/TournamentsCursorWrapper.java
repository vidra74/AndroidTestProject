package com.example.korisnik.androidtestproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.korisnik.androidtestproject.Tournament;

/**
 * Created by Frano on 17.07.2017.
 */

public class TournamentsCursorWrapper extends CursorWrapper {
    public TournamentsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Tournament getTournament() {
        int id = getInt(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.ID));
        String tuuidString = getString(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.TUUID));
        int scoring_no = getInt(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.SCORING));
        int club_no = getInt(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.CLUB));
        String dateString = getString(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.DATE));
        String timeString = getString(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.TIME));
        int board_count = getInt(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.BOARDNUMBER));
        int status = getInt(getColumnIndex(BridgeBoardsSchema.Tournaments.Cols.STATUS));

        // int pID, String pDate, String pTime, int pKlubId, int pTipObracun, int pBrojBordova, int pStatus, String pTurnirUUID
        Tournament lTourney = new Tournament(id, dateString, timeString, club_no, scoring_no, board_count, status, tuuidString);
        return lTourney;
    }
}
