package com.mogollon.dani.olinja.olinja;

import com.mogollon.dani.olinja.framework.Screen;
import  com.mogollon.dani.olinja.framework.impl.AndroidGame;

/**
 * Created by admin on 24/01/2016.
 */
public class OlinjaGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new SplashScreen(this);
    }
}
