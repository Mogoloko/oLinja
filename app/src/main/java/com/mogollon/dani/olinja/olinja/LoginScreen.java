package com.mogollon.dani.olinja.olinja;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.mogollon.dani.olinja.framework.Game;
import com.mogollon.dani.olinja.framework.Graphics;
import com.mogollon.dani.olinja.framework.Screen;

/**
 * Created by admin on 07/02/2016.
 */
public class LoginScreen extends Screen {

    public LoginScreen(Game game) {

        super(game);

        InputMethodManager imm = (InputMethodManager) game.getSystemService();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
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
