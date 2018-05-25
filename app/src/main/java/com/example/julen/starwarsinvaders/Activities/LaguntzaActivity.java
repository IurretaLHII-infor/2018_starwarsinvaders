package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.julen.starwarsinvaders.R;

public class LaguntzaActivity extends Activity {

    ImageButton atzera;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laguntza);

        this.atzera = (ImageButton) findViewById(R.id.atzera);
        this.texto = (TextView) findViewById(R.id.texto);

        texto.setText("Jolasteko modua 'sensor' bada mugikorra mugituz kontrolatzen da jokalaria." +
                "Sensor aukeran pantailaren edozein tokitan sakatuz gero tiro egiten da.\n" +
                "'tactil' aukera badago pantailaren azpiko partean eskuman sakatuz gero eskumarantz mugituko da eta ezkerrean sakatuz gero ezkerrerantz mugituko da." +
                "Tactil  aukeran azpiko zatian izan ezik beste edozein lekutan sakatuta tiro egiten da.\n" +
                "\n                                    ------------------------------------  GAME OVER:  ------------------------------------\n" +
                "-Hiru bidak galtzen badira.\n" +
                "-4 protekzioak apurtzen badira\n" +
                "\n                                    -------------------------------------  VICTORY:  -------------------------------------\n" +
                "-Etsai guztiak hiltzerakoan");

        atzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
