package com.mogollon.dani.olinja.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.mogollon.dani.olinja.framework.Graphics;
import com.mogollon.dani.olinja.framework.Pixmap;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
    int screenOffsetX;
    int screenOffsetY;
    double scaleRatio;

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer, double scaleRatio, int screenOffsetXP, int screenOffsetYP) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
        this.scaleRatio = scaleRatio;
        this.screenOffsetX = screenOffsetXP;
        this.screenOffsetY = screenOffsetYP;
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = (int) (scaleRatio * x) + screenOffsetX;
        dstRect.top = (int) (scaleRatio * y)  + screenOffsetY;
        dstRect.right = (int) (scaleRatio * (x + srcWidth - 1))+ screenOffsetX;
        dstRect.bottom = (int) (scaleRatio * (y + srcHeight - 1)) + screenOffsetY;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    public void drawPixmap(Pixmap pixmap, int srcX, int srcY) {
        int srcWidth = (int) (pixmap.getWidth());
        int srcHeight = (int) (pixmap.getHeight());

        srcRect.left = 0;
        srcRect.top = 0;
        srcRect.right = srcWidth - 1;
        srcRect.bottom = srcHeight - 1;

        dstRect.left = (int) (scaleRatio * srcX) + screenOffsetX;
        dstRect.top = (int) (scaleRatio * srcY) + screenOffsetY;
        dstRect.right = (int) (scaleRatio * (srcRect.right +srcX))+ screenOffsetX;
        dstRect.bottom = (int) (scaleRatio * (srcRect.bottom + srcY)) + screenOffsetY;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);

    }

    public void  drawText(String text, int srcX, int srcY) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvas.drawText(text, (int)(scaleRatio * srcX) + screenOffsetX, (int) (scaleRatio * srcY) + screenOffsetY, paint);

    }

    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }
}

