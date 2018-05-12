package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;

import java.util.Random;

/**
 * Created by agusv on 24/2/2018.
 */

public class Espiritu_Enemigo {

    private Juego juego;
    private Bitmap personaje;


    //Int
    int x = 0, y = 0, distXX, distYY, speedX, speedY, contador_movimiento, xfinal, yfinal;
    int Posinicial;
    int Posfinal;
    float aleatorio;

    //Boolean

    boolean moverse_cap2 = true;
    boolean primer_mov_cap2 = true;
    boolean segundo_mov_cap2 = false;
    boolean tercer_mov_cap2 = false;
    boolean una_vez2 = true;
    boolean una_vez3 = true;
    boolean una_vez_cuatro = true;
    boolean una_vez_ocho = true;
    boolean una_vez_diez = true;
    boolean contador_en_cuatro = true;
    boolean contador_en_ocho = true;
    boolean contador_en_diez = true;
    boolean prueba = true;
    boolean una_vez_fin_cap2 = true;

    //Animacion
    private static final int Horizontal = 1;
    private static final int Vertical = 8;
    int currentFrame = 0;
    int ancho;
    int alto;
    int radio = 50;
    int magico = 0;
    Paint paint;
    int distX, distY, speed = 20, magicoX, magicoY;
    boolean una_vez = true;
    boolean dibujar = true;

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
    boolean espera = false;
    boolean moverse = false;
    boolean chequearColision = false;
    Rect src, dst;

    public Espiritu_Enemigo(Juego juego, Bitmap personaje) {

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

        if (juego.prologo == true) {

            if (juego.posicion1 == true) {
                if (una_vez == true) {
                    x = 0 - (ancho / 2);
                    y = (canvas.getHeight() / 2) - (alto / 2);
                    una_vez = false;
                }

                magicoX = ancho / 2;
                magicoY = (canvas.getHeight() / 2) - (alto / 2);

                distX = (magicoX - x) / speed;
                distY = (magicoY - y) / speed;

                x = x + distX;
                y = y + distY;
            }

            if (juego.posicion2 == true) {

                y = juego.y;
            }

            if (dibujar == true) {
                src = new Rect(srcX, srcY, srcX + ancho, srcY + alto);
                dst = new Rect(x, y, x + ancho, y + alto);
                canvas.drawBitmap(personaje, src, dst, null);
                paint.setColor(Color.TRANSPARENT);
                canvas.drawCircle(x + (ancho / 2), y + (alto / 2), radio, paint);
            }

            if (moverse == true) {

                chequearColision = true;

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

            double dist = Math.sqrt(Math.pow((x - juego.x), 2) + Math.pow((y - juego.y), 2));

            if (chequearColision == true) {
                if (dist < radio + juego.radio) {
                    una_vez = true;
                    juego.golpe_enemigo++;
                    moverse = false;
                }
            }

            double distPlanetaEspiritu = Math.sqrt(Math.pow((x - juego.colisionEspirituPlanetaX), 2) + Math.pow((y - juego.colisionEspirituPlanetaY), 2));

            if (distPlanetaEspiritu < (radio + juego.otroRadio) - 20) {
                juego.destruccion_planeta = true;
            }
        }

        if (juego.capitulo1 == true) {

            if (una_vez == true) {

                Random random = new Random();
                int donde_aparece = random.nextInt(4);
                int speed = random.nextInt(21) + 60;

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

                distXX = ((canvas.getWidth() / 2 - ancho / 2) - x) / speed;
                distYY = ((canvas.getHeight() / 2 - alto / 2) - y) / speed;

                una_vez = false;
                contar();
            }

            if (dibujar == true) {
                src = new Rect(srcX, srcY, srcX + ancho, srcY + alto);
                dst = new Rect(x, y, x + ancho, y + alto);
                canvas.drawBitmap(personaje, src, dst, null);
                paint.setColor(Color.YELLOW);
                canvas.drawCircle(x + (ancho / 2), y + (alto / 2), radio, paint);
            }

            if (moverse == true) {

                chequearColision = true;

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

            double dist = Math.sqrt(Math.pow((x - juego.x), 2) + Math.pow((y - juego.y), 2));

            if (chequearColision == true) {
                if (dist < radio + juego.radio) {
                    una_vez = true;
                    juego.golpe_enemigo++;
                    moverse = false;
                }
            }

            double distPlanetaEspiritu = Math.sqrt(Math.pow((x - juego.colisionEspirituPlanetaX), 2) + Math.pow((y - juego.colisionEspirituPlanetaY), 2));

            if (distPlanetaEspiritu < (radio + juego.otroRadio) - 20) {
                juego.destruccion_planeta = true;
            }
        }

        if (juego.capitulo2 == true) {

            if (primer_mov_cap2 == true) {
                if (una_vez == true) {
                    x = 0 - (ancho / 2);
                    y = juego.y;
                    una_vez = false;
                }

                magicoX = ancho / 2;
                magicoY = juego.y;

                distX = (magicoX - x) / speed;
                distY = (magicoY - y) / speed;

                x = x + distX;
                y = y + distY;

                if (distX <= 0 && distX <= 0) {
                    primer_mov_cap2 = false;
                    una_vez = true;
                    segundo_mov_cap2 = true;
                }
            }

            if (segundo_mov_cap2 == true) {
                if (una_vez == true) {
                    Random random = new Random();
                    float min = alto / 2;
                    float max = canvas.getHeight() - alto - alto / 2;
                    aleatorio = min + random.nextFloat() * (max - min);
                    una_vez = false;
                }

                magicoY = (int) aleatorio;
                distY = (magicoY - y) / speed;
                y = y + distY;
                if (distY == 0) {
                    una_vez = true;
                }

                if (prueba == true) {
                    if (juego.contador_espiritu_energia == 3 && contador_en_cuatro == true){
                        segundo_mov_cap2 = false;
                        tercer_mov_cap2 = true;
                        prueba = false;
                        juego.mi_cuenta_atras.cancel();
                        juego.cuenta_espiritu_energia.cancel();
                        contador_en_cuatro = false;

                    } else if (juego.contador_espiritu_energia == 7 && contador_en_ocho == true){
                        segundo_mov_cap2 = false;
                        tercer_mov_cap2 = true;
                        prueba = false;
                        juego.mi_cuenta_atras.cancel();
                        juego.cuenta_espiritu_energia.cancel();
                        contador_en_ocho = false;

                    } else if (juego.contador_espiritu_energia == 10 && contador_en_diez == true){
                        segundo_mov_cap2 = false;
                        tercer_mov_cap2 = true;
                        prueba = false;
                        juego.mi_cuenta_atras.cancel();
                        juego.cuenta_espiritu_energia.cancel();
                        contador_en_diez = false;
                    }
                }
            }

            if (tercer_mov_cap2 == true) {
                if (una_vez2 == true) {
                    Posinicial = y - 50;
                    Posfinal = y + 50;
                    distY = (Posinicial - y) / speed;

                    una_vez2 = false;
                }

                if (y <= Posinicial) {
                    distY = (Posfinal - y) / speed;
                }

                if (y >= Posfinal) {
                    distY = (Posinicial - y) / speed;
                }

                y = y + distY;

                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        if (juego.contador_espiritu_energia == 3) {
                            segundo_mov_cap2 = true;
                            tercer_mov_cap2 = false;
                            if (una_vez_cuatro == true) {
                                juego.sumarAsteroides();
                                juego.sumarEspirituEnergia();
                                una_vez_cuatro = false;
                                prueba = true;
                            }

                        } else if (juego.contador_espiritu_energia == 7){
                            segundo_mov_cap2 = true;
                            tercer_mov_cap2 = false;
                            if (una_vez_ocho == true) {
                                juego.LluviaDeAsteroides();
                                juego.sumarEspirituEnergia();
                                una_vez_ocho = false;
                            }

                        } else if (juego.contador_espiritu_energia == 10){
                            if (una_vez_diez == true) {
                                segundo_mov_cap2 = true;
                                una_vez_diez = false;
                                prueba = true;
                                juego.LluviaDeAsteroides();
                            }
                        }
                    }
                }.start();
            }

            if (juego.casi_fin_del_cap2 == true){
                segundo_mov_cap2 = false;
                tercer_mov_cap2 = false;
                if (una_vez_fin_cap2 == true){
                    xfinal = -100;
                    distX = (xfinal - x)/speed;
                    una_vez_fin_cap2 = false;
                }

                x = x + distX;
            }

            src = new Rect(srcX, srcY, srcX + ancho, srcY + alto);
            dst = new Rect(x, y, x + ancho, y + alto);
            canvas.drawBitmap(personaje, src, dst, null);
            paint.setColor(Color.TRANSPARENT);
            canvas.drawCircle(x + (ancho / 2), y + (alto / 2), radio, paint);
        }
    }

    public void contar() {

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                dibujar = true;
            }

            public void onFinish() {
                if (juego.cantidad_de_asteroides >= 10 && juego.cantidad_de_asteroides <= 20) {
                    dibujar = false;
                    segundaCuenta();

                } else moverse = true;
            }
        }.start();
    }

    public void segundaCuenta(){

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                una_vez = true;
            }
        }.start();
    }
}