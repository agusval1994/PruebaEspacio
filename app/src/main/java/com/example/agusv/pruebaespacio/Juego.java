package com.example.agusv.pruebaespacio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by agusv on 19/2/2018.
 */

public class Juego extends SurfaceView implements View.OnTouchListener {

    //Declarar Clases

    Estrellas estrellas;
    Espiritu espiritu;
    Espiritu_Enemigo enemigo;
    DestruccionPlaneta destruccionPlaneta;
    OtroPlaneta otroPlaneta;
    Planeta planeta;
    Asteroides asteroides;
    EspirituEnergia espirituEnergia;
    MuerteEspiritu muerteEspiritu;


    //Imagenes

    Bitmap imagen_planeta, imagen_asteroides, imagen_espiritu, imagen_destruccion,
            imagen_enemigo, imagen_destruccion_planeta, imagen_otro_planeta, imagen_espiritu_sin_brillo,
            imagen_espiritu_energia, imagen_espiritu_en_transicion, imagen_espiritu_energia_sin_brillo,
            imagen_muerte_espiritu;


    //Int

    int x, y, radio, distXX, distYY, radioplaneta, otroX, otrY, otroRadio = 130, golpe_enemigo = 0, radio_final = 10,
            speed = 10, magicoX, magicoY, tocoX, tocoY, cant_estrellas = 30, cantidad_de_asteroides = 0,
            xparaColision, yparaColision, progreso_historia, x1, x2, y1, y2, distXX1, distXX2, distYY1, distYY2,
            xinicial1, xinicial2, yinicial1, yinicial2, i, colisionEspirituPlanetaX, colisionEspirituPlanetaY,
            contador_prueba, contador_espiritu_energia, cant_espi = 10;


    //Booleanos

    boolean prologo = false;
    boolean capitulo1 = false;
    boolean capitulo2 = false;
    boolean capitulo3 = false;
    boolean jugando = false;
    boolean una_vez = true;
    boolean una_vez2 = true;
    boolean movimiento_de_estrellas = false;
    boolean me_puedo_mover = true;
    boolean mover = false;
    boolean mov_prologo = false;
    boolean muere_prologo = false;
    boolean colisionTruchaEspirituPlaneta = true;
    boolean finDeJuego = false;
    boolean destruccion_planeta = false;
    boolean posicion1 = false, posicion2 = false, posicion_final = false;
    boolean una_vez_prueba = true;
    boolean primer_efecto = false, segundo_efecto = false, tercer_efecto = false, asteroides_prologo = true;
    boolean primera_vez = true;
    boolean una_vez_prologo = true;
    boolean una_vez_capitulo1 = true;
    boolean una_vez_capitulo2 = true;
    boolean una_vez_capitulo3 = true;
    boolean capitulo2_pierde = false;
    boolean casi_fin_del_cap2 = false;
    boolean tirameEseFinalPapa = false;
    boolean mostrar_espiritu_fin_cap2 = false;
    boolean final_del_capitulo2 = false;


    //Otros

    Paint paint;
    CountDownTimer mi_cuenta_atras, cuenta_crear_y_eliminar_estrellas, segunda_cuenta, cuenta_espiritu_energia,
            cuenta_lluvia_de_asteroides;
    List<Estrellas> estrellitas = new ArrayList<Estrellas>();
    List<Asteroides> lista_asteroides = new ArrayList<Asteroides>();
    List<EspirituEnergia> lista_espiritu_energia = new ArrayList<EspirituEnergia>();
    List<MuerteEspiritu> lista_muerte_espiritu = new ArrayList<MuerteEspiritu>();
    long cuanto_cuento, tiempo_espiritu_energia;


    public Juego(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        setFocusable(true);

        estrellas = new Estrellas(this);
        paint = new Paint();

        Drawable imagen_planeta_drawable = context.getResources().getDrawable(R.drawable.planeta_sprite_sheet2);
        Drawable imagen_asteroides_drawable = context.getResources().getDrawable(R.drawable.asteroide);
        Drawable imagen_espiritu_drawable = context.getResources().getDrawable(R.drawable.espiritu_sprite_sheet);
        Drawable imagen_destruccion_drawable = context.getResources().getDrawable(R.drawable.destruccion_spritesheet);
        Drawable imagen_enemigo_drawable = context.getResources().getDrawable(R.drawable.espiritu_enemigo_spritesheet);
        Drawable imagen_destruccion_planeta_drawable = context.getResources().getDrawable(R.drawable.destruccion_planeta_spritesheet);
        Drawable imagen_otro_planeta_drawable = context.getResources().getDrawable(R.drawable.planeta_espiritu_spritesheet);
        Drawable imagen_espiritu_sin_brillo_drawable = context.getResources().getDrawable(R.drawable.espiritu_sin_brillo_spritesheet);
        Drawable imagen_espiritu_energia_drawable = context.getResources().getDrawable(R.drawable.espiritu_sprite_sheet);
        Drawable imagen_espiritu_en_transicion_drawable = context.getResources().getDrawable(R.drawable.transcicion_spritesheet);
        Drawable imagen_muerte_espiritu_drawable = context.getResources().getDrawable(R.drawable.espiritu_sin_brillo_spritesheet);


        imagen_planeta = ((BitmapDrawable) imagen_planeta_drawable).getBitmap();
        planeta = new Planeta(this, imagen_planeta);

        imagen_asteroides = ((BitmapDrawable) imagen_asteroides_drawable).getBitmap();
        imagen_asteroides = Bitmap.createScaledBitmap(imagen_asteroides, 200, 200, false);

        imagen_espiritu = ((BitmapDrawable) imagen_espiritu_drawable).getBitmap();
        imagen_espiritu_sin_brillo = ((BitmapDrawable) imagen_espiritu_sin_brillo_drawable).getBitmap();
        imagen_espiritu_en_transicion = ((BitmapDrawable) imagen_espiritu_en_transicion_drawable).getBitmap();
        //200 de heght y es 1 sola imagen en horizontal, entonces si tengo 8 imagenes son 200x8
        imagen_espiritu = Bitmap.createScaledBitmap(imagen_espiritu, 1600, 200, false);
        imagen_espiritu_sin_brillo = Bitmap.createScaledBitmap(imagen_espiritu_sin_brillo, 1600, 200, false);
        imagen_espiritu_en_transicion = Bitmap.createScaledBitmap(imagen_espiritu_en_transicion, 1600, 200, false);
        espiritu = new Espiritu(this, imagen_espiritu, imagen_espiritu_sin_brillo, imagen_espiritu_en_transicion);

        imagen_destruccion = ((BitmapDrawable) imagen_destruccion_drawable).getBitmap();
        imagen_destruccion = Bitmap.createScaledBitmap(imagen_destruccion, 400, 200, false);
        asteroides = new Asteroides(this, imagen_asteroides, imagen_destruccion);

        imagen_enemigo = ((BitmapDrawable) imagen_enemigo_drawable).getBitmap();
        imagen_enemigo = Bitmap.createScaledBitmap(imagen_enemigo, 1600, 200, false);
        enemigo = new Espiritu_Enemigo(this, imagen_enemigo);

        imagen_destruccion_planeta = ((BitmapDrawable) imagen_destruccion_planeta_drawable).getBitmap();
        imagen_destruccion_planeta = Bitmap.createScaledBitmap(imagen_destruccion_planeta, 2200, 1600, false);
        destruccionPlaneta = new DestruccionPlaneta(this, imagen_destruccion_planeta);

        imagen_otro_planeta = ((BitmapDrawable) imagen_otro_planeta_drawable).getBitmap();
        otroPlaneta = new OtroPlaneta(this, imagen_otro_planeta);

        imagen_espiritu_energia = ((BitmapDrawable) imagen_espiritu_energia_drawable).getBitmap();
        imagen_espiritu_energia = Bitmap.createScaledBitmap(imagen_espiritu_energia, 800, 100, false);
        espirituEnergia = new EspirituEnergia(this, imagen_espiritu_energia);

        imagen_muerte_espiritu = ((BitmapDrawable) imagen_muerte_espiritu_drawable).getBitmap();
        imagen_muerte_espiritu = Bitmap.createScaledBitmap(imagen_muerte_espiritu, 800, 100, false);
        muerteEspiritu = new MuerteEspiritu(this, imagen_muerte_espiritu);

        SharedPreferences datos = context.getSharedPreferences("Datos", MODE_PRIVATE);
        progreso_historia = datos.getInt("record", 0);

        dibujarEstrellas();
        crear_y_eliminar_estrellas();
    }

    public void onDraw(Canvas canvas) {

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        for (Estrellas estrellas : estrellitas) {
            estrellas.onDraw(canvas);
        }

        for (Asteroides ast : lista_asteroides) {
            ast.onDraw(canvas);
        }

        enemigo.onDraw(canvas);

        progreso_historia = 2;
        if (progreso_historia == 1){
            if (primera_vez == true){
                planeta.onDraw(canvas);
            }
        }

        if (prologo == true) {
            if (mover == true) {
                cuenta_crear_y_eliminar_estrellas.cancel();

                if (una_vez == true) {
                    efecto_movimiento();
                    una_vez = false;
                }

                magicoX = canvas.getWidth() - espiritu.ancho;
                magicoY = canvas.getHeight() - espiritu.alto;

                distXX = (tocoX - espiritu.x) / speed;
                distYY = (tocoY - espiritu.y) / speed;

                espiritu.x = (espiritu.x + distXX);
                espiritu.y = (espiritu.y + distYY);

                if (posicion2 == true) {

                    if (espiritu.x <= canvas.getWidth() / 2) {
                        espiritu.x = canvas.getWidth() / 2;
                    }

                    if (asteroides_prologo == true) {
                        sumarAsteroides();
                        asteroides_prologo = false;
                    }

                } else if (espiritu.x <= 0) {
                    espiritu.x = 0;
                }

                if (espiritu.x >= magicoX) {
                    espiritu.x = magicoX;
                }

                if (espiritu.y <= 0) {
                    espiritu.y = 0;
                }

                if (espiritu.y >= magicoY) {
                    espiritu.y = magicoY;
                }
            }

            if (posicion1 == true || posicion2 == true) {
                enemigo.onDraw(canvas);
            }
        }

        if (posicion1 == true) {
            if (destruccion_planeta == false) {
                otroPlaneta.onDraw(canvas);
            }
            tocoX = espiritu.x;
            tocoY = espiritu.y;
            if (una_vez_prueba == true) {
                xinicial1 = 0 - 200;
                xinicial2 = 0 - 200;
                yinicial1 = canvas.getHeight() / 2 - 600;
                yinicial2 = canvas.getHeight() / 2 + 400;

                x1 = (canvas.getWidth() / 2) - 100;
                x2 = 400;
                una_vez_prueba = false;
            }

            if (muere_prologo == false) {
                canvas.drawBitmap(imagen_asteroides, xinicial1, yinicial1, paint);
                canvas.drawBitmap(imagen_asteroides, xinicial1, yinicial2, paint);
                canvas.drawBitmap(imagen_asteroides, xinicial2, yinicial1, paint);
                canvas.drawBitmap(imagen_asteroides, xinicial2, yinicial2, paint);

                if (mov_prologo == false) {
                    distXX = (x1 - xinicial1) / speed;
                    distXX2 = (x2 - xinicial2) / speed;

                    xinicial1 = xinicial1 + distXX;
                    xinicial2 = xinicial2 + distXX2;
                }

                final int posX = (canvas.getWidth() / 2 - 540);
                final int posY = (canvas.getHeight() / 2) - 50;

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        mov_prologo = true;
                    }
                }.start();

                if (mov_prologo == true) {
                    distXX1 = (posX - xinicial1) / speed;
                    distXX2 = (posX - xinicial2) / speed;
                    distYY1 = (posY - yinicial1) / speed;
                    distYY2 = (posY - yinicial2) / speed;

                    xinicial1 = (xinicial1 + distXX1);
                    xinicial2 = (xinicial2 + distXX2);
                    yinicial1 = (yinicial1 + distYY1);
                    yinicial2 = (yinicial2 + distYY2);

                    if (distXX1 <= 5 && distYY1 <= 5 || distXX2 <= 5 && distYY2 <= 5) {
                        destruccion_planeta = true;
                        muere_prologo = true;
                    }
                }
            }

            if (destruccion_planeta == true) {
                destruccionPlaneta.onDraw(canvas);
            }
        }

        if (posicion_final == true) {
            planeta.onDraw(canvas);
            finDeJuego = true;
        }

        espiritu.onDraw(canvas);

        if (capitulo1 == true) {

            if (destruccion_planeta == false) {
                planeta.onDraw(canvas);
            }

            if (jugando == true) {

                for (Asteroides ast : lista_asteroides) {
                    ast.onDraw(canvas);
                }

                if (cantidad_de_asteroides >= 10) {
                    enemigo.onDraw(canvas);
                }

                if (una_vez == true) {
                    otroX = canvas.getWidth() / 2;
                    otrY = canvas.getHeight() / 2;
                    colisionEspirituPlanetaX = canvas.getWidth() / 2 - 100;
                    colisionEspirituPlanetaY = canvas.getHeight() / 2 - 100;
                    radioplaneta = 130;
                    una_vez = false;
                }

                if (mover == true) {

                    magicoX = canvas.getWidth() - espiritu.ancho;
                    magicoY = canvas.getHeight() - espiritu.alto;

                    distXX = (tocoX - espiritu.x) / speed;
                    distYY = (tocoY - espiritu.y) / speed;

                    espiritu.x = (espiritu.x + distXX);
                    espiritu.y = (espiritu.y + distYY);

                    if (espiritu.x <= 0) {
                        espiritu.x = 0;
                    }

                    if (espiritu.x >= magicoX) {
                        espiritu.x = magicoX;
                    }

                    if (espiritu.y <= 0) {
                        espiritu.y = 0;
                    }

                    if (espiritu.y >= magicoY) {
                        espiritu.y = magicoY;
                    }
                }

                if (colisionTruchaEspirituPlaneta == true) {
                    xparaColision = otroX - 100;
                    yparaColision = otrY - 100;
                    colisionTruchaEspirituPlaneta = false;
                }

                double dist = Math.sqrt(Math.pow((espiritu.x - xparaColision), 2) + Math.pow((espiritu.y - yparaColision), 2));

                if (dist <= radio + radioplaneta) {
                    // This calculate the displacement of a the distance 'radius*2' on the line between the two circles centers.
                    double angle = Math.atan2(espiritu.y - yparaColision, espiritu.x - xparaColision);
                    int moveX = (int) ((radio + radioplaneta) * Math.cos(angle));
                    int moveY = (int) ((radio + radioplaneta) * Math.sin(angle));
                    // Then we need to add the displacement to the coordinates of the origin to have the new position.
                    espiritu.x = xparaColision + moveX;
                    espiritu.y = yparaColision + moveY;
                }

                x = espiritu.x;
                y = espiritu.y;
                radio = espiritu.radio;
            }

            if (golpe_enemigo >= 3) {
                paint.setColor(Color.WHITE);
                canvas.drawCircle(x, y, radio_final, paint);
                radio_final = radio_final + 50;
                jugando = false;
            }

            if (una_vez2 == true) {
                x = canvas.getWidth() / 2;
                otrY = canvas.getHeight() / 2;
                una_vez2 = false;
            }

            paint.setColor(Color.TRANSPARENT);
            canvas.drawCircle(otroX, otrY, otroRadio, paint);

            if (radio_final > canvas.getWidth() && radio_final > canvas.getHeight()) {
                finDelJuego();
            }

            if (finDeJuego == true) {
                finDelJuego();
            }

            if (destruccion_planeta == true) {
                destruccionPlaneta.onDraw(canvas);
                jugando = false;
            }
        }

        if (capitulo2 == true) {

            enemigo.onDraw(canvas);

            cuenta_crear_y_eliminar_estrellas.cancel();

            for (Asteroides ast : lista_asteroides) {
                ast.onDraw(canvas);
            }

            for (EspirituEnergia espiriEnergia : lista_espiritu_energia) {
                espiriEnergia.onDraw(canvas);
            }

            for (MuerteEspiritu muerteEspiritu : lista_muerte_espiritu) {
                muerteEspiritu.onDraw(canvas);
            }

            if (una_vez == true) {
                efecto_movimiento();
                sumarEspirituEnergia();
                sumarAsteroides();
                una_vez = false;
            }

            if (mover == true) {

                magicoX = canvas.getWidth() - espiritu.ancho;
                int mitadX = canvas.getWidth() / 2 - espiritu.ancho;
                magicoY = canvas.getHeight() - espiritu.alto;

                distXX = (tocoX - espiritu.x) / speed;
                distYY = (tocoY - espiritu.y) / speed;

                espiritu.x = (espiritu.x + distXX);
                espiritu.y = (espiritu.y + distYY);

                if (espiritu.x <= mitadX) {
                    espiritu.x = mitadX;
                }

                if (espiritu.x >= magicoX) {
                    espiritu.x = magicoX;
                }

                if (espiritu.y <= 0) {
                    espiritu.y = 0;
                }

                if (espiritu.y >= magicoY) {
                    espiritu.y = magicoY;
                }
            }

            if (tirameEseFinalPapa == true) {
                finDelCapitulo2();
                tirameEseFinalPapa = false;
            }

            if (mostrar_espiritu_fin_cap2 == true){
                espiritu.una_vez = true;
            }

            if (final_del_capitulo2 == true){
                finDelCapitulo2();
            }
        }

        x = espiritu.x;
        y = espiritu.y;
        radio = espiritu.radio;

        /*String texto = String.valueOf(contador_espiritu_energia);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText(texto, canvas.getWidth()/2, 50, paint);

        paint.setStrokeWidth(10);
        int uno = (canvas.getWidth()/2)/3;
        int dos = canvas.getWidth()/2;
        canvas.drawLine(uno+dos,0, uno+dos, canvas.getHeight(), paint);
        canvas.drawLine(uno+dos+uno,0, uno+dos+uno, canvas.getHeight(), paint);*/

        invalidate();
    }

    public void muerteDeEspiritu(){
        for (i = 0; i < cant_espi; i++) {
            lista_muerte_espiritu.add(crearMuerteDeEspiritu());
        }
    }

    private Estrellas createEstrellas() {
        return new Estrellas(this);
    }

    private Asteroides createAsteroides() {
        return new Asteroides(this, imagen_asteroides, imagen_destruccion);
    }

    private EspirituEnergia createEspirituEnergia() {
        return new EspirituEnergia(this, imagen_espiritu_energia);
    }

    private MuerteEspiritu crearMuerteDeEspiritu() {
        return new MuerteEspiritu(this, imagen_muerte_espiritu);
    }

    public void sumarAsteroides() {

        mi_cuenta_atras = new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                if (capitulo1 == true) {
                    cantidad_de_asteroides++;
                }

                if (capitulo2 == true){
                    lista_asteroides.add(createAsteroides());
                }
                sumarAsteroides();
            }
        }.start();
    }

    public void sumarEspirituEnergia(){

        Random random = new Random();
        int tiempo = random.nextInt(4);
        if (tiempo == 0){
            tiempo_espiritu_energia = 6000;
        } else if (tiempo == 1){
            tiempo_espiritu_energia = 7000;
        } else if (tiempo == 2){
            tiempo_espiritu_energia = 8000;
        } else if (tiempo == 3){
            tiempo_espiritu_energia = 9000;
        }

        cuenta_espiritu_energia = new CountDownTimer(tiempo_espiritu_energia, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                lista_espiritu_energia.add(createEspirituEnergia());
                sumarEspirituEnergia();
            }
        }.start();
    }

    public void LluviaDeAsteroides() {

        if (contador_espiritu_energia == 10) {
            mi_cuenta_atras = new CountDownTimer(5000, 10) {

                public void onTick(long millisUntilFinished) {
                    lista_asteroides.add(createAsteroides());
                }

                public void onFinish() {
                }
            }.start();

        } else if (contador_espiritu_energia != 10) {

            mi_cuenta_atras = new CountDownTimer(500, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {

                    lista_asteroides.add(createAsteroides());

                    LluviaDeAsteroides();
                }
            }.start();
        }
    }

    public void dibujarEstrellas() {

        for (i = 0; i < cant_estrellas; i++) {
            estrellitas.add(createEstrellas());
        }
    }

    public void crear_y_eliminar_estrellas() {

        cuenta_crear_y_eliminar_estrellas = new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (i == cant_estrellas) {
                    Random random = new Random();
                    Estrellas elementoRandom = estrellitas.get(random.nextInt(estrellitas.size()));
                    estrellitas.remove(elementoRandom);
                }
                estrellitas.add(createEstrellas());
                crear_y_eliminar_estrellas();
            }
        }.start();
    }

    public void efecto_movimiento() {

        if (primer_efecto == true){
            cuanto_cuento = 10000;
        } else if (segundo_efecto == true){
            cuanto_cuento = 20000;
        } else if (capitulo2 == true){
            cuanto_cuento = 5000;
        }

        segunda_cuenta = new CountDownTimer(cuanto_cuento, 500) {

            public void onTick(long millisUntilFinished) {

                estrellitas.add(createEstrellas());
                estrellitas.add(createEstrellas());

                if (primer_efecto == true) {
                    if (millisUntilFinished <= 1000) {
                        me_puedo_mover = false;
                        posicion1 = true;
                        mover = false;
                    }
                }

                if (segundo_efecto == true) {
                    if (millisUntilFinished <= 5000) {
                        posicion2 = false;
                        mi_cuenta_atras.cancel();

                    }
                    if (millisUntilFinished <= 2000) {
                        posicion_final = true;
                    }
                }
            }

            public void onFinish() {
                if (prologo == true) {
                    movimiento_de_estrellas = false;
                    me_puedo_mover = false;
                    mover = false;
                    crear_y_eliminar_estrellas();
                    if (posicion_final == true) {
                        progreso_historia++;
                        finDelPrologo();
                    }
                }

                if (capitulo2 == true){
                    efecto_movimiento();
                }
            }
        }.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (prologo == true){
            if (me_puedo_mover == true) {

                tocoX = (int) motionEvent.getX() - espiritu.ancho / 2;
                tocoY = (int) motionEvent.getY() - espiritu.alto / 2;

                mover = true;
                movimiento_de_estrellas = true;
            }
        }

        if (capitulo1 == true) {
            if (jugando == true) {

                tocoX = (int) motionEvent.getX() - espiritu.ancho / 2;
                tocoY = (int) motionEvent.getY() - espiritu.alto / 2;

                mover = true;
            }
        }

        if (capitulo2 == true){
            if (jugando == true){

                tocoX = (int) motionEvent.getX() - espiritu.ancho / 2;
                tocoY = (int) motionEvent.getY() - espiritu.alto / 2;

                movimiento_de_estrellas = true;
                mover = true;
            }
        }

        invalidate();
        return true;
    }

    public void finDelJuego() {
        mi_cuenta_atras.cancel();
        capitulo1 = false;
        jugando = false;
        espiritu.una_vez = true;
        lista_asteroides.clear();
        cantidad_de_asteroides = 0;
        golpe_enemigo = 0;
        radio_final = 10;
        tocoX = 1190;
        tocoY = 894;
        mi_cuenta_atras.cancel();
        destruccion_planeta = false;
        destruccionPlaneta.currentFrame = 0;
        destruccionPlaneta.currentFrame2 = 0;
        finDeJuego = true;
    }

    public void finDelPrologo(){
        prologo = false;
        una_vez = true;
        una_vez2 = true;
        una_vez_prueba = true;
        posicion1 = false;
        posicion2 = false;
        me_puedo_mover = true;
        mover = false;
        primer_efecto = true;
        segundo_efecto = false;
        mov_prologo = false;
        muere_prologo = false;
        movimiento_de_estrellas = false;
        destruccion_planeta = false;
        asteroides_prologo = true;

        destruccionPlaneta.currentFrame = 0;
        destruccionPlaneta.currentFrame2 = 0;
    }

    public void finDelCapitulo2(){
        lista_asteroides.clear();
        mi_cuenta_atras.cancel();
        lista_espiritu_energia.clear();
        cuenta_espiritu_energia.cancel();

        if (final_del_capitulo2 == true) {

            lista_muerte_espiritu.clear();
            capitulo2_pierde = false;
            casi_fin_del_cap2 = false;
            tirameEseFinalPapa = false;
            mostrar_espiritu_fin_cap2 = false;
            capitulo2 = false;
            jugando = false;
            una_vez = true;
            mover = false;
            tirameEseFinalPapa = false;
            mostrar_espiritu_fin_cap2 = false;
            final_del_capitulo2 = false;
            enemigo.primer_mov_cap2 = true;
            enemigo.segundo_mov_cap2 = false;
            enemigo.tercer_mov_cap2 = false;
            enemigo.una_vez2 = true;
            enemigo.una_vez_cuatro = true;
            enemigo.una_vez_ocho = true;
            enemigo.una_vez_diez = true;
            enemigo.contador_en_cuatro = true;
            enemigo.contador_en_ocho = true;
            enemigo.contador_en_diez = true;
            enemigo.prueba = true;
            enemigo.una_vez_fin_cap2 = true;
        }
    }
}
