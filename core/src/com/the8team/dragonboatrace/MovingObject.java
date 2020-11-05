package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MovingObject extends Actor {

    private float xPosition,yPosition;
    private float width,height;
    private Texture objectTexture;
    private float movementSpeed,acceleration,handling;
    private float stamina;

    public MovingObject(float xPosition, float yPosition, float width, float height,
                        Texture objectTexture,float movementSpeed, float acceleration, float handling, float stamina)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.objectTexture = objectTexture;
        this.movementSpeed = movementSpeed;
        this.acceleration = acceleration;
        this.handling = handling;
        this.stamina = stamina;
    }

    public void draw (Batch batch)
    {
        batch.draw(objectTexture,xPosition,yPosition,width,height);
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public float getWidth() {
        return super.getWidth();
    }

    @Override
    public float getHeight() {
        return super.getHeight();
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getHandling() {
        return handling;
    }

    public float getStamina() {
        return stamina;
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    public void setWidth(float width) {
        this.width=width;
    }

    @Override
    public void setHeight(float height) {
        this.height=height;
    }

    @Override
    public void setSize(float width, float height) {
        this.height=height;
        this.width=width;
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        this.xPosition=x;
        this.yPosition=y;
        this.setSize(width,height);
    }

    public void setMovementSpeed (float movementSpeed)
    {
        this.movementSpeed=movementSpeed;
    }

    public void setStamina (float stamina)
    {
        this.stamina=stamina;
    }

}
