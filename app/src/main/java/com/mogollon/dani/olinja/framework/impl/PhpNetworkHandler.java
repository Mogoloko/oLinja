package com.mogollon.dani.olinja.framework.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.mogollon.dani.olinja.framework.PhpNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 06/02/2016.
 */
public abstract class PhpNetworkHandler extends AsyncTask<Void, Void, Void> implements PhpNetwork {

    String sUrl;
    String sAction;
    String params;

    URL url;
    HttpURLConnection urlConnection;
    InputStream in;
    JSONObject jsonObject = null;

    public PhpNetworkHandler(String sUrlParam, String sActionParam, String paramsParam) {

        this.sUrl = sUrlParam;
        this.sAction = sActionParam;
        this.params = paramsParam;


        try {

            url = new URL(sUrl);
            urlConnection = (HttpURLConnection) url.openConnection();



        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        jsonObject = launchQuery();


        onDataReceived(jsonObject);

        return null;
    }

    protected JSONObject  launchQuery() {

        Log.v("oLINJALOG", "-- Comienza launchQuery");

        try {
            Log.v("oLINJALOG", "Hace conexión");
            // Prepare the connection
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            // Prepare params
            urlConnection.getOutputStream().write(("action=" + this.sAction + "&" + params).getBytes());

            // Execute
            in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String response = "";
            String responseTmp = "";
            while ((responseTmp = br.readLine()) != null) {
                response += responseTmp;
            }
Log.v("oLINJALOG", "RESP: " + response);
            jsonObject = new JSONObject(response);
            Log.v("oLINJALOG", "Lanza Return");
            return jsonObject;

        } catch (IOException e) {
            Log.v("oLINJALOG", "IO Exception");
            e.printStackTrace();
            return null;

        } catch (JSONException e) {

            e.printStackTrace();
            Log.v("oLINJALOG", "JSON Exception");
            Log.v("oLINJALOG", e.getMessage());
            return null;

        } finally {
            Log.v("oLINJALOG", "Desconecta");
            urlConnection.disconnect();

        }

    }
}
