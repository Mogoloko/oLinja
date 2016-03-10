package com.mogollon.dani.olinja.olinja;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Daniel Mogollón on 30/01/2016.
 */
public class GameData {

    Player player;

    public int getGameScreenWidth() {
        return gameScreenWidth;
    }

    public int getGameScreenHeight() {
        return gameScreenHeight;
    }

    int gameScreenWidth = 640;
    int gameScreenHeight = 480;

    public double getGameScreenRatio() {
        return gameScreenRatio;
    }

    double gameScreenRatio = 1.33;

    public String getToken() {
        return token;
    }

    String token;
    int lastUpdate;
    ArrayList<Partida> partidas = new ArrayList<Partida>();
    SharedPreferences settings;

    public GameData(SharedPreferences settingsPa) {

        this.settings = settingsPa;
        _init();
    }

    private void _init() {

        token = this.settings.getString("token", "");
    }
}