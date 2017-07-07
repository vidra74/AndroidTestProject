package com.example.korisnik.androidtestproject;

import java.util.UUID;

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
    private char mDeclarer;
    private String mContract;
    private int mDeclarerTricksToContract;
    private int mNSResult;

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

    public char getDeclarer() {
        return mDeclarer;
    }

    public void setDeclarer(char pDeclarer) {
        mDeclarer = pDeclarer;
    }

    public String getContract() {
        return mContract;
    }

    public void setContract(String pContract) {
        mContract = pContract;
    }

    public int getDeclarerTricksToContract() {
        return mDeclarerTricksToContract;
    }

    public void setDeclarerTricksToContract(int pDeclarerTricksToContract) {
        mDeclarerTricksToContract = pDeclarerTricksToContract;
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

    }

}
