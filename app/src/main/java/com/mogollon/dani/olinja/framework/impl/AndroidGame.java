package com.mogollon.dani.olinja.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.mogollon.dani.olinja.framework.Audio;
import com.mogollon.dani.olinja.framework.FileIO;
import com.mogollon.dani.olinja.framework.Game;
import com.mogollon.dani.olinja.framework.Graphics;
import com.mogollon.dani.olinja.framework.Input;
import com.mogollon.dani.olinja.framework.Screen;
import com.mogollon.dani.olinja.olinja.GameData;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    double screenRatio;
    double scaleRatio;
    int screenOffsetX;
    int screenOffsetY;
    int frameBufferWidth;
    int frameBufferHeight;

    public GameData getGameData() {
        return gameData;
    }

    @Override
    public InputMethodManager getSystemService() {
        return (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    GameData gameData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        frameBufferHeight= isLandscape ? displaymetrics.heightPixels : displaymetrics.widthPixels;
        frameBufferWidth = isLandscape ? displaymetrics.widthPixels : displaymetrics.heightPixels;
        screenRatio = (double) frameBufferWidth / (double)frameBufferHeight;

        SharedPreferences settings = getSharedPreferences("oLinjaPrefs", 0);
        gameData = new GameData(settings);


        // Calculate scaleRatio
        calculateScaleRatio();

        Bitmap frameBuffer = Bitmap.createBitmap((int) (frameBufferWidth),
                (int) (frameBufferHeight), Config.RGB_565);



        float scaleX = (float) ((float) frameBufferWidth);
                /// displaymetrics.widthPixels;
        float scaleY = (float) ((float) frameBufferHeight);
                /// displaymetrics.heightPixels;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer, scaleRatio, screenOffsetX, screenOffsetY);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);


        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {
        return screen;
    }

    private void calculateScaleRatio() {

        if (Double.compare(screenRatio,gameData.getGameScreenRatio()) < 0) {
            scaleRatio = (double) frameBufferWidth / gameData.getGameScreenWidth() ;
        } else {
            scaleRatio = (double) frameBufferHeight / gameData.getGameScreenHeight() ;
        }

        screenOffsetX = (int) (frameBufferWidth - (gameData.getGameScreenWidth() * scaleRatio)) / 2;
        screenOffsetY = (int) (frameBufferHeight - (gameData.getGameScreenHeight() * scaleRatio)) / 2;

    }

}
