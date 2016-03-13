package com.mogollon.dani.olinja.olinja;

import android.util.Log;

import com.mogollon.dani.olinja.framework.Game;
import com.mogollon.dani.olinja.framework.Graphics;
import com.mogollon.dani.olinja.framework.Screen;
import com.mogollon.dani.olinja.framework.impl.PhpNetworkHandler;

import org.json.JSONObject;

/**
 * Created by admin on 24/01/2016.
 */
public class MainMenuScreen extends Screen {

    String sUrl;


    public MainMenuScreen(Game game) {

        super(game);

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.redPiece, 0,0);
        g.drawText("FUNCIONA", 100, 100);

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
