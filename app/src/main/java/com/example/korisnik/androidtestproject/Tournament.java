package com.example.korisnik.androidtestproject;

import android.util.Log;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Korisnik on 16.7.2017..
 */

public class Tournament {
    // {"ID":"1","Datum":"2016-05-30","Vrijeme":"19:30:00","Klub":"1","Obracun":"1","Broj_Bordova":"18","STATUS":"2","UUID":""}
    private int mID;
    private String mDate;
    private String mTime;
    private int mKlubId;
    private int mTipObracun;
    private int mBrojBordova;
    private int mStatus;
    private UUID mTurnirUUID;

    public Tournament(int pID, String pDate, String pTime, int pKlubId, int pTipObracun, int pBrojBordova, int pStatus, String pTurnirUUID) {
        mID = pID;
        mDate = pDate;
        mTime = pTime;
        mKlubId = pKlubId;
        mTipObracun = pTipObracun;
        mBrojBordova = pBrojBordova;
        mStatus = pStatus;
        if ((pTurnirUUID != null && !pTurnirUUID.trim().isEmpty())) {
            mTurnirUUID = UUID.fromString(pTurnirUUID);
        }
    }

    public int getID() {
        return mID;
    }

    public void setID(int pID) {
        mID = pID;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String pDate) {
        mDate = pDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String pTime) {
        mTime = pTime;
    }

    public int getKlubId() {
        return mKlubId;
    }

    public void setKlubId(int pKlubId) {
        mKlubId = pKlubId;
    }

    public int getTipObracun() {
        return mTipObracun;
    }

    public void setTipObracun(int pTipObracun) {
        mTipObracun = pTipObracun;
    }

    public int getBrojBordova() {
        return mBrojBordova;
    }

    public void setBrojBordova(int pBrojBordova) {
        mBrojBordova = pBrojBordova;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int pStatus) {
        mStatus = pStatus;
    }

    public UUID getTurnirUUID() {
        return mTurnirUUID;
    }

    public void setTurnirUUID(UUID pTurnirUUID) {
        mTurnirUUID = pTurnirUUID;
    }

    public void setTurnirUUID(String pTurnirUUID) {
        try {
            mTurnirUUID = UUID.fromString(pTurnirUUID);
        } catch (Exception E){
            Log.e("UUIDEXC", "Tournament.setTurnirUUID(String pTurnirUUID) " + E.toString());
        };
    }

    public String toString()
    {
        return (mDate + " " + mTime);
    }

    public Boolean isEditable()
    {
        return (mTurnirUUID != null);
    }

}
