package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;

public class Obstacle extends MovingObject {

    public int damageDealt;
    public int horizontalVelocity;
    public int verticalVelocity;

    public Obstacle(int horizontalVelocity, int verticalVelocity, int damageDealt, int x, int y, int width, int height,
            int maxSpeed, boolean isStatic, World world, String textureFile) {

        super(x, y, width, height, maxSpeed, isStatic, world, textureFile);
        this.damageDealt = damageDealt;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;

    }

    /**
     * Updates horizontal velocity
     * 
     * @param vel
     */

    public void setHorizontalVel(int vel) {

        // Sets class variable to passed value and then runs updateMovement() method
        this.horizontalVelocity = vel;
        this.updateMovement();

    }

    /**
     * Updates vertical veolcity
     * 
     * @param vel
     */

    public void setVerticalVel(int vel) {

        // Sets class variable to passed value and then runs updateMovement() method
        this.verticalVelocity = vel;
        this.updateMovement();

    }

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
     * @param playerX
     * @return
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
     * Reinstante function that takes parameters for position of moved obstacle
     * 
     * @param x
     * @param y
     */

    public void reinstantiate(int x, int y) {

        this.bBody.getTransform().setPosition(new Vector2((float) x, (float) y));

    }

    /**
     * Reinstantate function that sets position of obstacle based on position of
     * player
     * 
     * @param playerX
     */

    public void reinstantiate(int playerX) {

        float screenWidth = Gdx.graphics.getWidth();
        float newX = (float) playerX + screenWidth / 2 + 4f;

        this.bBody.getTransform().setPosition(new Vector2(newX, this.getPosition().y));

    }

}