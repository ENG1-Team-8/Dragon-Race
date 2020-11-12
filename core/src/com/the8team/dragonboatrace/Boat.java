package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

public class Boat extends MovingObject{

	// Boat characteristics
    int health;
    float stamina;
    float acceleration;
	float maneuverability;

	// Time tracking
	float fastestTime;

	//lane check
	float yMax;
	float yMin;

	//is boat slowed
	Boolean slowed;

	//is boat out of stamina
	Boolean outOfStamina;

	/**
	 * Constructs a boat object
	 * 
	 * Takes a world object to create a box2d body within the world
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
	public Boat(int x, int y, int width, int height, int maxSpeed, int health,
						int stamina, float acceleration, float maneuverability, World world, String textureFile) {

		super(x, y, width, height, maxSpeed, false, world, textureFile);

		// Sets other relevant properties
		this.health = health;
		this.stamina = stamina;
		this.acceleration = acceleration;
		this.maneuverability = maneuverability;
		this.yMin = y - 40;
		this.yMax = y + 40;
		this.outOfStamina = false;

	}
	
	/**
	 * Draws the sprite for the boat on screen
	 * 
	 * @param batch
	 */
	public void draw(Batch batch) {
		// Attaches sprite to the bottom left of the boats body
		batch.draw(sprite, this.getPosition().x * scale - (this.sprite.getWidth()/2), this.getPosition().y * scale - (this.sprite.getHeight()/2));
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
		if(this.mvmntSpeed < 0) {
			this.mvmntSpeed = 0;
		}
		//checks if the boat is out of stamina
		else if(this.outOfStamina){
			this.mvmntSpeed = 0;
		}
		//checks if the boat should be slowed
		else if(this.mvmntSpeed > this.maxSpeed/2 && this.slowed){
			this.mvmntSpeed = this.maxSpeed/2;
		}
		// Caps the boats maximum speed
		else if(this.mvmntSpeed > this.maxSpeed) {
			this.mvmntSpeed = this.maxSpeed;
		}
		// Sets the boats velocity
		this.bBody.setLinearVelocity(this.mvmntSpeed, verticalForce * (this.maneuverability*(stamina/1000)));
	}

	//checks if a boat is in the correct lane
	public void inLane(){
		this.slowed = false;
		if ((this.getPosition().y)*16 > yMax || (this.getPosition().y)*16 < yMin){
			this.slowed = true;
		}
	}

	//runs every frame, updates stamina based on movement speed
	public void updateStamina(){
		//if movement speed is positive reduce stamina
		if(this.mvmntSpeed > 0){
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
}
