package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;

public class Obstacle extends MovingObject implements Steerable<Vector2> {

    public int damageDealt;
    private int horizontalVelocity;
    private int verticalVelocity;
    boolean tagged;
    float boundingRadius;

    public Obstacle(int horizontalVelocity, int verticalVelocity, int damageDealt, int x, int y, int width, int height,
            int maxSpeed, boolean isStatic, World world, String textureFile, Steerable < Vector2 > owner) {

        super(x, y, width, height, maxSpeed, isStatic, world, textureFile);
        this.damageDealt = damageDealt;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
        boolean tagged=false;
        float boundingRadius = 10;
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



    //interface fot steering

    @Override
    public Vector2 getLinearVelocity() {
        return this.bBody.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return this.bBody.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return this.boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged=tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxSpeed=maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
    }

    @Override
    public float getMaxAngularSpeed() {
        return this.maxSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxSpeed=maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
    }

    @Override
    public float getOrientation() {
        return this.bBody.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        this.bBody.setTransform(this.bBody.getPosition(),orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x,vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x=-(float)Math.sin(angle);
        outVector.y=(float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

}