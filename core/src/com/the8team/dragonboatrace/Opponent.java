package com.the8team.dragonboatrace;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An opponent boat with AI steering.
 * 
 * @author Ionut Manasia
 * @see Boat
 */
public class Opponent extends Boat implements Steerable<Vector2> {

	// For AI
	boolean tagged;
	float orientation, boundingRadius;

	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steerOutput;

	/**
	 * Construct an opponent manually, for testing purposes.
	 * 
	 * @param x               The starting x coordinate
	 * @param y               The starting y coordinate
	 * @param maxSpeed        The maximum speed the opponent can go
	 * @param health          The health of the opponent
	 * @param stamina         The stamina of the opponent
	 * @param acceleration    The acceleration of the opponent
	 * @param maneuverability The maneuverability of the opponent
	 * @param world           The world in which to create the opponent
	 * @param name            The name of the opponent
	 */
	public Opponent(int x, int y, int maxSpeed, int health, int stamina, float acceleration, float maneuverability,
			World world, String name) {
		super(x, y, maxSpeed, health, stamina, acceleration, maneuverability, world, name);

		// For AI
		this.setMaxLinearSpeed(maxSpeed);
		this.setMaxAngularSpeed(maxSpeed);
		this.orientation = this.bBody.getAngle();
		this.tagged = false;
		this.boundingRadius = 48;
		this.steerOutput = new SteeringAcceleration<>(new Vector2());
	}

	/**
	 * Construct an opponent using a static boat definition.
	 * 
	 * @param boat  An object array of characteristics (pre-made in Boat class)
	 * @param world The world in which to create the opponent
	 */
	public Opponent(Object[] boat, World world) {

		// Creates a Boat with relevant attributes
		super((int) boat[0], (int) boat[1], (int) boat[2], (int) boat[3], (int) boat[4], (float) boat[5],
				(float) boat[6], world, (String) boat[7]);
		this.setMaxLinearSpeed((int) boat[2]);
		this.setMaxAngularSpeed((int) boat[2]);
		this.orientation = this.bBody.getAngle();
		this.tagged = false;
		this.boundingRadius = 48;
		this.steerOutput = new SteeringAcceleration<>(new Vector2());

	}

	/**
	 * Updates the behaviour and stamina based on delta.
	 * 
	 * @param delta The delta time between frames
	 */
	public void update(float delta) {
		this.updateStamina();
		if (behavior != null) {
			behavior.calculateSteering(steerOutput);
			applySteering(delta);
		}
	}

	/**
	 * Applies steering to opponent by updating movement.
	 * 
	 * @param delta The delta time between frames
	 */
	private void applySteering(float delta) {
		if (this.steerOutput.linear.x > 0) {
			this.updateMovement(1, 0, delta);
		} else {
			this.updateMovement(0, (int) this.steerOutput.linear.y / 4, delta);
		}
		if (this.steerOutput.angular != 0) {
			if (!this.steerOutput.linear.isZero()) {
				this.updateMovement(1, (int) (this.steerOutput.linear.y + this.steerOutput.angular), delta);
			} else {
				this.updateMovement(0, (int) this.steerOutput.angular, delta);
			}
		}
	}

	/**
	 * Checks for an obstacle in front of the opponent and adapts movement in
	 * accordance to avoid it
	 * 
	 * @param obstacle The obstacle to avoid
	 * @param delta    The delta time between frames
	 */
	public void avoidObstacle(Obstacle obstacle, float delta) {

		if (obstacle.getPosition().x - this.getPosition().x <= 16
				&& obstacle.getPosition().x - this.getPosition().x > 0) {
			if (this.inLane()) {
				if (obstacle.getPosition().y - this.getPosition().y <= 4
						&& obstacle.getPosition().y - this.getPosition().y >= 0) {
					this.updateMovement(0, -1, delta);
				} else if (this.getPosition().y - obstacle.getPosition().y <= 4
						&& this.getPosition().y - obstacle.getPosition().y >= 0) {
					this.updateMovement(0, 1, delta);
				}
			}
		}

		// Stop it from getting out its lane
		if ((this.getPosition().y + 1) * Utils.scale >= yMax) {
			this.updateMovement(0, -1, delta);
		} else if ((this.getPosition().y - 1) * Utils.scale <= yMin) {
			this.updateMovement(0, 1, delta);
		}

		// Keep the boats in the first and last lane from getting stuck at the edge
		if (yMax >= 600) {
			if (((this.getPosition().y + 1) * Utils.scale) + 8 >= yMax) {
				this.updateMovement(0, -1, delta);
			}
		} else if (yMin <= 100) {
			if ((this.getPosition().y - 1) * Utils.scale - 8 <= yMin) {
				this.updateMovement(0, 1, delta);
			}
		}
	}

	/**
	 * Creates an arrive behaviour to reach the target.
	 * 
	 * @param target A steerable vector target (use targetable object)
	 */
	public void arriveAt(Steerable<Vector2> target) {
		this.setBehavior(new Arrive<>(this, target));
	}

	public void setBehavior(SteeringBehavior<Vector2> behavior) {
		this.behavior = behavior;
	}

	// Interface overrides

	// Interface for steering
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
		this.maxSpeed = (int) maxLinearSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		return this.acceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		this.acceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		return this.maxSpeed;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		this.maxSpeed = (int) maxAngularSpeed;
	}

	@Override
	public float getMaxAngularAcceleration() {
		return this.maneuverability;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		this.maneuverability = maxAngularAcceleration;
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