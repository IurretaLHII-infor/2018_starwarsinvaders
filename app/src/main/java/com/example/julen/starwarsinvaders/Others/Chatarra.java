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

public class Chatarra {

    RectF rect;

    Random generator = new Random();

    // La nave va a ser representada por un Bitmap
    private Bitmap bitmapChatarra;
    private Bitmap bitmapExplosion;
    //largo y ancho del caza
    private float length;
    private float height;

    // X es el extremo a la izquierda del rectángulo
    private float x;

    // Y es la coordenada superior
    private float y;

    // Esto mantendrá la rapidez de los pixeles por segundo a la que el caza se moverá.
    private float shipSpeed;

    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Se está moviendo la nave y en qué dirección
    private int shipMoving = RIGHT;

    boolean isVisible;
    boolean vivo;
    private int vidas;

    public Chatarra(Context context, int row, int column, int screenX, int screenY) {

        // Inicializa un RectF vacío
        rect = new RectF();

        isVisible = true;
        vivo = true;
        vidas=5;

        length = screenX / 8;
        height = screenY / 8;


        int padding = screenX / 9;

        //----CHATARRA ----//

        //cordenadas
        x = (column * (length + padding)) + 100;
        //MOVIL JON
        //MOVIL JULEN
        y = (row * (length + padding / 7)) + length*(float)3.3;




        // Inicializa el bitmap
        bitmapChatarra = BitmapFactory.decodeResource(context.getResources(), R.drawable.chatarra);
        bitmapExplosion = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);

        // Ajusta el primer bitmap a un tamaño apropiado para la resolución de la pantalla
        bitmapChatarra = Bitmap.createScaledBitmap(bitmapChatarra,(int) (length),(int) (height),false);
        bitmapExplosion = Bitmap.createScaledBitmap(bitmapExplosion,(int) (length),(int) (height), false);

        // la rapided...
        shipSpeed = 100;
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
        return bitmapChatarra;
    }


    public Bitmap getBitmap2(){
        return bitmapExplosion;
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

    public void update(long fps){
        if(shipMoving == LEFT){
            x=x;
        }

        if(shipMoving == RIGHT){
            x=x;
        }

        // Actualiza rect el cual es usado para detectar impactos
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }

    public void dropDownAndReverse(){
        if(shipMoving == LEFT){
            shipMoving = RIGHT;
        }else{
            shipMoving = LEFT;
        }

       // Para bajar los cazas cuando llegue al borde!
      //  y = y + height;

        shipSpeed = shipSpeed * 1f;
    }




}
