package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by agusv on 6/3/2018.
 */

public class OtroPlaneta {

    static final int Horizontal = 2;
    static final int Vertical = 14;
    int x = 0;
    int y = 0;
    Juego juego;
    Bitmap personaje;
    int currentFrame = 0;
    int currentFrame2 = 0;
    int ancho;
    int alto;
    int radio = 130, circX, circY;
    Paint paint;

    // This variable tracks the game frame rate
    long fps;
    // This is used to help calculate the fps
    private long timeThisFrame;
    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;
    // How long should each frame last
    private int frameLengthInMilliseconds = 300;
    //cantidad de frames
    int cantFrames = 14;
    int cantFrames2 = 2;
    int distX, distY, speed = 20, magicoX, magicoY;
    boolean una_vez = true;

    public OtroPlaneta(Juego juego, Bitmap personaje){
        this.juego = juego;
        this.personaje = personaje;
        this.ancho = personaje.getWidth() / Vertical;
        this.alto = personaje.getHeight() / Horizontal;

        paint = new Paint();
    }

    private void update() {

        long startFrame = System.currentTimeMillis();

        if (startFrame > lastFrameChangeTime + frameLengthInMilliseconds) {
            lastFrameChangeTime = startFrame;
            currentFrame++;
            if (currentFrame >= cantFrames) {
                currentFrame2++;
                currentFrame = 0;
            }
            if (currentFrame2 >= cantFrames2) {
                currentFrame2 = 0;
            }
        }

        long startFrameTime = System.currentTimeMillis();

        timeThisFrame = System.currentTimeMillis() - startFrameTime;
        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        // cual de los personajes se ve
        int srcX = currentFrame * ancho;
        // cual de las filas muestro
        int srcY = alto * currentFrame2;
        Rect src = new Rect(srcX, srcY, srcX + ancho, srcY + alto);
        if (una_vez == true) {
            x = 0 - (ancho / 2);
            y = (canvas.getHeight() / 2) - (alto / 2);
            una_vez = false;
        }

        Rect dst = new Rect(x, y, x + ancho, y + alto);
        canvas.drawBitmap(personaje, src, dst, null);

        magicoX = (canvas.getWidth()/2 - 600);
        magicoY = (canvas.getHeight()/2) - 100;

        distX = (magicoX - x) / speed;
        distY = (magicoY - y) / speed;

        x = x + distX;
        y = y + distY;
    }
}