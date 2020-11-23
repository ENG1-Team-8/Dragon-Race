package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An obstacle, extended from MovingObject.
 * 
 * @author Josh Stafford
 */
public class Obstacle extends MovingObject {

    // Characteristics
    public int damageDealt;
    public int horizontalVelocity;
    public int verticalVelocity;

    /**
     * Construct an obstacle.
     * 
     * @param horizontalVelocity The horizontal velocity of the obstacle
     * @param verticalVelocity   The vertical velocity of the obstacle
     * @param damageDealt        How much damage it can deal
     * @param x                  The starting x coordinate
     * @param y                  The starting y coordinate
     * @param width              The width
     * @param height             The height
     * @param world              The world in which to create the obstacle
     * @param textureFile        The texture path for the obstacle
     */
    public Obstacle(int horizontalVelocity, int verticalVelocity, int damageDealt, int x, int y, int width, int height,
            World world, String textureFile) {

        super(x, y, width, height, world, textureFile);
        this.damageDealt = damageDealt;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
    }

    /**
     * Updates horizontal velocity.
     * 
     * @param vel The velocity value to set
     */
    public void setHorizontalVel(int vel) {

        // Sets class variable to passed value and then runs updateMovement() method
        this.horizontalVelocity = vel;
        this.updateMovement();

    }

    /**
     * Updates vertical veolcity.
     * 
     * @param vel The velocity value to set
     */
    public void setVerticalVel(int vel) {

        // Sets class variable to passed value and then runs updateMovement() method
        this.verticalVelocity = vel;
        this.updateMovement();

    }

    /**
     * Update the movement of the obstacle with the velocities.
     */
    public void updateMovement() {

        // Sets obstacle velocity using class variables
        if (this.bBody != null) {
            this.bBody.setLinearVelocity(this.horizontalVelocity, this.verticalVelocity);
        }

    }

    /**
     * Function to check if obstacle has left the boundaries of the screen behind
     * the player and returns a boolean result.
     * 
     * @param playerX The player's x coordinate
     * @return true if offscreen, false otherwise
     */

    public boolean isOffScreen(int playerX) {

        float x = this.getPosition().x * Utils.scale;
        float screenWidth = Gdx.graphics.getWidth();

        if (x < playerX - screenWidth / 2) {
            System.out.println("Obstacle has left the screen");
            return true;
        } else {
            return false;
        }

    }

    /**
     * Reinstantiate function that takes parameters for position of moved obstacle.
     * 
     * @param x The x coordinate to move to
     * @param y The y coordinate to move to
     */
    public void reinstantiate(int x, int y) {

        this.bBody.getTransform().setPosition(new Vector2((float) x, (float) y));

    }

    /**
     * Reinstantiate function that sets position of obstacle based on position of
     * player.
     * 
     * @param playerX The player's x coordinate
     */
    public void reinstantiate(int playerX) {

        float screenWidth = Gdx.graphics.getWidth();
        float newX = (float) playerX + screenWidth / 2 + 4f;

        this.bBody.getTransform().setPosition(new Vector2(newX, this.getPosition().y));

    }

}