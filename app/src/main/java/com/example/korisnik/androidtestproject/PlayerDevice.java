package com.example.korisnik.androidtestproject;

import java.io.StringBufferInputStream;
import java.util.UUID;

/**
 * Created by Korisnik on 15.8.2017..
 */

public class PlayerDevice {
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID pUUID) {
        mUUID = pUUID;
    }

    public int getID() {
        return mID;
    }

    public void setID(int pID) {
        mID = pID;
    }

    public String getIme() {
        return mIme;
    }

    public void setIme(String pIme) {
        mIme = pIme;
    }

    public String getPrezime() {
        return mPrezime;
    }

    public void setPrezime(String pPrezime) {
        mPrezime = pPrezime;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String pMail) {
        mMail = pMail;
    }

    public String getMobitel() {
        return mMobitel;
    }

    public void setMobitel(String pMobitel) {
        mMobitel = pMobitel;
    }

    public int getKlub() {
        return mKlub;
    }

    public void setKlub(int pKlub) {
        mKlub = pKlub;
    }

    public int getAktivan() {
        return mAktivan;
    }

    public void setAktivan(int pAktivan) {
        mAktivan = pAktivan;
    }

    private UUID mUUID;
    private int mID;
    private String mIme;
    private String mPrezime;
    private String mMail;
    private String mMobitel;
    private int mKlub;
    private int mAktivan;

    public PlayerDevice(){
        this.mUUID = UUID.randomUUID();
        this.mID = 1;
        this.mKlub = 1;
        this.mAktivan = 1;
    }
}
