package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class MovingObject {

    private float xPosition,yPosition;
    private float width,height;
    private Texture objectTexture;
    private float movementSpeed,acceleration,handling;
    private float stamina;
    private Rectangle BoundBox;

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
        this.BoundBox = new Rectangle(xPosition,yPosition,width,height);
    }

    public void draw (Batch batch)
    {
        batch.draw(objectTexture,xPosition,yPosition,width,height);
    }

    public void setMovementSpeed (float movementSpeed)
    {
        this.movementSpeed=movementSpeed;
    }

}
