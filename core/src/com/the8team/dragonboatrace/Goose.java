package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

/**
 * A goose, extended from Obstacle.
 * 
 * @author Josh Stafford
 * @see Obstacle
 */
public class Goose extends Obstacle {

    /**
     * Construct a goose obstacle.
     * 
     * @param horizontalVel The horizontal velocity of the goose
     * @param verticalVel   The vertical velocity of the goose
     * @param x             The starting x coordinate
     * @param y             The starting y coordinate
     * @param world         The world in which to create the goose
     */
    public Goose(int horizontalVel, int verticalVel, int x, int y, World world) {
        super(horizontalVel, verticalVel, 1, x, y, 24, 16, world, "sprites/goose.png");
    }

    /**
     * Inverts the goose's vertical velocity to simulate a 'bounce' from map
     * boundaries.
     */
    public void bounce() {
        this.setVerticalVel(this.verticalVelocity * -1);
    }

}