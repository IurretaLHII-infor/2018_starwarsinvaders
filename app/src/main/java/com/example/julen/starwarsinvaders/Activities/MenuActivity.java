package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.julen.starwarsinvaders.R;

public class MenuActivity extends Activity {


    Button Irten, campaña, rapida, scores;
    ImageButton help;
    ImageView logo;
    MediaPlayer menuMP = new MediaPlayer();
    MediaPlayer botonMP = new MediaPlayer();
    Intent intent;
    Switch aukera;
    public String mugimenduAukera = "sensor";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        this.Irten = (Button) findViewById(R.id.irten);
        this.rapida = (Button) findViewById(R.id.partidaRapida);
        this.campaña = (Button) findViewById(R.id.campaina);
        this.logo = (ImageView) findViewById(R.id.imageView);
        this.aukera = (Switch) findViewById(R.id.sensor);
        this.help = (ImageButton) findViewById(R.id.help);
        this.scores = (Button) findViewById(R.id.scores);

        botonMP = MediaPlayer.create(this, R.raw.disparolaser);
        menuMP = MediaPlayer.create(MenuActivity.this, R.raw.cantinasong);
        menuMP.start();


        if (aukera.isChecked()) {
            aukera.setBackgroundColor(Color.RED);
        } else {
            aukera.setBackgroundColor(Color.YELLOW);
        }
        aukera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mugimenduAukera = "sensor";
                    aukera.setBackgroundColor(Color.RED);
                    b = false;

                } else {
                    aukera.setBackgroundColor(Color.YELLOW);
                    mugimenduAukera = "tactil";
                    b = true;

                }
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuMP.isPlaying()) {
                    menuMP.pause();
                } else {
                    menuMP.start();
                }
            }
        });

        Irten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMP.pause();
                Intent intent;
                intent = new Intent(MenuActivity.this, IntroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit me", true);
                startActivity(intent);
                finish();

            }
        });

        rapida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, NibelaActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("MOTA", "rapida");
                startActivity(intent);
                menuMP.stop();
                botonMP.start();
            }
        });

        campaña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", "lvl1");
                intent.putExtra("MOTA", "historia");
                startActivityForResult(intent, 1);
                menuMP.stop();
                botonMP.start();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(MenuActivity.this, LaguntzaActivity.class);
               startActivity(i);

            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, QueryScoreActivity.class);
                startActivity(i);
            }
        });


    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            System.exit(0);
        }

    }
}
