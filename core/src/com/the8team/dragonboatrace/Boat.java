package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

public class Boat extends MovingObject{

	// Boat characteristics
	int health;
	int initialHealth;
	float stamina;
	float initialStamina;
    float acceleration;
	float maneuverability;

	// Time tracking
	float fastestTime = 10000000;

	//lane check
	float yMax;
	float yMin;

	// Booleans
	Boolean outOfStamina= false, broken = false;

	/**
	 * Constructs a boat object
	 * <p>
	 * Takes a world object to create a box2d body within the world
	 * @see Player
	 * @see Opponent
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param maxSpeed
	 * @param health
	 * @param stamina
	 * @param acceleration
	 * @param maneuverability
	 * @param world
	 * @param textureFile
	 */
	public Boat(int x, int y, int width, int height, int maxSpeed, int health,
						int stamina, float acceleration, float maneuverability, World world, String textureFile) {

		super(x, y, width, height, maxSpeed, false, world, textureFile);

		// Sets other relevant properties
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
	 * 
	 * The forces take values 1 or 0 typically to represent whether or not a force is being applied
	 * 
	 * @param horizontalForce
	 * @param verticalForce
	 * @param delta
	 */
	public void updateMovement(int horizontalForce, int verticalForce, float delta) {
		// Accelerates the boat over a set time regardless of framerate
		this.mvmntSpeed += horizontalForce * (this.acceleration*(stamina/1000)) * delta;

		// Stops the boat from going backwards
		if(this.mvmntSpeed < 0 || this.broken) {
			this.mvmntSpeed = 0;
		}
		//checks if the boat is out of stamina
		else if(this.outOfStamina){
			this.mvmntSpeed = 0;
		}
		// Caps the boats maximum speed
		else if(this.mvmntSpeed > this.maxSpeed) {
			this.mvmntSpeed = this.maxSpeed;
		}
		// Sets the boats velocity and checks if boat is in its lane
		if(this.inLane()) {
			this.bBody.setLinearVelocity(this.mvmntSpeed, verticalForce * (this.maneuverability*(stamina/1000)));
		} else {
			this.bBody.setLinearVelocity(this.mvmntSpeed/2, verticalForce * (this.maneuverability*(stamina/1000)));
		}
	}

	//checks if a boat is in the correct lane
	public boolean inLane(){
		if ((this.getPosition().y)*16 > yMax || (this.getPosition().y)*16 < yMin){
			return false;
		}
		return true;
	}

	public void updateHealth(int damage) {
		this.health -= damage;
		if(this.health <= 0) {
			this.broken = true;
		}
	}

	//runs every frame, updates stamina based on movement speed
	public void updateStamina(){
		//if movement speed is positive reduce stamina
		if(this.mvmntSpeed > 5){
			this.stamina -= this.mvmntSpeed / 10;
		}
		//otherwise add stamina but not over max
		else{
			if(stamina < 1000){
				this.stamina+=10;
			}
		}
		//tells updateMovement that the boat is out of stamina
		if (this.stamina <= 0){
			this.outOfStamina = true;
		}
		//tells updateMovement that the boat has stamina
		else{
			this.outOfStamina = false;
		}
	}

	public boolean isFinished(float timer) {
		if (this.getPosition().x * DragonBoatRace.scale > 6320) {
			if (timer < this.fastestTime) {
				this.fastestTime = timer;
			}
			return true;
		}
		return false;
	}

	public void reset() {
		this.bBody.setTransform(this.initialX/16, this.initialY/16, 0);
		this.health = initialHealth;
		this.stamina = initialStamina;
		this.mvmntSpeed = 0;
	}
}
