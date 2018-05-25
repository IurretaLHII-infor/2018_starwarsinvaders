package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.julen.starwarsinvaders.R;

public class LoadingActivity extends Activity {

    String aukera, nibela, mota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent intent = getIntent();
        aukera = intent.getStringExtra("AUKERA");
        nibela = intent.getStringExtra("NIBELA");
        mota=intent.getStringExtra("MOTA");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoadingActivity.this, StarWarsActivity.class);
                i.putExtra("AUKERA", aukera);
                i.putExtra("NIBELA", nibela);
                i.putExtra("MOTA", mota);
                i.putExtra("pasarNivel", true);

                startActivity(i);
                finish();
            }
        },100);
    }
}
