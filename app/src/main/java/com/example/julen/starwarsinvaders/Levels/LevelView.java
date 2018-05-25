package com.example.julen.starwarsinvaders.Levels;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by Julen on 28/04/2018.
 */

public class LevelView extends SurfaceView implements Runnable {

    private volatile boolean playing;
    private Thread gameThread = null;

    public LevelView(Context context) {
        super(context);
    }

    @Override
    public void run() {
    }
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }




}
