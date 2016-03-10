package com.mogollon.dani.olinja.framework;

import com.mogollon.dani.olinja.framework.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
