package com.mogollon.dani.olinja.framework;

import android.view.inputmethod.InputMethodManager;

import com.mogollon.dani.olinja.olinja.GameData;

public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();

    public GameData getGameData();

    public InputMethodManager getSystemService();

}