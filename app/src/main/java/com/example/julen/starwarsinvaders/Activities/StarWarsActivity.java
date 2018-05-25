package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.example.julen.starwarsinvaders.Levels.Level1View;
import com.example.julen.starwarsinvaders.Levels.Level2View;
import com.example.julen.starwarsinvaders.Levels.Level3View;
import com.example.julen.starwarsinvaders.Levels.Level4View;
import com.example.julen.starwarsinvaders.Levels.Level5View;
import com.example.julen.starwarsinvaders.Levels.Level6View;
import com.example.julen.starwarsinvaders.Levels.LevelView;
import com.example.julen.starwarsinvaders.R;

public class StarWarsActivity extends Activity {

    private VideoView mVideoView;
    //SENSOR gauzak deklaratu
    protected Sensor azelerometroa;
    protected SensorManager sm;
    Point size;
    Intent i;

    String mugimenduAukera;
    String nibela, partidaMota;
    String lvl = "lvl";
    int numeroNivel;
    int score = 0, lives = 3;
    LevelView levelView = null;
    Uri path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        levelView = null;
        i = getIntent();
        boolean prueba = i.getBooleanExtra("pasarNivel", false);
        if (prueba == false) {
            numeroNivel = 1;
        } else {
            numeroNivel = i.getIntExtra("NUMERO", 1);
        }
        mugimenduAukera = i.getStringExtra("AUKERA");
        nibela = i.getStringExtra("NIBELA");
        partidaMota = i.getStringExtra("MOTA");
        score = i.getIntExtra("SCORE", 0);
        lives = i.getIntExtra("LIVES", 3);
        this.sm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        this.azelerometroa = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Obtener un objeto de Display para accesar a los detalles de la pantalla
        Display display = getWindowManager().getDefaultDisplay();
        // Cargar la resolución a un objeto de Point
        size = new Point();
        display.getSize(size);

        if (nibela.compareToIgnoreCase(lvl + "1") == 0) {
            this.levelView = new Level1View(this, size.x, size.y, azelerometroa, sm, mugimenduAukera, partidaMota, score, lives, this);
        } else if (nibela.compareToIgnoreCase(lvl + "2") == 0) {
            this.levelView = new Level2View(this, size.x, size.y, azelerometroa, sm, mugimenduAukera, partidaMota, score, lives, this);
        } else if (nibela.compareToIgnoreCase(lvl + "3") == 0) {
            this.levelView = new Level3View(this, size.x, size.y, azelerometroa, sm, mugimenduAukera, partidaMota, score, lives, this);
        } else if (nibela.compareToIgnoreCase(lvl + "4") == 0) {
            this.levelView = new Level4View(this, size.x, size.y, azelerometroa, sm, mugimenduAukera, partidaMota, score, lives, this);
        } else if (nibela.compareToIgnoreCase(lvl + "5") == 0) {
            this.levelView = new Level5View(this, size.x, size.y, azelerometroa, sm, mugimenduAukera, partidaMota, score, lives, this);
        } else if (nibela.compareToIgnoreCase(lvl + "6") == 0) {
            this.levelView = new Level6View(this, size.x, size.y, azelerometroa, sm, mugimenduAukera, partidaMota, score, lives, this);
        }


        if (partidaMota.compareToIgnoreCase("historia") == 0) {

            setContentView(R.layout.activity_main);
            //carga el video de la intro en el layout
            this.mVideoView = (VideoView) findViewById(R.id.videoView_video);
            this.mVideoView.setVideoURI(null);
            this.path = null;
            if (nibela.compareToIgnoreCase("lvl1") == 0) {
                this.path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.prueba2);
            } else if (nibela.compareToIgnoreCase("lvl2") == 0) {
                this.path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.prueba2);
            } else if (nibela.compareToIgnoreCase("lvl3") == 0) {
                this.path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.prueba2);
            } else if (nibela.compareToIgnoreCase("lvl4") == 0) {
                this.path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.video_intro_menu);
            } else if (nibela.compareToIgnoreCase("lvl5") == 0) {
                this.path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.prueba2);
            } else if (nibela.compareToIgnoreCase("lvl6") == 0) {
                this.path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.prueba2);
            }
            this.mVideoView.setVideoURI(path);
            this.mVideoView.start();

            //BIDEOA AMAITZERAKOAN
            this.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    path = null;
                    mVideoView.setVideoURI(null);
                    setContentView(levelView);
                }
            });
        } else {
            setContentView(levelView);
        }

    }

    //BIDEOAN SAKATZERAKOAN MENURA JOAN
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // El jugador ha tocado la pantalla
            case MotionEvent.ACTION_DOWN:
                this.path = null;
                this.mVideoView.setVideoURI(null);
                setContentView(levelView);
                break;
            default:
                break;
        }
        return true;
    }

    public void pasarNivel(int score, int vidas) {
        numeroNivel++;
        if (vidas < 3) {
            vidas++;
        }
        nibela = lvl + Integer.toString(numeroNivel);

        Intent intent = new Intent(StarWarsActivity.this, StarWarsActivity.class);
        intent.putExtra("AUKERA", mugimenduAukera);
        intent.putExtra("NIBELA", nibela);
        intent.putExtra("NUMERO", numeroNivel);
        intent.putExtra("MOTA", "historia");
        intent.putExtra("pasarNivel", true);
        intent.putExtra("SCORE", score);
        intent.putExtra("LIVES", vidas);
        startActivity(intent);
    }

    public void guardarPuntuacion(int score) {

        Intent intent = new Intent(StarWarsActivity.this, InsertScoreActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);

    }

    public void salirMenu() {
        Intent intent = new Intent(StarWarsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    // Este método se ejecuta cuando el jugador empieza el juego
    @Override
    protected void onResume() {
        super.onResume();
        levelView.resume();
    }

    // Este método se ejecuta cuando el jugador se sale del juego
    @Override
    protected void onPause() {
        super.onPause();
        levelView.pause();
    }

    @Override
    public void onBackPressed() {

    }
}