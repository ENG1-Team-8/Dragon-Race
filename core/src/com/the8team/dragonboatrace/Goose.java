package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Goose extends Obstacle {

    public Goose(int horizontalVel, int verticalVel, int x, int y, int width, int height, int maxSpeed, World world,
            String textureFile) {

        super(horizontalVel, verticalVel, 1, x, y, width, height, world, textureFile);

    }

    public void bounce() {

        this.setVerticalVel(this.verticalVelocity * -1);
    }

}