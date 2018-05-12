package com.example.agusv.pruebaespacio;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by agusv on 20/2/2018.
 */

public class Espiritu {

    private Juego juego;
    private Bitmap personaje, espiritu_sin_brillo, espiritu_en_transicion;
    Paint paint;


    //Int
    int x = 0, y = 0, radio = 50, magico = 0, distX, distY, speed = 20, magicoX, magicoY;


    //Boolean
    boolean una_vez = true;


    //Animacion
    int ancho, alto;
    int ancho_espiritu_sin_brillo, alto_espiritu_sin_brillo, alto_espiritu_en_transicion, ancho_espiritu_en_transicion;
    int currentFrame = 0;
    private static final int Horizontal = 1;
    private static final int Vertical = 8;
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



    public Espiritu(Juego juego, Bitmap personaje, Bitmap espiritu_sin_brillo, Bitmap espiritu_en_transicion){

        this.juego = juego;
        this.personaje = personaje;
        this.ancho = personaje.getWidth() / Vertical;
        this.alto = personaje.getHeight() / Horizontal;

        this.espiritu_sin_brillo = espiritu_sin_brillo;
        this.ancho_espiritu_sin_brillo = espiritu_sin_brillo.getWidth() / Vertical;
        this.alto_espiritu_sin_brillo = espiritu_sin_brillo.getHeight() / Horizontal;

        this.espiritu_en_transicion = espiritu_en_transicion;
        this.ancho_espiritu_en_transicion = espiritu_en_transicion.getWidth() / Vertical;
        this.alto_espiritu_en_transicion = espiritu_en_transicion.getHeight() / Horizontal;

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
        if (juego.progreso_historia == 0){
            update();
            if (una_vez == true) {
                x = 1190;
                y = canvas.getHeight() / 2 + alto / 2;
                una_vez = false;
            }
        } else if (juego.progreso_historia == 1) {
            update();
            if (una_vez == true) {
                x = 1190;
                y = 894;
                una_vez = false;
            }
        } else if (juego.progreso_historia == 2){
            update();
            if (una_vez == true) {
                x = 1190;
                y = canvas.getHeight()/2;
                una_vez = false;
            }
        }

        if (juego.mostrar_espiritu_fin_cap2 == true){
            x = canvas.getWidth()/2 - ancho/2;
            y = canvas.getHeight()/2 + alto/2;
        }

        Rect dst = new Rect(x, y, x + ancho, y + alto);

        if (juego.progreso_historia == 2){
            if (juego.casi_fin_del_cap2 == false) {
                if (juego.contador_espiritu_energia >= 7 && juego.contador_espiritu_energia <= 9) {
                    canvas.drawBitmap(espiritu_en_transicion, src, dst, null);
                } else if (juego.contador_espiritu_energia == 10) {
                    canvas.drawBitmap(personaje, src, dst, null);
                } else canvas.drawBitmap(espiritu_sin_brillo, src, dst, null);
            }
        } else canvas.drawBitmap(personaje, src, dst, null);

        paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(x + (ancho/2), y + (alto/2), radio, paint);

        if (juego.posicion1 == true){

            magicoX = canvas.getWidth() - ancho;
            magicoY = canvas.getHeight()/2 - alto/2;

            distX = (magicoX - x) / speed;
            distY = (magicoY - y) / speed;

            x = x + distX;
            y = y + distY;
        }

        if (juego.posicion_final == true){
            magicoX = 1140;
            magicoY = 894;

            distX = (magicoX - x) / speed;
            distY = (magicoY - y) / speed;

            x = x + distX;
            y = y + distY;
        }
    }
}
