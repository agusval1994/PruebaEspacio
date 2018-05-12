package com.example.agusv.pruebaespacio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by agusv on 20/2/2018.
 */

public class Estrellas {

    Juego juego;
    Paint paint;
    private int starAlpha = 80;
    private int starFade = 2;
    int x, y, radio;
    boolean una_vez = true;
    boolean muere = false;

    public Estrellas(Juego juego){

        this.juego = juego;
        paint = new Paint();
    }

    public void onDraw(Canvas canvas) {

        if (muere == false) {

            if (una_vez == true) {
                Random random = new Random();
                y = random.nextInt(canvas.getHeight());
                if (juego.mover == false) {
                    x = random.nextInt(canvas.getWidth());
                } else x = 0;
                radio = random.nextInt(7) + 1;
                una_vez = false;
            }
            //draw the stars
            paint.setColor(Color.CYAN);
            paint.setAlpha(starAlpha += starFade);
            //fade them in and out
            canvas.drawCircle(x, y, radio, paint);
            if (starAlpha >= 252 || starAlpha <= 80) starFade = starFade * -1;
            paint.setStrokeWidth(5);

            if (juego.movimiento_de_estrellas == true) {
                x = x + 10;
            }

            if (x >= canvas.getWidth()) {
                muere = true;
            }
        }
    }
}
