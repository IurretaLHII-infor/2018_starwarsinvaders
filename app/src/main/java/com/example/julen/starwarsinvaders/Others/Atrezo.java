package com.example.julen.starwarsinvaders.Others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.example.julen.starwarsinvaders.R;

import java.util.Random;

/**
 * Created by Julen on 29/01/2018.
 */

public class Atrezo {

    RectF rect;

    Random generator = new Random();

    // declarar los Bitmap

    private Bitmap bitmapFondoLevel1;
    private Bitmap bitmapFondoLevel2;
    private Bitmap bitmapFondoLevel3;
    private Bitmap bitmapFondoLevel4;
    private Bitmap bitmapFondoLevel5;
    private Bitmap bitmapFondoLevel6;
    private Bitmap bitmapPAUSA;
    private Bitmap bitmapVIDA;

    // Largo y ancho
    private float length,length2,length3;
    private float height,height2,height3;

    // X es el extremo a la izquierda del rectángulo
    private float x1,x2;
    // Y es la coordenada superior
    private float y1,y2;

    Boolean isVisible;
    public Atrezo(Context context, int screenX, int screenY) {

        // Inicializa un RectF vacío
        rect = new RectF();

        //para el fondo
        length = screenX ;
        height = screenY ;

        //-----PAUSA---
        //tamaño
        length2 = screenX / 20 ;
        height2 = screenY / 12 ;
        //la posicion - JULEN
        //x2 = screenX - 65; y2 = 5;
        //la posicion - JON
         x2 = screenX - 100; y2 = 5;

        //----BIZITZAK---
        length3 = screenX/40 ;
        height3 = screenY/23 ;
        //posizioa view esanten jako

        isVisible = true;

        // Inicializa el bitmap
        bitmapFondoLevel1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_level1);
        bitmapFondoLevel2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_level2);
        bitmapFondoLevel3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_level3);
        bitmapFondoLevel4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_level4);
        bitmapFondoLevel5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_level5);
        bitmapFondoLevel6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_level6);
        bitmapPAUSA = BitmapFactory.decodeResource(context.getResources(), R.drawable.pausa);
        bitmapVIDA = BitmapFactory.decodeResource(context.getResources(), R.drawable.vida);

        // Ajusta el primer bitmap a un tamaño apropiado para la resolución de la pantalla
        bitmapFondoLevel1 = Bitmap.createScaledBitmap(bitmapFondoLevel1, (int) (length),(int) (height), false);
        bitmapFondoLevel2 = Bitmap.createScaledBitmap(bitmapFondoLevel2, (int) (length),(int) (height), false);
        bitmapFondoLevel3 = Bitmap.createScaledBitmap(bitmapFondoLevel3, (int) (length),(int) (height), false);
        bitmapFondoLevel4 = Bitmap.createScaledBitmap(bitmapFondoLevel4, (int) (length),(int) (height), false);
        bitmapFondoLevel5 = Bitmap.createScaledBitmap(bitmapFondoLevel5, (int) (length),(int) (height), false);
        bitmapFondoLevel6 = Bitmap.createScaledBitmap(bitmapFondoLevel6, (int) (length),(int) (height), false);
        bitmapPAUSA = Bitmap.createScaledBitmap(bitmapPAUSA, (int) (length2),(int) (height2), false);
        bitmapVIDA = Bitmap.createScaledBitmap(bitmapVIDA, (int) (length3),(int) (height3), false);

    }

    public boolean getVisibility(){
        return isVisible;
    }

    public Bitmap getBitmap(){
        return bitmapFondoLevel3;
    }
    public Bitmap getBitmap2(){
        return bitmapPAUSA;
    }
    public Bitmap getBitmap3(){
        return bitmapVIDA;
    }
    public Bitmap getBitmap4(){
        return bitmapFondoLevel1;
    }
    public Bitmap getBitmap5(){
        return bitmapFondoLevel2;
    }
    public Bitmap getBitmap6(){
        return bitmapFondoLevel4;
    }
    public Bitmap getBitmap7(){
        return bitmapFondoLevel5;
    }
    public Bitmap getBitmap8(){
        return bitmapFondoLevel6;
    }
    public float getX(){
        return x1;
    }
    public float getY(){
        return y1;
    }
    public float getX2(){
        return x2;
    }

    public float getY2(){
        return y2;
    }

}
