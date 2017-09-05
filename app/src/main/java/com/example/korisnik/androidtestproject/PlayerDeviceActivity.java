package com.example.korisnik.androidtestproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.korisnik.androidtestproject.database.BridgeBoardsHelper;
import com.example.korisnik.androidtestproject.database.BridgeBoardsSchema;
import com.example.korisnik.androidtestproject.database.PlayerDeviceCursorWrapper;

import java.util.UUID;

import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.AKTIVAN;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.ID;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.IME;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.KLUB;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.MAIL;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.MOBITEL;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.PREZIME;
import static com.example.korisnik.androidtestproject.database.BridgeBoardsSchema.PlayerDevice.Cols.UUID;

/**
 * Created by Korisnik on 15.8.2017..
 */

public class PlayerDeviceActivity extends Activity {

    private PlayerDevice mPlayerDeviceData;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private TextView tvDeviceUUID;
    private EditText evPlayerName;
    private EditText evPlayerSurname;
    private EditText evPlayerEMail;
    private EditText evPlayerNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_device);

        tvDeviceUUID = (TextView) findViewById(R.id.tvDeviceUUID);


        evPlayerName = (EditText) findViewById(R.id.etIme);

        evPlayerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) { ; }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
                mPlayerDeviceData.setIme(pCharSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable pEditable) { ; }
        });

        evPlayerSurname = (EditText) findViewById(R.id.etPrezime);

        evPlayerSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) { ; }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
                mPlayerDeviceData.setPrezime(pCharSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable pEditable) { ; }
        });

        evPlayerEMail = (EditText) findViewById(R.id.etEmail);

        evPlayerEMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) { ; }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
                mPlayerDeviceData.setMail(pCharSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable pEditable) { ; }
        });

        evPlayerNumber = (EditText) findViewById(R.id.etMobitel);

        evPlayerNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) { ; }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
                mPlayerDeviceData.setMobitel(pCharSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable pEditable) { ; }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContext =  PlayerDeviceActivity.this.getApplicationContext();
        mDatabase = new BridgeBoardsHelper(mContext).getWritableDatabase();
        mPlayerDeviceData = getDevice();
        if (mPlayerDeviceData != null){
            tvDeviceUUID.setText(mPlayerDeviceData.getUUID().toString());
            evPlayerName.setText(mPlayerDeviceData.getIme());
            evPlayerSurname.setText(mPlayerDeviceData.getPrezime());
            evPlayerEMail.setText(mPlayerDeviceData.getMail());
            evPlayerNumber.setText(mPlayerDeviceData.getMobitel());
        } else {
            mDatabase.execSQL("INSERT INTO " + BridgeBoardsSchema.PlayerDevice.NAME +
                    " (" + BridgeBoardsSchema.PlayerDevice.Cols.ID +
                    ", " + BridgeBoardsSchema.PlayerDevice.Cols.UUID +
                    ", " + BridgeBoardsSchema.PlayerDevice.Cols.AKTIVAN + ")" +
                    " VALUES (1, '" + java.util.UUID.randomUUID().toString() + "', 1)");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateDevice(mPlayerDeviceData);
    }

    private PlayerDeviceCursorWrapper queryBoards(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BridgeBoardsSchema.PlayerDevice.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new PlayerDeviceCursorWrapper(cursor);
    }

    public void updateDevice(PlayerDevice pDevice){

        ContentValues values = getContentValues(pDevice);

        mDatabase.update(BridgeBoardsSchema.PlayerDevice.NAME,
                values,
                BridgeBoardsSchema.PlayerDevice.Cols.UUID + " = ?",
                new String[] {pDevice.getUUID().toString()});
    }

    public PlayerDevice getDevice(){
        PlayerDeviceCursorWrapper cursor = queryBoards("", null);

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPlayerDevice();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(PlayerDevice pDevice) {
        ContentValues values = new ContentValues();
        values.put(ID, pDevice.getID());
        values.put(IME, pDevice.getIme());
        values.put(PREZIME, pDevice.getPrezime());
        values.put(MAIL, pDevice.getMail());
        values.put(MOBITEL, pDevice.getMobitel());
        values.put(KLUB, pDevice.getKlub());
        values.put(AKTIVAN, pDevice.getAktivan());
        values.put(UUID, pDevice.getUUID().toString());

        return values;
    }
}
