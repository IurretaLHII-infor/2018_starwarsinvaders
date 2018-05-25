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

public class CazaRepublica {

    RectF rect;

    Random generator = new Random();

    // La nave va a ser representada por un Bitmap
    private Bitmap bitmap1;
    private Bitmap bitmap2;

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

    public CazaRepublica(Context context, int row, int column, int screenX, int screenY) {

        // Inicializa un RectF vacío
        rect = new RectF();

        //tamaño del caza
        length = screenX / 12;
        height = screenY / 9;

        isVisible = true;
        vivo = true;
        int padding = screenX / 12;

        //cordenadas del caza
        x = column * (length + padding);
        //MOVIL JON

        //MOVIL JULEN
        y = (row * (length + padding / 4)) + length*(float)5.3;

        // Inicializa el bitmap
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.caza_republica);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);

        // Ajusta el primer bitmap a un tamaño apropiado para la resolución de la pantalla
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,(int) (length),(int) (height),false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2,(int) (length),(int) (height), false);

        // Qué tan rápido va el caza en pixeles por segundo
         //shipSpeed = 100; //velocidad julen
        shipSpeed = 185;
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

    public void update(long fps){
        if(shipMoving == LEFT){
            x = x - shipSpeed / fps;
        }

        if(shipMoving == RIGHT){
            x = x + shipSpeed / fps;
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
        randomNumber = generator.nextInt(2000);
        if(randomNumber == 0){
            return true;
        }

        return false;
    }


}
