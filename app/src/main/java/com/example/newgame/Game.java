package com.example.newgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects in the screen
 */


public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);
//        Get SurfaceHolder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

//        Initializing player class, game objects
        joystick = new Joystick(275,700,70,40);
        player = new Player(getContext(), 1000,500,30);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        handle touch event
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double)event.getX(),(double)event.getY())){
                    joystick.setIsPressed(true);
                }
//                player.setPosition((double)event.getX(),(double)event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double)event.getX(),(double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawFPS(canvas);
        drawUPS(canvas);

        player.draw(canvas);
        joystick.draw(canvas);
    }

    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();

        int color = ContextCompat.getColor(getContext(),R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(60);
        canvas.drawText("UPS " + averageUPS, 100, 50, paint);

    }

    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(60);
        canvas.drawText("FPS " + averageFPS, 100, 150, paint);

    }

    public void update() {
        player.update(joystick);
        joystick.update();
    }
}
