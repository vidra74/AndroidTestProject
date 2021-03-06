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
            public static final String LEAD         = "lead";
            public static final String DECLARER     = "declarer";
            public static final String DECTRICKS    = "declarer_tricks";
            public static final String NSRESULT     = "ns_result";
            public static final String ISBYE        = "is_bye";
        }
    }

    public static final class Tournaments {
        public static final String NAME = "tournaments";

        public static final class Cols {
            public static final String ID           = "id";
            public static final String TUUID        = "tournament_uuid";
            public static final String DATE         = "pair_no";
            public static final String TIME         = "opps_pair_no";
            public static final String CLUB         = "is_ns";
            public static final String SCORING      = "contract";
            public static final String BOARDNUMBER  = "board_t_no";
            public static final String STATUS       = "lead";
        }
    }

    public static final class PlayerDevice {
        public static final String NAME = "igraci";

        public static final class Cols{
            public static final String ID           = "id";
            public static final String IME          = "ime";
            public static final String PREZIME      = "prezime";
            public static final String MAIL         = "mail";
            public static final String MOBITEL      = "mobitel";
            public static final String KLUB         = "klub";
            public static final String AKTIVAN      = "aktivan";
            public static final String UUID         = "uuid";
        }
    }
}
