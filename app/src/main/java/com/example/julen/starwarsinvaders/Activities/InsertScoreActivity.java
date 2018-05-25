package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julen.starwarsinvaders.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertScoreActivity extends Activity {

    Button btnconsultar, btnGuardar;
    EditText etId, etPlayer, etScore;
    TextView tvTitulo;
    Intent i;
    int score = 0;
    private SQLiteDatabase mDataBase;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_score);

        i = getIntent();
        score = i.getIntExtra("SCORE", 0);

        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        etPlayer = (EditText) findViewById(R.id.etPlayer);
        etScore = (EditText) findViewById(R.id.etScore);

        tvTitulo.setText("Sartu zure izena lortutako puntuazioa gordetzeko");
        tvTitulo.setTextSize(32);
        String score2 = String.valueOf(score);
        etScore.setText(score2);

        this.mContext = getApplicationContext().getApplicationContext();
        this.mDataBase = new PuntuBaseHelper(mContext).getWritableDatabase();

     /*   btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  new ConsultarDatos().execute("http://192.168.0.161/StarWars/consulta.php?id=" + etId.getText().toString());

            }
        });*/

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jugador = String.valueOf(etPlayer.getText());

                if (jugador.compareToIgnoreCase("") != 0) {
                    //    new CargarDatos().execute("http://192.168.9.66/StarWars/registro.php?jugador=" + etPlayer.getText().toString() + "&puntuacion=" + score);
                    new CargarDatos().execute("https://stinvaders.000webhostapp.com/registro.php?jugador=" + etPlayer.getText().toString() + "&puntuacion=" + score);

                    ContentValues registro = new ContentValues();
                    registro.put("izena", etPlayer.getText().toString());
                    registro.put("score", etScore.getText().toString());

                    //Elementua txertatu
                    mDataBase.insert("puntuak", null, registro);

                    Intent intent = new Intent(InsertScoreActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    jokalariarenIzenaBeharrezkoa();
                }

            }
        });
        etPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                etPlayer.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });



    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), "Los datos se han guardado correctamente", Toast.LENGTH_LONG).show();

        }
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                etPlayer.setText(ja.getString(1));
                etScore.setText(ja.getString(2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    //Para realizar la conexio mediante una url
    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
        InputStream is = null;
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    @Override
    public void onBackPressed() {
        jokalariarenIzenaBeharrezkoa();
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public void jokalariarenIzenaBeharrezkoa() {
        Toast.makeText(InsertScoreActivity.this, "JOKALARIAREN IZENA BEHARREZKOA DA", Toast.LENGTH_SHORT).show();
        etPlayer.setBackgroundColor(Color.parseColor("#ff0000"));
        etPlayer.startAnimation(shakeError());
    }
}
