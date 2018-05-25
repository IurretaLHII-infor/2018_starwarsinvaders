package com.example.julen.starwarsinvaders.Others;

import android.graphics.RectF;

/**
 * Created by Julen on 29/01/2018.
 */

public class DisparoLaser {


    private float x;
    private float y;

    private RectF rect;

    // En qué dirección se está disparando
    public final int UP = 0;
    public final int DOWN = 1;

    // No vas a ningún lado
    int heading = -1;
    float speed =  650;

    private int width = 5;
    private int height;

    private boolean isActive;

    public DisparoLaser(int screenY) {

        height = screenY / 20;
        isActive = false;

        rect = new RectF();
    }


    public RectF getRect(){
        return  rect;
    }

    public boolean getStatus(){
        return isActive;
    }

    public void setInactive(){
        isActive = false;
    }

    public float getImpactPointY(){
        if (heading == DOWN){
            return y + height;
        }else{
            return  y;
        }

    }


    public boolean shoot(float startX, float startY, int direction) {
        if (!isActive) {
            x = startX;
            y = startY;
            heading = direction;
            isActive = true;
            return true;
        }

        // La bala ya está activa
        return false;
    }

    public void update(long fps){

        // Solo se mueve para arriba o abajo
        if(heading == UP){
            y = y - speed / fps;
        }else{
            y = y + speed / fps;
        }

        // Actualiza rect
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;

    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
