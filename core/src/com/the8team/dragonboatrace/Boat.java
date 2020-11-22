package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

/**
 * A boat entity extended from MovingObject
 * 
 * @author Matt Tomlinson
 * @author Charlie Hayes
 * @see MovingObject
 */
public class Boat extends MovingObject {

	//Boat selection
	static Object[] red = {720, 128, 50, 15, 1000, 5f, 2.0f, "sprites/red_boat.png"}; //more health
	static Object[] purple = {720, 224, 55, 10, 1000, 5f, 2.0f, "sprites/purple_boat.png"}; //higher max speed
	static Object[] blue = {720, 320, 50, 10, 1200, 5f, 2.0f, "sprites/blue_boat.png"};//higher stamina
	static Object[] green = {720, 416, 50, 10, 1000, 6f, 2.0f, "sprites/green_boat.png"};//higher acceleration
	static Object[] yellow = {720, 512, 50, 10, 1000, 5f, 2.5f, "sprites/yellow_boat.png"};//higher maneuverability
	static Object[] pink = {720, 608, 52, 12, 1100, 5.5f, 2.25f, "sprites/pink_boat.png"};//all rounder

	// Boat characteristics
	int health;
	int initialHealth;
	float stamina;
	float initialStamina;
	float acceleration;
	float maneuverability;
	int maxSpeed;

	// Time tracking set to initial high value
	float fastestTime = 10000000;

	// Lane check
	float yMax;
	float yMin;

	// Booleans
	Boolean outOfStamina = false, broken = false;

	/**
	 * Constructs a boat object
	 * <p>
	 * Takes a world object to create a box2d body within the world and store
	 * relevant characteristics.
	 * 
	 * @see Player
	 * @see Opponent
	 * 
	 * @param x               The starting x coordinate
	 * @param y               The starting y coordinate
	 * @param maxSpeed        The maximum speed of the boat
	 * @param health          The boat's health
	 * @param stamina         The stamina of the boat
	 * @param acceleration    The boat's acceleration
	 * @param maneuverability How quickly the boat can move side to side
	 * @param world           The world in which to create the boat
	 * @param textureFile     The texture location
	 */
	public Boat(int x, int y, int maxSpeed, int health, int stamina, float acceleration, float maneuverability,
			World world, String textureFile) {

		// Creates a MovingObject with relevant attributes
		super(x, y, 48, 16, world, textureFile);

		// Sets other relevant properties
		this.maxSpeed = maxSpeed;
		this.health = health;
		this.initialHealth = health;
		this.stamina = stamina;
		this.initialStamina = stamina;
		this.acceleration = acceleration;
		this.maneuverability = maneuverability;
		this.yMin = y - 56;
		this.yMax = y + 40;
	}

	/**
	 * Updates the movement properties of the boat
	 * <p>
	 * The forces take values 1 or 0 typically to represent whether or not a force
	 * is being applied.
	 * 
	 * @param horizontalForce Whether the boat is undergoing horizontal force (-1
	 *                        left, 1 right)
	 * @param verticalForce   Whether the boat is undergoing vertical force (-1
	 *                        down, 1 up)
	 * @param delta           The delta time between frames
	 */
	public void updateMovement(int horizontalForce, int verticalForce, float delta) {
		// Accelerates the boat over a set time regardless of framerate
		this.mvmntSpeed += horizontalForce * this.acceleration * delta;// * (stamina / 1000)) 

		// Stops the boat from going backwards or moving when broken
		if (this.mvmntSpeed < 0 || this.broken) {
			this.mvmntSpeed = 0;
			verticalForce = 0;
		}
		// checks if the boat is out of stamina
		else if (this.outOfStamina) {
			this.mvmntSpeed = 0;
		}
		// Caps the boats speed to its maximum speed
		else if (this.mvmntSpeed > this.maxSpeed) {
			this.mvmntSpeed = this.maxSpeed;
		}
		// Sets the boats velocity and checks if boat is in its lane
		if (this.inLane()) {
			this.bBody.setLinearVelocity(this.mvmntSpeed, verticalForce * (this.maneuverability * (stamina / 1000)));
		} else {
			// Applies halved velocity if out of lane to simulate a time penalty visually
			this.bBody.setLinearVelocity(this.mvmntSpeed / 2,
					verticalForce * (this.maneuverability * (stamina / 1000)));
		}
	}

	/**
	 * Checks if the boat is within its lane
	 * 
	 * 
	 * @return true if in lane, false otherwise
	 */
	public boolean inLane() {
		// If the boat exceeded its lane bounds...
		if ((this.getPosition().y) * Utils.scale > yMax || (this.getPosition().y) * Utils.scale < yMin) {
			return false;
		}
		return true;
	}

	/**
	 * Updates the boat's health
	 * <p>
	 * Subtracts the damage taken from the boat's health and sets broken=true if
	 * health less than or equal to 0
	 * 
	 * @param damage The damage to apply
	 */
	public void updateHealth(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			this.broken = true;
		}
	}

	/**
	 * Updates the boat's stamina based on it's speed
	 * 
	 */
	public void updateStamina() {
		// If movement speed is positive reduce stamina
		if (this.mvmntSpeed > 5) {
			this.stamina -= this.mvmntSpeed / 10;
		}
		// Otherwise add stamina but not over max
		else if (stamina < this.initialStamina) {
			this.stamina += 10;
		}
		// Tells updateMovement that the boat is out of stamina
		if (this.stamina <= 0) {
			this.outOfStamina = true;
		}
		// Tells updateMovement that the boat has stamina
		else {
			this.outOfStamina = false;
		}
	}

	/**
	 * Checks if the boat has finished the race
	 * <p>
	 * Compares boat's x position to the position of the finish line
	 * 
	 * @param timer The current leg's time
	 * 
	 * @return true if finished, false otherwise
	 */
	public boolean isFinished(float timer) {
		if (this.getPosition().x * Utils.scale > 6320) {
			// Updates the boat's fastest time
			this.fastestTime=Math.min(this.fastestTime, timer);
			return true;
		} else if (this.isBroken() == true) {
			// Does not update fastest time as DNF, just returns true
			return true;
		}
		return false;
	}

	/**
	 * Resets the boat to its initial position, stamina and health. Zeros speed.
	 */
	public void reset() {

		// Reposition boat to initial position
		this.bBody.setTransform(this.initialX / Utils.scale, this.initialY / Utils.scale, 0);
		this.health = initialHealth;
		this.stamina = initialStamina;
		this.broken = false;
		this.mvmntSpeed = 0;
	}

	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	public boolean isBroken() {
		return this.broken;
	}

	public float getFastestTime() {
		return this.fastestTime;
	}

	public void resetFastestTime() {
		this.fastestTime = 1000f;
	}

}
