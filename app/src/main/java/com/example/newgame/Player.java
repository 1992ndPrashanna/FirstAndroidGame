package com.example.newgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;

    private double velocityX;
    private double velocityY;

    public Player(Context context, double positionX, double positionY, double radius){
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;

        paint = new Paint();
        int colorID = ContextCompat.getColor(context, R.color.player);
        paint.setColor(colorID);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX,(float)positionY,(float)radius, paint);
    }

    public void update(Joystick joystick) {
        velocityX=joystick.getActuatorX()*joystick.MAX_SPEED;
        velocityY=joystick.getActuatorY()*joystick.MAX_SPEED;

        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }
}
