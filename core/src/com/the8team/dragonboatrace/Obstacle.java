package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;

public class Obstacle extends MovingObject {

    public int damage_dealt;
    public int health;

    public Obstacle(int damage_dealt, int health, float xPosition, float yPosition, float width, float height,
            Texture objectTexture, float movementSpeed, float acceleration, float handling, float stamina) {

        super(xPosition, yPosition, width, height, objectTexture, movementSpeed, acceleration, handling, stamina);
        this.damage_dealt = damage_dealt;
        this.health = health;

    }

}