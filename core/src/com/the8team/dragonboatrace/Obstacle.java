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

        // Sets boat velocity using class variables
        this.bBody.setLinearVelocity(this.horizontalVelocity, this.verticalVelocity);

    }

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

}