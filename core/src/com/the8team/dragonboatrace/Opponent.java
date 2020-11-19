package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Opponent extends Boat implements Steerable<Vector2> {

	/**
	 * Constructs a player boat from the boat class
	 * <p>
	 * Creates a player and assigns a player texture
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @param health
	 * @param stamina
	 * @param acceleration
	 * @param maneuverability
	 * @param world
	 */

	boolean tagged;
	float orientation,boundingRadius;

	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steerOutput;


	public Opponent(int x, int y, int width, int height, int maxSpeed, int health,
					int stamina, float acceleration, float maneuverability, World world, String textureFile) {
		super(x, y, width, height, health, maxSpeed, stamina, acceleration, maneuverability, world, textureFile);
		this.setMaxLinearSpeed(maxSpeed);
		this.setMaxAngularSpeed(maxSpeed);
		this.orientation=this.bBody.getAngle();
		this.tagged=false;
		this.boundingRadius=100;
		this.steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
	}



	public void update(float delta)
	{
		this.updateStamina();
		if (behavior!=null) {
			behavior.calculateSteering(steerOutput);
			applySteering(delta);
		}
	}

	//don't ask about the numbers

	private void applySteering (float delta)
	{
		this.updateMovement((int)(this.steerOutput.linear.x/(4*acceleration/5)),(int)this.steerOutput.linear.y/4,delta);
		if (this.steerOutput.angular!=0)
		{
			if (!this.steerOutput.linear.isZero())
			{
				this.updateMovement((int)this.steerOutput.linear.x,(int)(this.steerOutput.linear.y+this.steerOutput.angular),delta);
			}
			else
			{
				this.updateMovement(0,(int)this.steerOutput.angular,delta);
			}
		}
	}

	public Body getBody()
	{
		return this.bBody;
	}

	public void arriveAt (Steerable<Vector2> target)
	{
		this.setBehavior(new Arrive<Vector2>(this,target));
	}

	//interface fot steering

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
		return this.acceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		this.acceleration=maxLinearAcceleration;
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
		return this.maneuverability;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		this.maneuverability=maxAngularAcceleration;
	}

	@Override
	public float getOrientation() {
		return this.getBody().getAngle();
	}

	@Override
	public void setOrientation(float orientation) {
		this.getBody().setTransform(this.getBody().getPosition(),orientation);
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

	public void setBehavior(SteeringBehavior<Vector2> behavior) {
		this.behavior = behavior;
	}

}