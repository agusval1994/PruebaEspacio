package com.example.agusv.pruebaespacio;

import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    Button prologo, capitulo1, capitulo2, capitulo3;
    Juego juego;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        juego = (Juego) findViewById(R.id.juego);
        prologo = (Button)findViewById(R.id.prologo);
        capitulo1 = (Button) findViewById(R.id.capitulo1);
        capitulo2 = (Button) findViewById(R.id.capitulo2);
        //capitulo3 = (Button) findViewById(R.id.capitulo3);
        scroll = (ScrollView) findViewById(R.id.scroll);

        chequeoDeColision();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decodeView = getWindow().getDecorView();
        if (hasFocus) {
            decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    public void prologo(View view){
        juego.prologo = true;
        juego.posicion_final = false;
        scroll.setVisibility(View.GONE);
        juego.primera_vez = false;
        juego.primer_efecto = true;
    }

    public void capitulo1(View view) {
        juego.finDeJuego = false;
        juego.jugando = true;
        juego.capitulo1 = true;
        scroll.setVisibility(View.GONE);
        juego.sumarAsteroides();
        juego.primera_vez = false;
    }

    public void capitulo2(View view){
        juego.finDeJuego = false;
        juego.jugando = true;
        juego.capitulo2 = true;
        scroll.setVisibility(View.GONE);
        juego.movimiento_de_estrellas = true;
        juego.primera_vez = false;
    }

    public void chequeoDeColision() {
        new CountDownTimer(500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (juego.finDeJuego == true) {
                    scroll.setVisibility(View.VISIBLE);
                }
                chequeoDeColision();
            }
        }.start();
    }
}
