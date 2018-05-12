package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;

import java.util.Random;

/**
 * Created by agusv on 9/4/2018.
 */

public class MuerteEspiritu {

    private static final int Horizontal = 1;
    private static final int Vertical = 8;
    int x = 0;
    int y = 0;
    private Juego juego;
    private Bitmap muerte_espiritu;
    int currentFrame = 0;
    int ancho;
    int alto;
    int radio = 25;
    int magico = 0;
    Paint paint;
    boolean una_vez = true;
    int ancho_espiritu_sin_brillo, alto_espiritu_sin_brillo;
    boolean muere = false;
    boolean sumar = true;
    float moverX, moverY;

    // This variable tracks the game frame rate
    long fps;
    // This is used to help calculate the fps
    private long timeThisFrame;
    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;
    // How long should each frame last
    private int frameLengthInMilliseconds = 150;
    //cantidad de frames
    int cantFrames = 8;
    int distX, distY, speed = 20, magicoX, magicoY, xinicial, yinicial;
    boolean movFinal = false;

    public MuerteEspiritu(Juego juego, Bitmap muerte_espiritu) {

        this.juego = juego;
        this.muerte_espiritu = muerte_espiritu;
        this.ancho = muerte_espiritu.getWidth() / Vertical;
        this.alto = muerte_espiritu.getHeight() / Horizontal;

        paint = new Paint();
    }

    private void update() {

        long startFrame = System.currentTimeMillis();

        if (startFrame > lastFrameChangeTime + frameLengthInMilliseconds) {
            lastFrameChangeTime = startFrame;
            currentFrame++;
            if (currentFrame >= cantFrames) {

                currentFrame = 0;
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
        int srcY = alto * magico;
        Rect src = new Rect(srcX, srcY, srcX + ancho, srcY + alto);

        if (muere == false) {

            if (una_vez == true) {
                x = juego.x + ancho/2;
                y = juego.y + alto/2;

                xinicial = (canvas.getWidth()/2) - ancho/2;
                yinicial = (canvas.getHeight()/2) + alto/2;

                Aleatorio();
                BorrarDespuesDe5Segundos();

                una_vez = false;
            }

            Rect dst = new Rect(x, y, x + ancho, y + alto);
            canvas.drawBitmap(muerte_espiritu, src, dst, null);

            if (movFinal == false) {
                int movX = (int) moverX;
                int movY = (int) moverY;

                x = x + movX;
                y = y + movY;
            }

            if (movFinal == true) {
                distX = (xinicial - x) / speed;
                distY = (yinicial - y) / speed;

                x = x + distX;
                y = y + distY;

                if (distX == 0 && distY == 0){
                    muere = true;
                    juego.casi_fin_del_cap2 = false;
                    juego.mostrar_espiritu_fin_cap2 = true;
                    juego.finDeJuego = true;
                    juego.final_del_capitulo2 = true;
                }
            }
        }
    }

    public void Aleatorio(){
        Random random = new Random();
        float leftLimit = -6.0F;
        float rightLimit = 6.0F;
        moverX = leftLimit + random.nextFloat() * (rightLimit - leftLimit);
        moverY = leftLimit + random.nextFloat() * (rightLimit - leftLimit);

        if (moverX > -2 && moverX < -0.5 || moverX > 0.5 && moverX < 2){
            Aleatorio();
        }

        if (moverY > -2 && moverY < -0.5 || moverY > 0.5 && moverY < 2){
            Aleatorio();
        }
    }

    public void BorrarDespuesDe5Segundos(){
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                movFinal = true;
            }
        }.start();
    }
}
