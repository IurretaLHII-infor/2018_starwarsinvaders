
package com.example.julen.starwarsinvaders.Activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/*Created by jonuriza on 11/27/17.*/


public class PuntuBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "puntuDB.db";

    public PuntuBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PuntuDbSchema.PuntuTaula.NAME +
                "(" + PuntuDbSchema.PuntuTaula.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PuntuDbSchema.PuntuTaula.Cols.IZENA +
                ", " +PuntuDbSchema.PuntuTaula.Cols.SCORE +" INTEGER"+
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
