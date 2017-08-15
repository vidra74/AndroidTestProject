package com.example.korisnik.androidtestproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.korisnik.androidtestproject.PlayerDevice;

import java.util.UUID;

/**
 * Created by Korisnik on 15.8.2017..
 */

public class PlayerDeviceCursorWrapper extends CursorWrapper {
    public PlayerDeviceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public PlayerDevice getPlayerDevice(){

        int id          = getInt(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.ID));
        String ime      = getString(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.IME));
        String prezime  = getString(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.PREZIME));
        String mail     = getString(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.MAIL));
        String mobitel  = getString(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.MOBITEL));
        int klub        = getInt(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.KLUB));
        int aktivan     = getInt(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.AKTIVAN));
        String uuid     = getString(getColumnIndex(BridgeBoardsSchema.PlayerDevice.Cols.UUID));

        PlayerDevice lDevice = new PlayerDevice();

        lDevice.setID(id);
        lDevice.setIme(ime);
        lDevice.setPrezime(prezime);
        lDevice.setMail(mail);
        lDevice.setMobitel(mobitel);
        lDevice.setKlub(klub);
        lDevice.setAktivan(aktivan);
        lDevice.setUUID(UUID.fromString(uuid));

        return lDevice;
    }
}
