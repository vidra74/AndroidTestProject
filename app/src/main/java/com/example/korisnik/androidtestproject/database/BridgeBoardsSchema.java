package com.example.korisnik.androidtestproject.database;

/**
 * Created by Korisnik on 10.7.2017..
 */

public class BridgeBoardsSchema {
    public static final class BoardsTable {
        public static final String NAME = "boards";

        public static final class Cols {
            public static final String BUUID        = "board_uuid";
            public static final String TUUID        = "tournament_uuid";
            public static final String PAIRID       = "pair_no";
            public static final String OPPSPAIRID   = "opps_pair_no";
            public static final String ISNS         = "is_ns";
            public static final String CONTRACT     = "contract";
            public static final String BOARDNO      = "board_t_no";

            //            private String mLead;
            //            private char mDeclarer;
            //            private int mDeclarerTricksToContract;
            //            private int mNSResult;
        }
    }
}
