package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

public class Boat extends MovingObject{

	// Boat characteristics
    int health;
    int stamina;
    float acceleration;
	float maneuverability;

	// Time tracking
	float fastestTime;

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
		this.mvmntSpeed += horizontalForce * this.acceleration * delta;

		// Stops the boat from going backwards
		if(this.mvmntSpeed < 0) {
			this.mvmntSpeed = 0;
		
		// Caps the boats maximum speed
		} else if(this.mvmntSpeed > this.maxSpeed) {
			this.mvmntSpeed = this.maxSpeed;
		}

		// Sets the boats velocity
		this.bBody.setLinearVelocity(this.mvmntSpeed, verticalForce * this.maneuverability);
	}
}
