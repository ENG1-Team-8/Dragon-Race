package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle extends MovingObject {

    public int damage_dealt;
    public int health;

    public Obstacle(int damage_dealt, int health, int x, int y, int width, int height, int maxSpeed, boolean isStatic,
            World world, String textureFile) {

        super(x, y, width, height, maxSpeed, isStatic, world, textureFile);
        this.damage_dealt = damage_dealt;
        this.health = health;

    }


}