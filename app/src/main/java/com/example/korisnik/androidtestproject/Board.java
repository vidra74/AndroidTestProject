package com.example.korisnik.androidtestproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Math.abs;

/**
 * Created by Korisnik on 7.7.2017..
 */

public class Board {
    private UUID mBoardId;
    private UUID mTournamentId;
    private int mOppsPairId;
    private int mTournamentBoardId;
    private int mPairId;
    private boolean mNS;
    private String mLead;
    private String mDeclarer;
    private String mContract;
    private int mDeclarerTricksToContract;
    private int mNSResult;
    private String mDLM;
    private Map<String, ArrayList<Integer>> mRuka;

    public boolean isBye() {
        return mBye;
    }

    public void setBye(boolean pBye) {
        mBye = pBye;
    }

    private boolean mBye;

    public UUID getTournamentId() {
        return mTournamentId;
    }

    public void setTournamentId(UUID pTournamentId) {
        mTournamentId = pTournamentId;
    }

    public int getPairId() {
        return mPairId;
    }

    public void setPairId(int pPairId) {
        mPairId = pPairId;
    }

    public UUID getBoardId() {
        return mBoardId;
    }

    public void setBoardId(UUID pBoardId) {
        mBoardId = pBoardId;
    }

    public int getOppsPairId() {
        return mOppsPairId;
    }

    public void setOppsPairId(int pOppsPairId) {
        mOppsPairId = pOppsPairId;
    }

    public boolean isNS() {
        return mNS;
    }

    public void setNS(boolean pNS) {
        mNS = pNS;
    }

    public String getLead() {
        return mLead;
    }

    public void setLead(String pLead) {
        mLead = pLead;
    }

    public String getDLM() {
        return mDLM;
    }

    public void setDLM(String pLead) {
        mDLM = pLead;
        if (mRuka != null) {
            mRuka.clear();
        }
        if (mDLM == ""){
            return;
        }

        int nb, prvi, drugi, prvakarta, drugakarta;
        String ch;

        mRuka = new HashMap();

        mRuka.put("N", new ArrayList());
        mRuka.put("E", new ArrayList());
        mRuka.put("S", new ArrayList());
        mRuka.put("W", new ArrayList());

        for (int I = 0; I < 26; I++)
        {
            nb = (int)(mDLM.charAt(I)) - 97;

            if (nb > 15)
            {
                if (nb < 21)
                {
                    prvi  = (nb - 1) % 5;
                    drugi = 4;
                } else {
                    prvi  = 4;
                    drugi = (nb - 1) % 5;
                }
            } else {

                prvi  = nb / 4;
                drugi = nb % 4;
            }

            prvakarta  = (I * 2) - 2;
            drugakarta = (I * 2) - 1;
            ch = getIgrac(prvi);
            mRuka.get(ch).add(prvakarta);
            ch = getIgrac(drugi);
            mRuka.get(ch).add(drugakarta);

        }
    }

    public String getDeclarer() {
        return mDeclarer;
    }

    public void setDeclarer(String pDeclarer) {
        boolean bPromjena = (mDeclarer !=  pDeclarer);
        mDeclarer = pDeclarer;
        if ((mContract != null) && (mDeclarer.length() == 1) && (bPromjena)){
            this.setNSResult(bridgeResult());
        }
    }

    public String getContract() {
        return mContract;
    }

    public void setContract(String pContract) {
        boolean bPromjena = (mContract !=  pContract);
        mContract = pContract;
        if ((mContract != null) && (mDeclarer.length() == 1) && (bPromjena)){
            this.setNSResult(bridgeResult());
        }
    }

    public int getDeclarerTricksToContract() {
        return mDeclarerTricksToContract;
    }

    public void setDeclarerTricksToContract(int pDeclarerTricksToContract) {
        boolean bPromjena = (mDeclarerTricksToContract !=  pDeclarerTricksToContract);
        mDeclarerTricksToContract = pDeclarerTricksToContract;
        if ((mContract != null) && (mDeclarer.length() == 1) && (bPromjena)){
            this.setNSResult(bridgeResult());
        }
    }

    public int getNSResult() {
        return mNSResult;
    }

    public void setNSResult(int pNSResult) {
        mNSResult = pNSResult;
    }

    public int getTournamentBoardId() {
        return mTournamentBoardId;
    }

    public void setTournamentBoardId(int pTournamentBoardId) {
        mTournamentBoardId = pTournamentBoardId;
    }

    public Board(UUID pTournament, int pPairId){
        this.mTournamentId = pTournament;
        this.mPairId = pPairId;
        this.mBoardId = UUID.randomUUID();
        this.mDeclarer = "N";
        this.mNSResult = 0;
        this.mDeclarerTricksToContract = 0;
        this.mNS = false;
        this.mBye = false;
        this.mDLM = "";
    }

    public int bridgeResult(){

        int nBridgeResult = 0;

        int[] ZonaEW = {0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1};
        int[] ZonaNS = {0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0};
        int[] x0 = {100, 300, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500};
        int[] x1 = {200, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500, 3800};
        int[] xx0 = {200, 600, 1000, 1600, 2200, 2800, 3400, 4000, 4600, 5200, 5800, 6400, 7000};
        int[] xx1 = {400, 1000, 1600, 2200, 2800, 3400, 4000, 4600, 5200, 5800, 6400, 7000, 7600};
        try {
            int nZonaBord = mTournamentBoardId % 16;
            if (nZonaBord == 0) {
                nZonaBord = 16;
            }
            int nZona = 0;
            if ((mDeclarer == "N") || (mDeclarer == "S")) {
                nZona = ZonaNS[nZonaBord - 1];
            } else {
                nZona = ZonaEW[nZonaBord - 1];
            }

            int nNivo = Integer.parseInt(mContract.substring(0, 1));
            String strBoja = mContract.substring(1, 2);
            boolean bXX = false;
            boolean bX = false;
            if (mContract.length() > 2) {
                bXX = ((mContract.substring(mContract.length() - 2, mContract.length())).equals("XX"));
                bX = (((mContract.substring(mContract.length() - 1, mContract.length())).equals("X")) && (!bXX));
            }
            int nOvertrick = 1;

            if (mDeclarerTricksToContract >= 0) {

                if ((strBoja.equals("C")) || (strBoja.equals("D"))) {
                    nBridgeResult = 20 * nNivo;
                    nOvertrick = 20 * mDeclarerTricksToContract;
                }
                if ((strBoja.equals("H")) || (strBoja.equals("S"))) {
                    nBridgeResult = 30 * nNivo;
                    nOvertrick = 30 * mDeclarerTricksToContract;
                }
                if (strBoja.equals("N")) {
                    nBridgeResult = 30 * nNivo + 10;
                    nOvertrick = 30 * mDeclarerTricksToContract;
                }
                if (bXX) {
                    nBridgeResult *= 4;
                    if (nZona == 1) {
                        nOvertrick = 400 * mDeclarerTricksToContract;
                    } else {
                        nOvertrick = 200 * mDeclarerTricksToContract;
                    }
                }
                if (bX) {
                    nBridgeResult *= 2;
                    if (nZona == 1) {
                        nOvertrick = 200 * mDeclarerTricksToContract;
                    } else {
                        nOvertrick = 100 * mDeclarerTricksToContract;
                    }
                }

                if (nBridgeResult >= 100) {
                    if (nZona == 1) {
                        nBridgeResult += 500;
                    } else {
                        nBridgeResult += 300;
                    }

                    if (nNivo == 7) {
                        if (nZona == 1) {
                            nBridgeResult += 1500;
                        } else {
                            nBridgeResult += 1000;
                        }
                    }

                    if (nNivo == 6) {
                        if (nZona == 1) {
                            nBridgeResult += 750;
                        } else {
                            nBridgeResult += 500;
                        }
                    }
                } else {
                    nBridgeResult += 50;
                }
                if (bXX) {
                    nBridgeResult += 100;
                } else if (bX) {
                    nBridgeResult += 50;
                }
                nBridgeResult += nOvertrick;
            } else {
                if (nZona == 1) {
                    if (bXX) {
                        nBridgeResult = (-1) * xx1[abs(mDeclarerTricksToContract)-1];
                    } else if (bX) {
                        nBridgeResult = (-1) * x1[abs(mDeclarerTricksToContract)-1];
                    } else {
                        nBridgeResult = 100 * mDeclarerTricksToContract;
                    }

                } else {
                    if (bXX) {
                        nBridgeResult = (-1) * xx0[abs(mDeclarerTricksToContract)-1];
                    } else if (bX) {
                        nBridgeResult = (-1) * x0[abs(mDeclarerTricksToContract)-1];
                    } else {
                        nBridgeResult = 50 * mDeclarerTricksToContract;
                    }
                }
            }

            if ((mDeclarer.equals("E")) || (mDeclarer.equals("W"))) {
                nBridgeResult = nBridgeResult * (-1);
            }
        } catch(Exception e){
            nBridgeResult = 0;
        }
        return nBridgeResult;

    }

    public String getCards(String Hand, String Color){
        String Holding = "";
        if (mDLM.isEmpty()){
            return Holding;
        }

        int prvi, drugi, red_boja;

        prvi    = 0;
        drugi   = mRuka.get(Hand).size();

        switch (Color){
            case "S": red_boja = 0;
                break;
            case "H": red_boja = 1;
                break;
            case "D": red_boja = 2;
                break;
            case "C": red_boja = 3;
                break;
            default: red_boja = -1;
                break;
        }

        while (prvi < drugi)
        {

            if ((mRuka.get(Hand).get(prvi) / 13) == red_boja) {
                Holding = Holding + getKarta(mRuka.get(Hand).get(prvi) % 13) + " " ;
            }
            prvi++;
        };




        return Holding;
    }

    String getIgrac(int nPlayer){
        switch(nPlayer){
            case 0: return "N";
            case 1: return "E";
            case 2: return "S";
            case 3: return "W";
            default: return "?";
        }
    }

    String getKarta(int nKarta){
        switch(nKarta){
            case 0: return "A";
            case 1: return "K";
            case 2: return "Q";
            case 3: return "J";
            case 4: return "10";
            case 5: return "9";
            case 6: return "8";
            case 7: return "7";
            case 8: return "6";
            case 9: return "5";
            case 10: return "4";
            case 11: return "3";
            case 12: return "2";
            default: return "";
        }
    }

}
