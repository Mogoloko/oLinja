package com.mogollon.dani.olinja.olinja;


import android.content.SharedPreferences;
import android.util.Log;

import com.mogollon.dani.olinja.framework.Game;
import com.mogollon.dani.olinja.framework.Graphics;
import com.mogollon.dani.olinja.framework.Graphics.PixmapFormat;
import com.mogollon.dani.olinja.framework.Screen;
import com.mogollon.dani.olinja.framework.impl.PhpNetworkHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 24/01/2016.
 */
public class SplashScreen extends Screen {

    GameData gameData;
    Boolean imagesLoaded = false;

    public SplashScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.stick = g.newPixmap("palo.png", PixmapFormat.RGB565);
        Assets.redPiece = g.newPixmap("rojo.png", PixmapFormat.ARGB8888);
        Assets.blackPiece = g.newPixmap("negro.png", PixmapFormat.RGB565);
        Settings.load(game.getFileIO());

        if (!imagesLoaded) getLogin();


    }

    private void getLogin() {

        imagesLoaded = true;

        gameData = game.getGameData();

        String token = gameData.getToken();

        PhpNetworkHandler phpNetworkHandler = new PhpNetworkHandler("http://mogollones.com/olinja/networkhandler.php", "tokenLogin", "token=" + token) {
            @Override
            public void onDataReceived(JSONObject data) {

                try {

                    Boolean logged = Boolean.parseBoolean(data.getString("logged")) || false;

                    if (logged) {
                        game.setScreen(new MainMenuScreen(game));
                    } else {
                        Log.v("oLINJALOG", "No logueado");
                        //game.setScreen(new LoginScreen(game));
                        game.setScreen(new MainMenuScreen(game));
                    }

                } catch (JSONException e) {
                    Log.v("oLINJALOG", "Error en el JSON de SPLASH SCREEN");
                    e.printStackTrace();
                }
            }
        };
        phpNetworkHandler.execute();

    }

    @Override
    public void present(float deltaTime) {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
