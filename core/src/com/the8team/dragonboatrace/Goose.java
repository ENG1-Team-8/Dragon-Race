package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Goose extends Obstacle {

    public Goose(int damage_dealt, int health, int x, int y, int width, int height, int maxSpeed, boolean isStatic,
            World world, String textureFile) {

        super(damage_dealt, health, x, y, width, height, maxSpeed, isStatic, world, textureFile);

    }
}