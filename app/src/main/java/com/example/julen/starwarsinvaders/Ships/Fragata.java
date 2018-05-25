package com.example.julen.starwarsinvaders.Ships;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.example.julen.starwarsinvaders.R;

import java.util.Random;

/**
 * Created by Julen on 29/01/2018.
 */

public class Fragata {

    RectF rect;

    Random generator = new Random();

    // La nave va a ser representada por un Bitmap
    private Bitmap bitmap1;
    private Bitmap bitmap2;

    //largo y ancho será la fragata
    private float length;
    private float height;

    // X es el extremo a la izquierda del rectángulo
    private float x;

    // Y es la coordenada superior
    private float y;

    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int shipMoving = RIGHT;

    boolean isVisible;
    boolean vivo;
    private int vidas;

    public Fragata(Context context, int row, int column, int screenX, int screenY) {

        // Inicializa un RectF vacío
        rect = new RectF();

        length = screenX / 12;
        height = screenY / 3;

        isVisible = true;
        vivo = true;
        vidas=10;
        int padding = screenX / 5;

        x = column * (length + padding);
        y = row * (length + padding/4);


        x = (column * (length + padding)) + 50;
        //MOVIL JON
        y = (row * (length + padding / 4)) + length*(float)3.5;

        //MOVIL JULEN
        //y = (row * (length + padding / 4)) + length*(float)3.7;


        // Inicializa el bitmap
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fragata);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);

        // Ajusta el primer bitmap a un tamaño apropiado para la resolución de la pantalla
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,(int) (length),(int) (height), false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2,(int) (length),(int) (height), false);

    }
    public int getVidas(){
        return vidas;
}

    public void kenVidas(int a){
        vidas=vidas-a;
    }

    public void setExplosion(){
        vivo = false;
    }

    public boolean getExplosion(){
        return vivo;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }

    public RectF getRect(){
        return rect;
    }

    public Bitmap getBitmap(){
        return bitmap1;
    }

    public Bitmap getBitmap2(){
        return bitmap2;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getLength(){
        return length;
    }

    public float getHeight() {
        return height;
    }

    public void update(long fps){
        if(shipMoving == LEFT){
            x=x;
        }

        if(shipMoving == RIGHT){
            x = x;
        }

        // Actualiza rect el cual es usado para detectar impactos
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }




















}
