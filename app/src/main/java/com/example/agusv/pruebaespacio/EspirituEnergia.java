package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;

import java.util.Random;

/**
 * Created by agusv on 2/4/2018.
 */

public class EspirituEnergia {

    private static final int Horizontal = 1;
    private static final int Vertical = 8;
    int x = 0;
    int y = 0;
    private Juego juego;
    private Bitmap espiritu_energia;
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
    float moverX, moverY, xinicial, yinicial;

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
    int distX, distY, speed = 20, magicoX, magicoY;

    public EspirituEnergia(Juego juego, Bitmap espiritu_energia) {

        this.juego = juego;
        this.espiritu_energia = espiritu_energia;
        this.ancho = espiritu_energia.getWidth() / Vertical;
        this.alto = espiritu_energia.getHeight() / Horizontal;

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

        // cual de los personajes se ve
        int srcX = currentFrame * ancho;
        // cual de las filas muestro
        int srcY = alto * magico;
        Rect src = new Rect(srcX, srcY, srcX + ancho, srcY + alto);
        if (juego.capitulo2 == true) {
            if (muere == false) {
                update();
                if (una_vez == true) {
                    Random random = new Random();
                    x = 0 - ancho / 2;
                    float min = alto / 2;
                    float max = canvas.getHeight() - alto - alto / 2;
                    float aleatorio = min + random.nextFloat() * (max - min);
                    y = (int) aleatorio;
                    una_vez = false;
                }

                Rect dst = new Rect(x, y, x + ancho, y + alto);
                canvas.drawBitmap(espiritu_energia, src, dst, null);

                x = x + 15;

                paint.setColor(Color.YELLOW);
                canvas.drawCircle(x + (ancho / 2), y + (alto / 2), radio, paint);
            }

            double dist = Math.sqrt(Math.pow((x - juego.x), 2) + Math.pow((y - juego.y), 2));

            if (dist < radio + juego.radio) {
                muere = true;
                sumarUno();
            }

            if (juego.capitulo2_pierde == true){
                if (una_vez == true) {
                    x = juego.x;
                    y = juego.y;

                    xinicial = juego.x;
                    yinicial = juego.y;

                    Aleatorio();
                    BorrarDespuesDe5Segundos();

                }

                Rect dst = new Rect(x, y, x + ancho, y + alto);
                canvas.drawBitmap(espiritu_energia, src, dst, null);

                int movX = (int) moverX;
                int movY = (int) moverY;

                x = x + movX;
                y = y + movY;
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
                muere = true;
            }
        }.start();
    }

    public void sumarUno(){
        if (sumar == true){
            juego.contador_espiritu_energia++;
            sumar = false;
        }
    }
}
