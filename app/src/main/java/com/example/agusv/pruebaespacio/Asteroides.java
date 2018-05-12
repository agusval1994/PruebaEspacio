package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;

import java.util.Random;

/**
 * Created by agusv on 20/2/2018.
 */

public class Asteroides {

    Juego juego;
    Bitmap asteroide, destruccion;
    Paint paint;


    //Boolean
    boolean una_vez = true;
    boolean muere = false;
    boolean moverse = false;
    boolean espera = false;
    boolean dibujar = true;
    boolean destuc = false;
    boolean chequearColision = false;
    boolean muere_una_vez = false;


    //Int, Float
    float x, y, ancho, alto, distXX, distYY, speedX=5, speedY=5, radio = 80, circX, circY, speed;
    int currentFrame = -1, ancho_dest, alto_dest, magico = 0;


    //Animacion
    private static final int Horizontal = 1;
    private static final int Vertical = 2;
    // This variable tracks the game frame rate
    long fps;
    // This is used to help calculate the fps
    private long timeThisFrame;
    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;
    // How long should each frame last
    private int frameLengthInMilliseconds = 250;
    //cantidad de frames
    int cantFrames = 2;


    public Asteroides(Juego juego, Bitmap asteroide, Bitmap destruccion){

        this.juego = juego;
        this.asteroide = asteroide;
        this.ancho = asteroide.getWidth();
        this.alto = asteroide.getHeight();

        //Puede que haya error por eso, sino funca hay que crear otro sprite
        this.destruccion = destruccion;
        this.ancho_dest = destruccion.getWidth()/Vertical;
        this.alto_dest = destruccion.getHeight()/Horizontal;

        paint = new Paint();
    }

    public void onDraw(Canvas canvas) {

        if (juego.prologo == true) {
            if (dibujar == true) {
                if (muere == false) {
                    if (una_vez == true) {
                        Random random = new Random();
                        speed = random.nextInt(21) + 60;
                        y = random.nextInt(canvas.getHeight()) - (alto / 2);
                        x = 0 - (ancho / 2);
                        una_vez = false;
                    }

                    chequearColision = true;
                    canvas.drawBitmap(asteroide, x, y, paint);
                    circX = x + (ancho / 2);
                    circY = y + (alto / 2);

                    x = x + 30;

                    if (x >= canvas.getWidth()) {
                        muere = true;
                    }
                }

                if (destuc == true) {
                    destruccion();
                    // cual de los personajes se ve
                    int srcX2 = currentFrame * ancho_dest;
                    // cual de las filas muestro
                    int srcY2 = alto_dest * magico;
                    Rect src = new Rect(srcX2, srcY2, srcX2 + ancho_dest, srcY2 + alto_dest);
                    int x2 = (int) x;
                    int y2 = (int) y;
                    Rect dst = new Rect(x2, y2, x2 + ancho_dest, y2 + alto_dest);
                    canvas.drawBitmap(destruccion, src, dst, null);
                }

                double dist = Math.sqrt(Math.pow((x - juego.x), 2) + Math.pow((y - juego.y), 2));

                if (chequearColision == true) {
                    if (dist < radio + juego.radio) {
                        destuc = true;
                        muere = true;
                    }
                }
            }
        }

        //_____________________________________________________________________________________

        if (juego.capitulo1 == true) {

            if (dibujar == true) {

                if (muere == false) {

                    if (una_vez == true) {
                        Random random = new Random();
                        int donde_aparece = random.nextInt(4);
                        int speed = random.nextInt(21) + 100;

                        if (donde_aparece == 0) {
                            //Aparece arriba
                            x = random.nextInt(canvas.getWidth());
                            y = 0 - (alto / 2);
                        } else if (donde_aparece == 1) {
                            //Aparece abajo
                            x = random.nextInt(canvas.getWidth());
                            y = canvas.getHeight() - (alto / 2);
                        } else if (donde_aparece == 2) {
                            //Aparece a la izquierda
                            y = random.nextInt(canvas.getHeight());
                            x = 0 - (ancho / 2);
                        } else if (donde_aparece == 3) {
                            //Aparece a la derecha
                            y = random.nextInt(canvas.getHeight());
                            x = canvas.getWidth() - (ancho / 2);
                        }

                        distXX = (canvas.getWidth() / 2 - x) / speed;
                        distYY = (canvas.getHeight() / 2 - y) / speed;
                        una_vez = false;
                    }

                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            espera = true;
                        }

                        public void onFinish() {
                            moverse = true;
                        }
                    }.start();

                    if (espera == true) {
                        canvas.drawBitmap(asteroide, x, y, paint);
                    }

                    if (moverse == true) {
                        chequearColision = true;
                        canvas.drawBitmap(asteroide, x, y, paint);
                        circX = x + (ancho / 2);
                        circY = y + (alto / 2);

                        if (x >= canvas.getWidth() - ancho) {
                            speedX = -distXX;
                        }
                        if (x <= ancho) {
                            speedX = distXX;
                        }
                        if (y >= canvas.getHeight() - alto) {
                            speedY = -distYY;
                        }
                        if (y <= alto) {
                            speedY = distYY;
                        }

                        x = x + distXX;
                        y = y + distYY;
                    }
                }

                if (destuc == true) {
                    destruccion();
                    // cual de los personajes se ve
                    int srcX2 = currentFrame * ancho_dest;
                    // cual de las filas muestro
                    int srcY2 = alto_dest * magico;
                    Rect src = new Rect(srcX2, srcY2, srcX2 + ancho_dest, srcY2 + alto_dest);
                    int x2 = (int) x;
                    int y2 = (int) y;
                    Rect dst = new Rect(x2, y2, x2 + ancho_dest, y2 + alto_dest);
                    canvas.drawBitmap(destruccion, src, dst, null);
                }

                double dist = Math.sqrt(Math.pow((x - juego.x), 2) + Math.pow((y - juego.y), 2));

                if (chequearColision == true) {
                    if (dist < radio + juego.radio) {
                        destuc = true;
                        muere = true;
                    }
                }

                double distPlanetaAsteroide = Math.sqrt(Math.pow((circX - juego.otroX), 2) + Math.pow((circY - juego.otrY), 2));

                if (distPlanetaAsteroide < (radio + juego.otroRadio) - 20) {
                    muere = true;
                    dibujar = false;
                    juego.destruccion_planeta = true;
                }
            }
        }

        //____________________________________________________________________________________

        if (juego.capitulo2 == true){
            if (dibujar == true){
                if (muere == false){
                    if (una_vez == true) {
                        Random random = new Random();
                        x = 0 - ancho/2;
                        y = random.nextInt(canvas.getHeight());

                        int uno = (canvas.getWidth()/2)/3;
                        int dos = canvas.getWidth()/2;

                        if (juego.x < uno + dos){
                            if (juego.contador_espiritu_energia >= 10){
                                speed = random.nextInt(20) + 60;
                            } else speed = random.nextInt(40) + 80;

                        } else if (juego.x > uno+dos && juego.x < uno+uno+dos) {
                            if (juego.contador_espiritu_energia >= 10){
                                speed = random.nextInt(20) + 80;
                            } else speed = random.nextInt(60) + 100;

                        } else if (juego.x > uno+uno+dos && juego.x < canvas.getWidth()) {
                            if (juego.contador_espiritu_energia >= 10){
                                speed = random.nextInt(21) + 100;
                            } else speed = random.nextInt(80) + 120;
                        }

                        distXX = (juego.x - x) / speed;
                        distYY = (juego.y - y) / speed;

                        moverse = true;
                        una_vez = false;
                    }

                    canvas.drawBitmap(asteroide, x, y, paint);

                    if (moverse == true) {
                        chequearColision = true;
                        canvas.drawBitmap(asteroide, x, y, paint);
                        circX = x + (ancho / 2);
                        circY = y + (alto / 2);

                        if (x >= canvas.getWidth() - ancho) {
                            speedX = -distXX;
                        }
                        if (x <= ancho) {
                            speedX = distXX;
                        }
                        if (y >= canvas.getHeight() - alto) {
                            speedY = -distYY;
                        }
                        if (y <= alto) {
                            speedY = distYY;
                        }

                        x = x + distXX;
                        y = y + distYY;
                    }
                }

                if (x >= canvas.getWidth()) {
                    muere = true;
                }

                double dist = Math.sqrt(Math.pow((x - juego.x), 2) + Math.pow((y - juego.y), 2));

                if (chequearColision == true) {
                    if (dist < radio + juego.radio) {
                        if (juego.cantidad_de_asteroides == 10){
                            destuc = true;
                        } else if (juego.cantidad_de_asteroides < 10){
                            if (muere_una_vez == false) {
                                juego.muerteDeEspiritu();
                                muere_una_vez = true;
                                juego.tirameEseFinalPapa = true;
                                juego.casi_fin_del_cap2 = true;
                                juego.jugando = false;
                                juego.contador_espiritu_energia = 0;
                            }
                        }
                        muere = true;
                    }
                }

                if (destuc == true) {
                    destruccion();
                    // cual de los personajes se ve
                    int srcX2 = currentFrame * ancho_dest;
                    // cual de las filas muestro
                    int srcY2 = alto_dest * magico;
                    Rect src = new Rect(srcX2, srcY2, srcX2 + ancho_dest, srcY2 + alto_dest);
                    int x2 = (int) x;
                    int y2 = (int) y;
                    Rect dst = new Rect(x2, y2, x2 + ancho_dest, y2 + alto_dest);
                    canvas.drawBitmap(destruccion, src, dst, null);
                }
            }
        }
    }

    public void destruccion() {

        if (destuc == true) {
            long startFrame = System.currentTimeMillis();

            if (startFrame > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = startFrame;
                currentFrame++;
                if (currentFrame >= cantFrames) {
                    dibujar = false;
                }
            }

            long startFrameTime = System.currentTimeMillis();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }
}