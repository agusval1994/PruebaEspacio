package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by agusv on 1/3/2018.
 */

public class DestruccionPlaneta {

    Juego juego;
    Paint paint;


    //Int
    int x = 0, y = 0;
    int currentFrame = 0;
    int currentFrame2 = 0;
    int ancho;
    int alto;
    int radio = 130, circX, circY;

    //Boolean
    Bitmap personaje;


    //Animacion
    static final int Horizontal = 3;
    static final int Vertical = 6;
    // This variable tracks the game frame rate
    long fps;
    // This is used to help calculate the fps
    private long timeThisFrame;
    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;
    // How long should each frame last
    private int frameLengthInMilliseconds = 100;
    //cantidad de frames
    int cantFrames = 6;
    int cantFrames2 = 3;


    public DestruccionPlaneta(Juego juego, Bitmap personaje) {
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
                if (juego.prologo == true) {
                    juego.destruccion_planeta = false;
                    juego.posicion1 = false;
                    juego.posicion2 = true;
                    juego.me_puedo_mover = true;
                    juego.primer_efecto = false;
                    juego.segundo_efecto = true;
                    juego.movimiento_de_estrellas = true;
                    juego.mover = true;
                    juego.efecto_movimiento();

                } else if (juego.capitulo1 == true) {
                    juego.finDeJuego = true;
                }
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
        if (juego.prologo == true) {
            x = (canvas.getWidth() / 2 - 600);
            y = (canvas.getHeight() / 2) - alto/2;

        } else if (juego.capitulo1 == true) {

            x = (canvas.getWidth() / 2) - (ancho / 2);
            y = (canvas.getHeight() / 2) - (alto / 2);
        }
        Rect dst = new Rect(x, y, x + ancho, y + alto);
        canvas.drawBitmap(personaje, src, dst, null);
    }
}
