package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.julen.starwarsinvaders.R;

public class NibelaActivity extends Activity {

    ImageButton atzera;
    Button lvl1,lvl2,lvl3,lvl4,lvl5,lvl6;
    public String mugimenduAukera = "";
    public String nibela;
    Intent i;
    String partidaMota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nibela);

        this.atzera = (ImageButton) findViewById(R.id.atzera2);
        this.lvl1 = (Button) findViewById(R.id.lvl1);
        this.lvl2 = (Button) findViewById(R.id.lvl2);
        this.lvl3 = (Button) findViewById(R.id.lvl3);
        this.lvl4 = (Button) findViewById(R.id.lvl4);
        this.lvl5 = (Button) findViewById(R.id.lvl5);
        this.lvl6 = (Button) findViewById(R.id.lvl6);

        i = getIntent();
        mugimenduAukera = i.getStringExtra("AUKERA");
        partidaMota=i.getStringExtra("MOTA");

        atzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nibela = "lvl1";
                Intent intent = new Intent(NibelaActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", nibela);
                intent.putExtra("MOTA", partidaMota);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        this.lvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nibela = "lvl2";
                Intent intent = new Intent(NibelaActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", nibela);
                intent.putExtra("MOTA", partidaMota);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        this.lvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nibela = "lvl3";
                Intent intent = new Intent(NibelaActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", nibela);
                intent.putExtra("MOTA", partidaMota);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        this.lvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nibela = "lvl4";
                Intent intent = new Intent(NibelaActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", nibela);
                intent.putExtra("MOTA", partidaMota);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        this.lvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nibela = "lvl5";
                Intent intent = new Intent(NibelaActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", nibela);
                intent.putExtra("MOTA", partidaMota);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        this.lvl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nibela = "lvl6";
                Intent intent = new Intent(NibelaActivity.this, LoadingActivity.class);
                intent.putExtra("AUKERA", mugimenduAukera);
                intent.putExtra("NIBELA", nibela);
                intent.putExtra("MOTA", partidaMota);
                startActivityForResult(intent, 1);
                finish();
            }
        });
    }
}
