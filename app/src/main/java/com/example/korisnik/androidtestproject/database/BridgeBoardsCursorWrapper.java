package com.example.korisnik.androidtestproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.korisnik.androidtestproject.Board;

import java.util.UUID;

/**
 * Created by Korisnik on 10.7.2017..
 */

public class BridgeBoardsCursorWrapper extends CursorWrapper{
    public BridgeBoardsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Board getBoard() {
        String buuidString = getString(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.BUUID));
        String tuuidString = getString(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.TUUID));
        int pair_no = getInt(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.PAIRID));
        int opps_pair_no = getInt(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.OPPSPAIRID));
        boolean is_ns = (getInt(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.ISNS)) == 1);
        String contract = getString(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.CONTRACT));
        int board_t_no = getInt(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.BOARDNO));
        String declarer = getString(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.DECLARER));
        String lead = getString(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.LEAD));
        int dec_tricks = getInt(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.DECTRICKS));
        int resultNS = getInt(getColumnIndex(BridgeBoardsSchema.BoardsTable.Cols.NSRESULT));

        Board lBoard = new Board(UUID.fromString(tuuidString), pair_no);
        lBoard.setBoardId(UUID.fromString(buuidString));
        lBoard.setOppsPairId(opps_pair_no);
        lBoard.setNS(is_ns);
        lBoard.setContract(contract);
        lBoard.setTournamentBoardId(board_t_no);
        lBoard.setDeclarer(declarer);
        lBoard.setDeclarerTricksToContract(dec_tricks);
        lBoard.setLead(lead);
        lBoard.setNSResult(resultNS);

        return lBoard;
    }
}
