package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Boat {

	// Game scaling
	static float scale = 16;
	
	// Boat visuals and physics
	Body bBody;
	Texture sprite;

	// Boat characteristics
	float mvmntSpeed = 0;
	float maxSpeed = 5;
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
	public Boat(int x, int y, int width, int height, boolean isStatic, int health,
						int stamina, float acceleration, float maneuverability, World world) {

		// Creates the box2d body in the game world
		this.bBody = createBox(x, y, width, height, isStatic, world);

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
	 * Generates a box2d body for the boat
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @param world
	 * @return
	 */
    public Body createBox(int x, int y, int width, int height, boolean isStatic, World world) {

		// Creates a body and body definition (properties for a body)
		Body body;
		BodyDef def = new BodyDef();

		// Allows the programmer to define whether a body is static or not
		if(isStatic) {
			def.type = BodyDef.BodyType.StaticBody;
		} else {
			def.type = BodyDef.BodyType.DynamicBody;
		}

		// Sets the position of the boat's body according to the scale of the game
		def.position.set(x/scale, y/scale);

		// Fixes the rotation of the boat
		def.fixedRotation = true;

		// Adds the boat body to the game world
		body = world.createBody(def);

		// Sets the shape of the body to be a box polygon
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2/scale, height/2/scale);

		// Fixes the box to the body
		body.createFixture(shape, 1.0f);

		// Disposes of the used shape
		shape.dispose();
		return body;
	}

	/**
	 * Returns the position of the boat
	 * 
	 * @return
	 */
	public Vector2 getPosition() {
		return this.bBody.getPosition();
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
