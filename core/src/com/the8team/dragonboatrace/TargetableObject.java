package com.the8team.dragonboatrace;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A targetable object that the AI can aim for.
 * 
 * @author Ionut Manasia
 */
public class TargetableObject extends MovingObject implements Steerable<Vector2> {

    // For AI
    boolean tagged;
    float boundingRadius;

    /**
     * Construct a targetable object.
     * 
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param width  The width
     * @param height The height
     * @param world  The world n which to create the object
     */
    public TargetableObject(int x, int y, int width, int height, World world) {

        // Uses generic sprite for now as not drawn in this case
        super(x, y, width, height, world, "sprites/branch.png");
        this.tagged = false;
        this.boundingRadius = 100;
    }

    // Interface overrides

    @Override
    public Vector2 getLinearVelocity() {
        return this.getBody().getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return this.getBody().getAngularVelocity();
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
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.01f;
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
        this.maxSpeed = maxLinearSpeed;
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
        // this.maxSpeed=maxAngularSpeed;
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
        return this.getBody().getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        this.getBody().setTransform(this.getBody().getPosition(), orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

}
