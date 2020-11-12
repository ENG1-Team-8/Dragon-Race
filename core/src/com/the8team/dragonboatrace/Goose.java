package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Goose extends Obstacle {

    public Goose(int horizontalVel, int verticalVel, int damageDealt, int x, int y, int width, int height, int maxSpeed,
            boolean isStatic, World world, String textureFile) {

        super(horizontalVel, verticalVel, damageDealt, x, y, width, height, maxSpeed, isStatic, world, textureFile);

    }
}