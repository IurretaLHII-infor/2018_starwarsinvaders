package com.example.julen.starwarsinvaders.Levels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.julen.starwarsinvaders.Activities.StarWarsActivity;
import com.example.julen.starwarsinvaders.Others.Atrezo;
import com.example.julen.starwarsinvaders.Others.DisparoLaser;
import com.example.julen.starwarsinvaders.R;
import com.example.julen.starwarsinvaders.Ships.JangoFett;
import com.example.julen.starwarsinvaders.Ships.PlayerShip;

import java.io.IOException;

/**
 * Created by Julen on 29/01/2018.
 */


public class Level5View extends LevelView {

    Context context;

    // Esta es nuestra secuencia
    private Thread gameThread = null;

    // Nuestro SurfaceHolder para bloquear la superficie antes de que dibujemos nuestros gráficos
    private SurfaceHolder ourHolder;

    // Un booleano el cual vamos a activar y desactivar
    // cuando el juego este activo- o no.
    private volatile boolean playing;

    // El juego esta pausado al iniciar
    private boolean paused = true;

    // Un objeto de lienzo (Canvas) y de pintar (Paint)
    private Canvas canvas;
    private Paint paint;

    // Esta variable rastrea los cuadros por segundo del juego
    private long fps;

    // Esto se utiliza para ayudar a calcular los cuadros por segundo
    private long timeThisFrame;

    // El tamaño de la pantalla en pixeles
    private int screenX;
    private int screenY;

    // La nave del jugador
    private PlayerShip playerShip;


    //ATREZOS
    private Atrezo atrezo;
    private Atrezo fondoAtrezo;
    private Atrezo pausaAtrezo;
    private Atrezo vidasAtrezo;


    //--Contador de muertes
    int cazaDestruida = 0;

    // jangofett
    JangoFett[] cazaEnemigo = new JangoFett[1];
    int numCazaEnemigo = 0;

    // La bala del jugador
    private DisparoLaser disparoLaser;

    // disparos derecha jangofett
    private DisparoLaser[] disparosEnemigosDerecha = new DisparoLaser[200];
    private int nextBullet;
    private int maxEnemigoBullets = 16;

    // disparos izquierda jangofett
    private DisparoLaser[] disparosEnemigosIzquierda = new DisparoLaser[200];
    private int nextBullet3;
    private int maxEnemigoBullets3 = 16;

    // misil central jangofett
    private DisparoLaser[] misilEnemigo = new DisparoLaser[100];
    private int nextBullet2;
    private int maxEnemigoBullets2 = 8;

    // Para los efectos de sonido
    private SoundPool soundPool;
    private int disparoLaserID = -1;
    private int misilLaserID = -1;

    //---codigo para la cancion
    MediaPlayer mp = new MediaPlayer();

    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int damageShelterID = -1;

    // Jokua amaitzen denerako
    boolean galdu = false;
    boolean irabazi = false;

    // La puntuación
    int score = 0;

    // Vidas
    private int lives = 3;

    //Bibrador
    private Vibrator vibra;

    //AZELEROMETROA HASIERATU
    protected Sensor azelerometroa;
    protected SensorEventListener azelerometroaListener;
    protected SensorManager sm;

    //Bordearen boolean-ak
    private boolean tope = false;
    private boolean eskuma = false;
    private boolean ezkerra = false;

    String mugimenduAukera = "";
    String partidaMota = "";

    StarWarsActivity starWarsActivity;

    // Cuando inicializamos (call new()) en gameView
    // Este método especial de constructor se ejecuta
    public Level5View(Context context, int x, int y, Sensor azelerometroa, SensorManager sm, final String mugimenduAukera, String partidaMota, int score,int lives, StarWarsActivity starWarsActivity) {

        // La siguiente línea del código le pide a
        // la clase de SurfaceView que prepare nuestro objeto
        super(context);

        this.azelerometroa = azelerometroa;
        this.sm = sm;
        this.azelerometroa = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.mugimenduAukera = mugimenduAukera;
        this.partidaMota = partidaMota;
        this.starWarsActivity = starWarsActivity;
        this.score = score;
        this.lives=lives;

        //Sensorea aktibatzeko kodigoa
        //todo
        this.azelerometroa = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (this.azelerometroa != null) {
            if (mugimenduAukera.compareToIgnoreCase("sensor") == 0) {
                this.azelerometroaListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {

                        if (event.values[1] > 1.2 && !eskuma) {
                            playerShip.setMovementState(playerShip.RIGHT);
                            ezkerra = false;
                            tope = false;
                        } else if ((event.values[1] < 1.2 && event.values[1] > -1.2)) {
                            playerShip.setMovementState(playerShip.STOPPED);
                        } else if (event.values[1] < -1.2 && !ezkerra) {
                            //playerShip.shipSpeed=event.values[1]*50;
                            playerShip.setMovementState(playerShip.LEFT);
                            eskuma = false;
                            tope = false;
                        }
                    }


                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        // required method

                    }
                };
            }
        }


        // Hace una copia del "context" disponible globalmete para que la usemos en otro método
        this.context = context;


        //Vibrator
        vibra = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);

        // Inicializa los objetos de ourHolder y paint
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        // Este SoundPool está obsoleto
        soundPool = new SoundPool(11, AudioManager.STREAM_MUSIC, 0);

        try {
            // Crea objetos de las 2 clases requeridas
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Carga nuestros efectos de sonido en la memoria listos para usarse
            descriptor = assetManager.openFd("disparoLaser.mp3");
            disparoLaserID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("misilLaser.mp3");
            misilLaserID = soundPool.load(descriptor, 0);


            descriptor = assetManager.openFd("invaderexplode.ogg");
            invaderExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("damageshelter.ogg");
            damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("playerexplode.ogg");
            playerExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("damageshelter.ogg");
            damageShelterID = soundPool.load(descriptor, 0);


        } catch (IOException e) {
            // Imprime un mensaje de error a la consola
            Log.e("error", "failed to load sound files");
        }

        prepareLevel();
    }

    private void prepareLevel() {

        // Atrezoko gauzak sortu
        atrezo = new Atrezo(context, screenX, screenY);
        fondoAtrezo = atrezo;
        pausaAtrezo = fondoAtrezo;
        vidasAtrezo = fondoAtrezo;

        //----------JUGADOR----------//
        // Nabe del jugador
        playerShip = new PlayerShip(context, screenX, screenY);
        // Preparar las balas del jugador
        disparoLaser = new DisparoLaser(screenY);

        //----------JANGOFETT-------//
        // Construir jangofett
        numCazaEnemigo = 0;
        for (int column = 0; column < 1; column++) {
            for (int row = 0; row < 1; row++) {
                cazaEnemigo[numCazaEnemigo] = new JangoFett(context, row, column, screenX, screenY);
                numCazaEnemigo++;
            }
        }

        // Inicializar la formación de disparos derechos de jango
        for (int i = 0; i < disparosEnemigosDerecha.length; i++) {
            disparosEnemigosDerecha[i] = new DisparoLaser(screenY);
        }

        // Inicializar la formación de disparos izquierdos de jango
        for (int i = 0; i < disparosEnemigosIzquierda.length; i++) {
            disparosEnemigosIzquierda[i] = new DisparoLaser(screenY);
        }

        // inicializar los misiles del jango
        for (int i = 0; i < misilEnemigo.length; i++) {
            misilEnemigo[i] = new DisparoLaser(screenY);
        }

        reproducirCancion();

    }

    @Override
    public void run() {

        while (playing) {

            // Captura el tiempo actual en milisegundos en startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Actualiza el cuadro
            if (!paused) {
                update();
            }

            // Dibuja el cuadro
            draw();

            // Calcula los cuadros por segundo de este cuadro
            // Ahora podemos usar los resultados para
            // medir el tiempo de animaciones y otras cosas más.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }


        }
    }

    private void update() {

        boolean bumped = false;

        //-------------JUGADOR-------------//
        // Mueve la nave espacial del jugador
        playerShip.update(fps);

        // para que la nave del jugador no se escape de los bordes
        if (playerShip.getX() > screenX - playerShip.getLength()) {
            tope = true;
            eskuma = true;
        } else if (playerShip.getX() < 0) {
            tope = true;
            ezkerra = true;
        }
        if (tope) {
            playerShip.setMovementState(playerShip.STOPPED);
        }

        // Actualiza las balas del jugador
        if (disparoLaser.getStatus()) {
            disparoLaser.update(fps);
        }

        // ¿Han golpeado las balas del jugador la parte superior de la pantalla?
        if (disparoLaser.getImpactPointY() < 0) {
            disparoLaser.setInactive();
        }

        // ¿Ha golpeado la bala del jugador a un caza enemigo?
        if (disparoLaser.getStatus()) {
            for (int i = 0; i < numCazaEnemigo; i++) {
                if (cazaEnemigo[i].getVisibility()) {
                    if (RectF.intersects(disparoLaser.getRect(), cazaEnemigo[i].getRect())) {
                        cazaEnemigo[i].kenVidas(1);
                        if (cazaEnemigo[i].getVidas() == 0) {
                            cazaEnemigo[i].setExplosion();
                            cazaDestruida = cazaDestruida + 1;
                            score = score + 300;
                        }
                        soundPool.play(invaderExplodeID, 1, 1, 0, 0, 1);
                        disparoLaser.setInactive();
                        score = score + 0;


                    }
                }
            }
        }


        //-------------JANGOFETTT-------------//

        // Actualiza a Jangofett y la opcion de disparo
        for (int i = 0; i < numCazaEnemigo; i++) {

            if (cazaEnemigo[i].getVisibility()) {

                cazaEnemigo[i].update(fps);

                // opcion de disparo derecho
                if (cazaEnemigo[i].takeAim(playerShip.getX(),
                        playerShip.getLength())) {
                    // Si sí, intentalo y genera una bala
                    if (disparosEnemigosDerecha[nextBullet].shoot(cazaEnemigo[i].getX() + cazaEnemigo[i].getLength(), cazaEnemigo[i].getY() + cazaEnemigo[i].getHeight(), disparoLaser.DOWN)) {

                        // Disparo realizado
                        soundPool.play(disparoLaserID, 1, 1, 0, 0, 1);

                        // Preparete para el siguiente disparo
                        nextBullet++;

                        // Inicia el ciclo repetitivo otra vez al
                        // primero si ya hemos llegado al último.
                        if (nextBullet == maxEnemigoBullets) {
                            // Esto detiene el disparar otra bala hasta
                            // que una haya completado su trayecto.
                            // Por que si bullet 0 todavia está activo,
                            // shoot regresa a false.
                            nextBullet = 0;
                        }
                    }
                }

                // opcion de disparo del misil (tiro central)
                if (cazaEnemigo[i].takeAim(playerShip.getX(),
                        playerShip.getLength())) {
                    // Si sí, intentalo y genera una bala
                    if (misilEnemigo[nextBullet].shoot(cazaEnemigo[i].getX() + cazaEnemigo[i].getLength() / 2, cazaEnemigo[i].getY() + cazaEnemigo[i].getHeight(), disparoLaser.DOWN)) {

                        // Disparo realizado
                        soundPool.play(misilLaserID, 1, 1, 0, 0, 1);

                        // Preparete para el siguiente disparo
                        nextBullet2++;

                        // Inicia el ciclo repetitivo otra vez al
                        // primero si ya hemos llegado al último.
                        if (nextBullet2 == maxEnemigoBullets2) {
                            // Esto detiene el disparar otra bala hasta
                            // que una haya completado su trayecto.
                            // Por que si bullet 0 todavia está activo,
                            // shoot regresa a false.
                            nextBullet2 = 0;
                        }
                    }
                }

                // opcion de disparo izquierdo
                if (cazaEnemigo[i].takeAim(playerShip.getX(),
                        playerShip.getLength())) {
                    // Si sí, intentalo y genera una bala
                    if (disparosEnemigosIzquierda[nextBullet].shoot(cazaEnemigo[i].getX(), cazaEnemigo[i].getY() + cazaEnemigo[i].getHeight(), disparoLaser.DOWN)) {

                        // Disparo realizado
                        soundPool.play(disparoLaserID, 1, 1, 0, 0, 1);

                        // Preparete para el siguiente disparo
                        nextBullet3++;

                        // Inicia el ciclo repetitivo otra vez al
                        // primero si ya hemos llegado al último.
                        if (nextBullet3 == maxEnemigoBullets3) {
                            // Esto detiene el disparar otra bala hasta
                            // que una haya completado su trayecto.
                            // Por que si bullet 0 todavia está activo,
                            // shoot regresa a false.
                            nextBullet3 = 0;
                        }
                    }
                }

                // calcula si el disparo del jugador va ha impactar y hace una evasion
                if (disparoLaser.getStatus()) {
                    cazaEnemigo[i].evasion(disparoLaser.getX());
                }

                // Si ese movimiento causó que golpearan la pantalla,
                // cambia bumped a true.
                if (cazaEnemigo[i].getX() > screenX - cazaEnemigo[i].getLength()
                        || cazaEnemigo[i].getX() < 0) {
                    bumped = true;
                }
            }
        }

        // Actualiza las balas deracha de jango
        for (int i = 0; i < disparosEnemigosDerecha.length; i++) {
            if (disparosEnemigosDerecha[i].getStatus()) {
                disparosEnemigosDerecha[i].update(fps);
            }
        }

        // Actualiza las balas izquierda de jango
        for (int i = 0; i < disparosEnemigosIzquierda.length; i++) {
            if (disparosEnemigosIzquierda[i].getStatus()) {
                disparosEnemigosIzquierda[i].update(fps);
            }
        }

        // Actualiza los misiles de jango
        for (int i = 0; i < misilEnemigo.length; i++) {
            if (misilEnemigo[i].getStatus()) {
                misilEnemigo[i].update(fps);
            }
        }

        // ¿Ha golpeado alguna bala derecha de jango la parte inferior de la pantalla?
        for (int i = 0; i < disparosEnemigosDerecha.length; i++) {
            if (disparosEnemigosDerecha[i].getImpactPointY() > screenY) {
                disparosEnemigosDerecha[i].setInactive();
            }
        }
        // ¿Ha golpeado alguna bala izquierda de jango la parte inferior de la pantalla?
        for (int i = 0; i < disparosEnemigosIzquierda.length; i++) {
            if (disparosEnemigosIzquierda[i].getImpactPointY() > screenY) {
                disparosEnemigosIzquierda[i].setInactive();
            }
        }

        // ¿Ha golpeado algun misil de jango la parte inferior de la pantalla?
        for (int i = 0; i < misilEnemigo.length; i++) {
            if (misilEnemigo[i].getImpactPointY() > screenY) {
                misilEnemigo[i].setInactive();
            }
        }

        // Si jango toca un borde
        if (bumped) {
            // Mueve a todos los cazas hacia abajo y cambia la dirección
            for (int i = 0; i < numCazaEnemigo; i++) {
                cazaEnemigo[i].dropDownAndReverse();
            }
        }


        //impacto de disparo derecho de jango a jugador
        for (int i = 0; i < disparosEnemigosDerecha.length; i++) {
            if (disparosEnemigosDerecha[i].getStatus()) {
                if (RectF.intersects(playerShip.getRect(), disparosEnemigosDerecha[i].getRect())) {
                    disparosEnemigosDerecha[i].setInactive();
                    lives--;
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);
                    vibra.vibrate(300);

                    // ¿Se acabó el juego?
                    if (lives == 0) {
                        Galdu();
                    }
                }
            }
        }

        //impacto de disparo izquierdo de jango a jugador
        for (int i = 0; i < disparosEnemigosIzquierda.length; i++) {
            if (disparosEnemigosIzquierda[i].getStatus()) {
                if (RectF.intersects(playerShip.getRect(), disparosEnemigosIzquierda[i].getRect())) {
                    disparosEnemigosIzquierda[i].setInactive();
                    lives--;
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);
                    vibra.vibrate(300);

                    // ¿Se acabó el juego?
                    if (lives == 0) {
                        Galdu();
                    }
                }
            }
        }

        //impacto de misil de jango a jugador
        for (int i = 0; i < misilEnemigo.length; i++) {
            if (misilEnemigo[i].getStatus()) {
                if (RectF.intersects(playerShip.getRect(), misilEnemigo[i].getRect())) {
                    misilEnemigo[i].setInactive();
                    lives = 0;
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);

                    // ¿Se acabó el juego?
                    if (lives == 0) {
                        Galdu();
                    }
                }
            }
        }


        //----------------PERDER O GANAR------------//

        // Si jango es destruido el jugador gana
        if (cazaDestruida == 1) {
            Irabazi();
        }

    }

    private void draw() {

        // Asegurate de que la superficie del dibujo sea valida o tronamos
        if (ourHolder.getSurface().isValid()) {
            // Bloquea el lienzo para que este listo para dibujar
            canvas = ourHolder.lockCanvas();
            // Escoje el color de la brocha para dibujar
            paint.setColor(Color.argb(255, 255, 255, 255));

            //-----------PINTAR EL FONDO------------//
            // dibuja la imagen de fondo
            if (fondoAtrezo.getVisibility()) {
                canvas.drawBitmap(fondoAtrezo.getBitmap7(), fondoAtrezo.getX(), fondoAtrezo.getY(), paint);
            }

            //---------------JUGADOR-------------//
            // Dibuja a la nave espacial del jugador
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getX(), screenY - playerShip.getAltura(), paint);

            // Dibuja a las balas del jugador si están activas
            if (disparoLaser.getStatus()) {
                paint.setColor(Color.CYAN);
                canvas.drawRect(disparoLaser.getRect(), paint);
            }


            // Dibuja a las balas derecha de jango si están activas
            for (int i = 0; i < disparosEnemigosDerecha.length; i++) {
                if (disparosEnemigosDerecha[i].getStatus()) {
                    paint.setColor(Color.RED);
                    canvas.drawRect(disparosEnemigosDerecha[i].getRect(), paint);
                }
            }
            // Dibuja a las balas izquierda de jango si están activas
            for (int i = 0; i < disparosEnemigosIzquierda.length; i++) {
                if (disparosEnemigosIzquierda[i].getStatus()) {
                    paint.setColor(Color.RED);
                    canvas.drawRect(disparosEnemigosIzquierda[i].getRect(), paint);
                }
            }

            // Dibuja a los misiles de jango si están activas
            for (int i = 0; i < misilEnemigo.length; i++) {
                if (misilEnemigo[i].getStatus()) {
                    paint.setColor(Color.MAGENTA);
                    canvas.drawRect(misilEnemigo[i].getRect(), paint);
                }
            }
            //-------------JANGOFETT------------//
            // Dibuja a jangofett
            for (int i = 0; i < numCazaEnemigo; i++) {
                if (cazaEnemigo[i].getVisibility()) {
                    if (cazaEnemigo[i].getExplosion()) {
                        canvas.drawBitmap(cazaEnemigo[i].getBitmap(), cazaEnemigo[i].getX(), cazaEnemigo[i].getY(), paint);
                    } else {
                        canvas.drawBitmap(cazaEnemigo[i].getBitmap2(), cazaEnemigo[i].getX(), cazaEnemigo[i].getY(), paint);
                        cazaEnemigo[i].setInvisible();
                    }
                }
            }

            //------VIDAS------
            int vidas = 300;
            for (int i = 0; i < lives; i++) {
                // canvas.drawBitmap(vidasAtrezo.getBitmap3(), vidasAtrezo.getX3(), vidasAtrezo.getY3(), paint);
                canvas.drawBitmap(vidasAtrezo.getBitmap3(), vidas, 25, paint);
                vidas += 60;
            }

            //----BOTON PAUSA-----
            paint.setColor(Color.parseColor("#61fdfdfd"));
            canvas.drawBitmap(pausaAtrezo.getBitmap2(), pausaAtrezo.getX2(), pausaAtrezo.getY2(), paint);

            // Cambia el color de la brocha
            paint.setColor(Color.argb(255, 249, 129, 0));

            //----PERDER O GANAR----
            if (galdu && paused) {
                paint.setTextSize(160);
                canvas.drawText("GAME OVER", (screenX / 2) - 400, screenY / 2, paint);
            }

            if (irabazi && paused) {
                paint.setTextSize(160);
                canvas.drawText("VICTORY!", (screenX / 2) - 400, screenY / 2, paint);
            }

            // Dibuja la puntuación y las vidas restantes

            paint.setTextSize(30);
            canvas.drawText("Puntuak: " + score + " Bizitzak: ", 10, 50, paint);

            // Extrae todo a la pantalla
            ourHolder.unlockCanvasAndPost(canvas);
        }


    }

    private void Irabazi() {
        paused = true;
        if (partidaMota.compareToIgnoreCase("historia") == 0) {

            try {
                super.finalize();

                starWarsActivity.pasarNivel(score, lives);
                mp.stop();
               // disparoLaserMP.stop();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else if (partidaMota.compareToIgnoreCase("rapida") == 0) {
            try {
                super.finalize();

                starWarsActivity.salirMenu();
                mp.stop();
                //disparoLaserMP.stop();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private void Galdu() {
        paused = true;
        if (partidaMota.compareToIgnoreCase("historia") == 0) {

            try {
                super.finalize();

                starWarsActivity.guardarPuntuacion(score);
                mp.stop();
                //disparoLaserMP.stop();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else if (partidaMota.compareToIgnoreCase("rapida") == 0) {
            try {
                super.finalize();

                starWarsActivity.salirMenu();
                mp.stop();
                //disparoLaserMP.stop();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


    // Si SpaceInvadersActivity es pausado/detenido
    // apaga nuestra secuencia.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
        sm.unregisterListener(this.azelerometroaListener);
    }

    // Si SpaceInvadersActivity es iniciado entonces
    // inicia nuestra secuencia.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        sm.registerListener(this.azelerometroaListener, this.azelerometroa, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // La clase de SurfaceView implementa a onTouchListener
    // Así es que podemos anular este método y detectar toques a la pantalla.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // El jugador ha tocado la pantalla
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if (this.mugimenduAukera.compareToIgnoreCase("tactil") == 0) {
                    if (motionEvent.getY() > screenY - screenY / 8) {
                        if (motionEvent.getX() > screenX / 2) {
                            playerShip.setMovementState(playerShip.RIGHT);
                            if (tope) {
                                tope = false;
                            }
                            ezkerra = false;
                        } else {
                            playerShip.setMovementState(playerShip.LEFT);
                            if (tope) {
                                tope = false;
                            }
                            eskuma = false;
                        }
                    }
                }

                if (motionEvent.getY() < screenY / 6) {
                    if (motionEvent.getX() > screenX - 65) {
                        if (playing) {
                            pause();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Pausa");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Jarraitu", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    soundPool.play(disparoLaserID, 1, 1, 0, 0, 1);
                                    resume();
                                }
                            });
                            builder.setNegativeButton("Irten", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    starWarsActivity.salirMenu();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }


                if (motionEvent.getY() < screenY - screenY / 8) {
                    // Disparos lanzados
                    if (disparoLaser.shoot(playerShip.getX() + playerShip.getLength() / 2, screenY, disparoLaser.UP)) {
                        soundPool.play(disparoLaserID, 1, 1, 0, 0, 1);

                    }
                }
                break;

            // El jugador ha retirado su dedo de la pantalla
            case MotionEvent.ACTION_UP:

                if (this.mugimenduAukera.compareToIgnoreCase("tactil") == 0) {
                    if (motionEvent.getY() > screenY - screenY / 8) {
                        playerShip.setMovementState(playerShip.STOPPED);
                    }
                }

                break;

        }

        return true;
    }

    public void reproducirCancion() {
        mp = MediaPlayer.create(context, R.raw.dark_side_march);
        mp.setVolume(6000, 6000);
        mp.start();

    }


}



