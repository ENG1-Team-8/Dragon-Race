package com.the8team.dragonboatrace;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
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
		super(x, y, maxSpeed, health, stamina, acceleration, maneuverability, world, textureFile);
		this.setMaxLinearSpeed(maxSpeed);
		this.setMaxAngularSpeed(maxSpeed);
		this.orientation=this.bBody.getAngle();
		this.tagged=false;
		this.boundingRadius=48;
		this.steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
	}


	//updates the behavior and acceleration based on delta

	public void update(float delta)
	{
		this.updateStamina();
		if (behavior!=null) {
			behavior.calculateSteering(steerOutput);
			applySteering(delta);
		}
	}

	//don't ask

	private void applySteering (float delta)
	{
		if (this.steerOutput.linear.x>0) {
			this.updateMovement(1, 0, delta);
		}
		else
		{
			this.updateMovement(0, (int) this.steerOutput.linear.y / 4, delta);
		}
		if (this.steerOutput.angular!=0)
		{
			if (!this.steerOutput.linear.isZero())
			{
				this.updateMovement(1,(int)(this.steerOutput.linear.y+this.steerOutput.angular),delta);
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

	// create an arrive behavior to get to the finishline

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
		return this.maxSpeed;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		this.maxSpeed=(int)maxLinearSpeed;
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
		this.maxSpeed=(int)maxAngularSpeed;
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

	@Override
	public void reset() {
		// Reposition boat to initial position
		this.getBody().setTransform((this.initialX+32)/ 16, this.initialY / 16, 0);
		this.health = initialHealth;
		this.stamina = initialStamina;
		this.mvmntSpeed = 0;
		this.broken=false;
	}

	// a simple function that checks for an object in front of the opponent and makes it change it's position in accordance

	public void avoidObstacle (Obstacle obstacle, float delta)
	{

		if (this.inLane()) {
			if (obstacle.getPosition().y - this.getPosition().y <= 8 && obstacle.getPosition().y - this.getPosition().y >= 0) {
				this.updateMovement(0, -1, delta);
			} else if (this.getPosition().y - obstacle.getPosition().y <= 8 && this.getPosition().y - obstacle.getPosition().y >= 0) {
				this.updateMovement(0, 1, delta);
			}
		}

		//top it from getting out its lane

		if ((this.getPosition().y+1)*scale>=yMax)
		{
			this.updateMovement(0,-1,delta);
		}
		else if ((this.getPosition().y-1)*scale<=yMin)
		{
			this.updateMovement(0,1,delta);
		}

		if (yMin >=600)
		{
			if (((this.getPosition().y+1)*scale)+8>=yMax)
			{
				this.updateMovement(0,-1,delta);
			}
		}
		else if (yMin <=100)
		{
			if ((this.getPosition().y-1)*scale-8<=yMin)
			{
				this.updateMovement(0,1,delta);
			}
		}
	}

}