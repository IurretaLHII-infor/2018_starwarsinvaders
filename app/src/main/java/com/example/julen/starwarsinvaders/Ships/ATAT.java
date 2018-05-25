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

public class ATAT {

    RectF rect;

    Random generator = new Random();

    // La nave va a ser representada por un Bitmap
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    // largo y ancho del caza
    private float length;
    private float height;
    // X es el extremo a la izquierda del rectángulo
    private float x;
    // Y es la coordenada superior
    private float y;

    // Esto mantendrá la rapidez de los pixeles por segundo a la que el at-at se moverá.
    private float shipSpeed;

    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Se está moviendo la nave y en qué dirección
    private int shipMoving = RIGHT;

    boolean isVisible;
    boolean vivo;
    private int vidas;

    public ATAT(Context context, int row, int column, int screenX, int screenY) {

        // Inicializa un RectF vacío
        rect = new RectF();

        //tamaño del caza
        length = screenX / 12;
        height = screenY / 3;

        isVisible = true;
        vivo = true;
        vidas=5;
        int padding = screenX / 6;

        //cordenada del caza
        x = column * (length + padding)+60;
        //MOVIL JON
        //y = (row * (length + padding/4))+125;
        //MOVIL JULEN
         y = (row * (length + padding/4));

        // Inicializa el bitmap
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.at_at);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);

        // Ajusta el primer bitmap a un tamaño apropiado para la resolución de la pantalla
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,(int) (length),(int) (height),false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2,(int) (length),(int) (height), false);

        // Qué tan rápido va el caza en pixeles por segundo
        //shipSpeed = 6; //velocidad julen
        shipSpeed = 11;
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

    public int getVidas(){
        return vidas;
    }

    public void kenVidas(int a){
        vidas=vidas-a;
    }

    public float getLength(){
        return length;
    }

    public float getHeight(){
        return height;
    }

    public void update(long fps){

        y = y + shipSpeed / fps;
        // Actualiza rect el cual es usado para detectar impactos
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }


    public boolean takeAim(float playerShipX, float playerShipLength){

        int randomNumber = -1;

        // Si está cerca del jugador
        if((playerShipX + playerShipLength > x &&
                playerShipX + playerShipLength < x + length) || (playerShipX > x && playerShipX < x + length)) {

            // Una probabilidad de 1 en 500 chance para disparar
            randomNumber = generator.nextInt(150);
            if(randomNumber == 0) {
                return true;
            }

        }

        // Si está disparando aleatoriamente (sin estar cerca del jugador) una probabilidad de 1 en 5000
        randomNumber = generator.nextInt(150);
        if(randomNumber == 0){
            return true;
        }

        return false;
    }

}
