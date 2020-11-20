package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An obstacle within the game, extends MovingObject
 * 
 * @author Josh Stafford
 * @see MovingObject
 */
public class Obstacle extends MovingObject {

    // Obstacle velocities and damage
    public int damageDealt;
    public int horizontalVelocity;
    public int verticalVelocity;

    /**
     * Should be used as a super constructor for obstacle types, or as a generic
     * obstacle
     * 
     * @see Goose
     * @see Branch
     * 
     * @param horizontalVelocity The horizontal velocity of the obstacle
     * @param verticalVelocity   The vertical velocity of the obstacle
     * @param damageDealt        The damage to be taken from a boat's health on
     *                           collision
     * @param x                  The starting x coordinate
     * @param y                  The starting y coordinate
     * @param width              The width of the obstacle
     * @param height             The height of the obstacle
     * @param world              The world to create the obstacle in
     * @param textureFile        The texture/sprite of the obstacle
     */
    public Obstacle(int horizontalVelocity, int verticalVelocity, int damageDealt, int x, int y, int width, int height,
            World world, String textureFile) {

        super(x, y, width, height, world, textureFile);

        // Set obstacle characteristics
        this.damageDealt = damageDealt;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;

    }

    /**
     * Updates horizontal velocity
     * 
     * @param vel The velocity value to set
     */
    public void setHorizontalVel(int vel) {

        // Sets class variable to passed value and then runs updateMovement() method
        this.horizontalVelocity = vel;
        this.updateMovement();

    }

    /**
     * Updates vertical veolcity
     * 
     * @param vel The velocity value to set
     */
    public void setVerticalVel(int vel) {

        // Sets class variable to passed value and then runs updateMovement() method
        this.verticalVelocity = vel;
        this.updateMovement();

    }

    /**
     * Sets a linear velocity using the obstacles horiontal and vertical velocity
     */
    public void updateMovement() {

        // Sets obstacle velocity using class variables
        if (this.bBody != null) {
            this.bBody.setLinearVelocity(this.horizontalVelocity, this.verticalVelocity);
        }

    }

    /**
     * Function to check if obstacle has left the boundaries of the screen behind
     * the player and returns a boolean result
     * 
     * @param playerX The x coordinate of the player
     * @return true if obstacle has left the player's view, false otherwise
     */
    public boolean isOffScreen(int playerX) {

        float x = this.getPosition().x * DragonBoatRace.scale;
        float screenWidth = Gdx.graphics.getWidth();

        if (x < playerX - screenWidth / 2) {
            System.out.println("Obstacle has left the screen");
            return true;
        } else {
            return false;
        }

    }

    /**
     * Reinstantiate function that takes parameters for position of moved obstacle
     * 
     * @param x The new x coordinate
     * @param y The new y coordinate
     */
    public void reinstantiate(int x, int y) {

        this.bBody.getTransform().setPosition(new Vector2((float) x, (float) y));

    }

    /**
     * Reinstantiate function that sets position of obstacle based on position of
     * player
     * 
     * @param playerX The player's x coordinate
     */
    public void reinstantiate(int playerX) {

        float screenWidth = Gdx.graphics.getWidth();
        float newX = (float) playerX + screenWidth / 2 + 4f;

        this.bBody.getTransform().setPosition(new Vector2(newX, this.getPosition().y));

    }

}