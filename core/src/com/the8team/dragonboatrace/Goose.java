package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

public class Goose extends Obstacle {

    public Goose(int horizontalVel, int verticalVel, int x, int y, World world) {

        super(horizontalVel, verticalVel, 1, x, y, 24, 16, world, "sprites/goose.png");

    }

    public void bounce() {

        this.setVerticalVel(this.verticalVelocity * -1);
    }

}