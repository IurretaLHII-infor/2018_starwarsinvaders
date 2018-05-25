package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.julen.starwarsinvaders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class QueryScoreActivity extends Activity {

    private ListView lvGlobal;
    private ListView lvLokal;
    private AdapterActivity mAdapterGlobal;
    private AdapterActivity mAdapterLokal;
    private List<Puntuazioa> mPuntuazioGlobalaList;
    private List<Puntuazioa> mPuntuazioLokalaList;
    private ImageButton exit, refresh;
    private SQLiteDatabase mDataBase;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_score);

        new ConsultarDatos().execute("https://stinvaders.000webhostapp.com/consulta.php");

        this.exit = (ImageButton) findViewById(R.id.pAtzera);
        this.refresh = (ImageButton) findViewById(R.id.refresh);


        /////////Lokal///////////
        this.lvLokal = (ListView) findViewById(R.id.localScoreList);
        registerForContextMenu(lvLokal);
        this.mPuntuazioLokalaList = new ArrayList<>();
        this.mContext= getApplicationContext().getApplicationContext();
        this.mDataBase = new PuntuBaseHelper(mContext).getWritableDatabase();
        /*this.mAdapterLokal = new AdapterActivity(getApplicationContext(), mPuntuazioLokalaList);
        this.lvLokal.setAdapter(mAdapterLokal);*/
        consultaLokala();


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });


    }

    public void consultaLokala() {
        //Datu basetik irakurtzeko
        Cursor c = mDataBase.query(
                PuntuDbSchema.PuntuTaula.NAME,//Nombre de la tabla
                null,//Lista de cols a consultar
                null,//Cols para la clausula where
                null,//Valores a comparar con las cols del where
                null,//Agrupar con GROUP BY
                null,//Condicion HAVING para GROUP BY
                "score DESC",//Clausulas ORDER BY
                null
        );
        while (c.moveToNext()) {
//            Integer id = c.getInt(c.getColumnIndex(AbestiDbSchema.AbestiTaula.Cols.ID));
            String name = c.getString(c.getColumnIndex(PuntuDbSchema.PuntuTaula.Cols.IZENA));
            Integer scoreLokal = c.getInt(c.getColumnIndex(String.valueOf(PuntuDbSchema.PuntuTaula.Cols.SCORE)));
            Puntuazioa lag = new Puntuazioa();
            lag.setZenbakia(Integer.toString(c.getPosition()+1));
            lag.setIzena(name);
            lag.setPuntuak(scoreLokal);
            mPuntuazioLokalaList.add(lag);
        }
        this.mAdapterLokal = new AdapterActivity(getApplicationContext(), mPuntuazioLokalaList);
        this.lvLokal.setAdapter(mAdapterLokal);
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
            JSONArray json = null;
            try {
                json = new JSONArray(result);

                //Lista Globala//

                lvGlobal = (ListView) findViewById(R.id.globalScoreList);
                mPuntuazioGlobalaList = new ArrayList<>();

                for (int i = 0; i < json.length(); i++) {
                    JSONArray  json2 = new JSONArray(json.getString((i)));

                    String valorJugador = json2.getString((1));
                    String valorPuntacion = json2.getString((2));
                    mPuntuazioGlobalaList.add(new Puntuazioa(String.valueOf(i + 1), valorJugador,Integer.parseInt(valorPuntacion)));


                }

                mAdapterGlobal = new AdapterActivity(getApplicationContext(), mPuntuazioGlobalaList);
                lvGlobal.setAdapter(mAdapterGlobal);


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
            conn.setConnectTimeout(150000 /* milliseconds */);
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


}



