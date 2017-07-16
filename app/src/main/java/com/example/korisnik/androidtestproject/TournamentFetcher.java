package com.example.korisnik.androidtestproject;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 16.7.2017..
 */

public class TournamentFetcher {
    private static final String TAG = "TournamentFetcher";

    private static final String API_KEY = "REPLACE_ME_WITH_A_REAL_KEY";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Tournament> fetchItems() {

        List<Tournament> items = new ArrayList<>();

        try {
            String url = Uri.parse("http://franojancic.com/skola_bridza/json_turniri.php")
                    .buildUpon()
                    // .appendQueryParameter("method", "flickr.photos.getRecent")
                    // .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    // .appendQueryParameter("nojsoncallback", "1")
                    // .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            int pocetak = jsonString.indexOf("<body>");
            int kraj = jsonString.indexOf("</body>");
            jsonString = jsonString.substring(pocetak + 7, kraj - 1);
            // JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    private void parseItems(List<Tournament> items, String jsonBody)
            throws IOException, JSONException {

        JSONArray tournamentJsonArray = new JSONArray(jsonBody);

        for (int i = 0; i < tournamentJsonArray.length(); i++) {
            JSONObject itemJsonObject = tournamentJsonArray.getJSONObject(i);
            // pID, Date pDate, Time pTime, int pKlubId, int pTipObracun, int pBrojBordova, int pStatus, String pTurnirUUID
            // {"ID":"1","Datum":"2016-05-30","Vrijeme":"19:30:00","Klub":"1","Obracun":"1","Broj_Bordova":"18","STATUS":"2","UUID":""}
            Tournament item = new Tournament(itemJsonObject.getInt("ID"),
                    itemJsonObject.getString("Datum"),
                    itemJsonObject.getString("Vrijeme"),
                    itemJsonObject.getInt("Klub"),
                    itemJsonObject.getInt("Obracun"),
                    itemJsonObject.getInt("Broj_Bordova"),
                    itemJsonObject.getInt("STATUS"),
                    itemJsonObject.getString("UUID")
                    );

            items.add(item);
        }
    }
}
