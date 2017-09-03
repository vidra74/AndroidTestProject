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
import java.util.UUID;

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

    public List<Board> fetchBoards(String tournament_uuid) {

        List<Board> items = new ArrayList<>();

        try {
            String url = Uri.parse("http://franojancic.com/skola_bridza/json_turniri_bordovi.php")
                    .buildUpon()
                    // .appendQueryParameter("method", "flickr.photos.getRecent")
                    // .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("t_uuid", tournament_uuid)
                    // .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            int pocetak = jsonString.indexOf("<body>");
            int kraj = jsonString.indexOf("</body>");
            jsonString = jsonString.substring(pocetak + 7, kraj - 1);
            // JSONObject jsonBody = new JSONObject(jsonString);
            parseBoards(items, jsonString);
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

    private void parseBoards(List<Board> items, String jsonBody)
            throws IOException, JSONException {

        JSONArray boardJsonArray = new JSONArray(jsonBody);

        for (int i = 0; i < boardJsonArray.length(); i++) {
            JSONObject itemJsonObject = boardJsonArray.getJSONObject(i);
            // `ID_BORD`, `ID_TURNIR`, `RBR_BORD`, `UUID_TURNIR`, `UUID_BORD`, `DLM`, `VRIJEME`
            Board item = new Board(UUID.fromString(itemJsonObject.getString("UUID_TURNIR")), 1);
            item.setBoardId(UUID.fromString(itemJsonObject.getString("UUID_BORD")));
            item.setTournamentBoardId(itemJsonObject.getInt("RBR_BORD"));
            item.setDLM(itemJsonObject.getString("DLM"));
            items.add(item);
        }
    }

    public void sendBoards(Board board){
        try {

            int ns_pair;
            int ew_pair;
            int is_bye = 0;
            if (board.isNS()){
                ns_pair = board.getPairId();
                ew_pair = board.getOppsPairId();
            } else {
                ew_pair = board.getPairId();
                ns_pair = board.getOppsPairId();
            }
            if (board.isBye()) { is_bye = 1; }


            // http://www.franojancic.com/skola_bridza/prijava_rezultati.php?
            // t_uuid=33671fea-8e5e-11e7-a351-00155d017c09&b_uuid=33675b5a-8e5e-11e7-a351-00155d017c09&
            // ns_pair=8&ew_pair=1&contract=3NT&rbr=1&declarer=N&lead=S2&dec_tricks=9&ns_result=400&is_bye=0 
            String url = Uri.parse("http://franojancic.com/skola_bridza/prijava_rezultati.php")
                    .buildUpon()
                    .appendQueryParameter("t_uuid", board.getTournamentId().toString())
                    .appendQueryParameter("b_uuid", board.getBoardId().toString())
                    .appendQueryParameter("ns_pair", new Integer(ns_pair).toString())
                    .appendQueryParameter("ew_pair", new Integer(ew_pair).toString())
                    .appendQueryParameter("contract", board.getContract())
                    .appendQueryParameter("rbr", new Integer(board.getTournamentBoardId()).toString())
                    .appendQueryParameter("declarer", board.getDeclarer())
                    .appendQueryParameter("lead", board.getLead())
                    .appendQueryParameter("dec_tricks", new Integer(board.getDeclarerTricksToContract()).toString())
                    .appendQueryParameter("ns_result", new Integer(board.getNSResult()).toString())
                    .appendQueryParameter("is_bye", new Integer(is_bye).toString())
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received : " + jsonString);
        } catch (Exception ioe) {
            Log.e(TAG, "Failed to send board", ioe);
        }
    }
}
