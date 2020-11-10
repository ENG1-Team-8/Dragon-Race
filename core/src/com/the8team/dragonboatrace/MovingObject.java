package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class MovingObject {

    private float xPosition,yPosition;
    private float width,height;
    private Texture objectTexture;
    private float movementSpeed,acceleration;

    public MovingObject(float xPosition, float yPosition, float width, float height,
                        Texture objectTexture,float movementSpeed)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.objectTexture = objectTexture;
        this.movementSpeed = movementSpeed;
    }

    public void draw (Batch batch)
    {
        batch.draw(objectTexture,xPosition,yPosition,width,height);
    }

}
