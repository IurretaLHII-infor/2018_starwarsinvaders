package com.example.julen.starwarsinvaders.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.example.julen.starwarsinvaders.R;

public class IntroActivity extends Activity {

    private VideoView mVideoView;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
        }
        //carga el video de la intro en el layout
        this.mVideoView = (VideoView) findViewById(R.id.videoView_video);
        Uri path = Uri.parse("android.resource://com.example.julen.starwarsinvaders/" + R.raw.video_intro_menu);
        this.mVideoView.setVideoURI(path);
        this.mVideoView.start();

        this.intent = new Intent(Intent.ACTION_MAIN);
        this.intent.addCategory(Intent.CATEGORY_HOME);
        this.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //BIDEOA AMAITZERAKOAN MENURA JOAN
        this.intent = new Intent(this, MenuActivity.class);
        this.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(intent);
            }
        });
    }

    //BIDEOAN SAKATZERAKOAN MENURA JOAN
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // El jugador ha tocado la pantalla
            case MotionEvent.ACTION_DOWN:

                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            System.exit(0);
        }

    }
}