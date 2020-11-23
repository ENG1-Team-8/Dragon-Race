package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Used to instantiate a player entity, extends Boat.
 * 
 * @author Matt Tomlinson
 * @author Charlie Hayes
 * @see Boat
 */
public class Player extends Boat {

	// Boolean for if player has moved
	boolean started = false;

	/**
	 * The player constructor.
	 * <p>
	 * Uses the passed arguments to create a boat to represent the player.
	 * 
	 * @param x               The starting x coordinate of the player
	 * @param y               The starting y coordinate of the player
	 * @param maxSpeed        The maximum speed the player can go
	 * @param health          The health of the boat (robustness)
	 * @param stamina         The stamina that the player has
	 * @param acceleration    The acceleration
	 * @param maneuverability How many quickly the player can move in the y-axis
	 * @param world           The world to create the player in
	 * @param name            The texture of the player's boat
	 */
	public Player(int x, int y, int maxSpeed, int health, int stamina, float acceleration, float maneuverability,
			World world, String name) {
		super(x, y, maxSpeed, health, stamina, acceleration, maneuverability, world, name);
	}

	public Player(Object[] boat, World world) {

		// Creates a Boat with relevant attributes
		super((int) boat[0], (int) boat[1], (int) boat[2], (int) boat[3], (int) boat[4], (float) boat[5],
				(float) boat[6], world, (String) boat[7]);

	}

	/**
	 * Updates the forces applied to the player's boat according to their input.
	 * 
	 * @param delta The delta time between frames
	 */
	public void inputUpdate(float delta) {

		// Assumes force is 0 by default
		int horizontalForce = 0;
		int verticalForce = 0;

		// Applies a set force of 1 for each direction according to key press
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
			// Player has started
			this.started = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			verticalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			verticalForce += 1;
		}

		// Uses the updateMovement method
		this.updateMovement(horizontalForce, verticalForce, delta);

	}

	@Override
	public void reset() {

		// Reposition boat to initial position
		this.bBody.setTransform(this.initialX / Utils.scale, this.initialY / Utils.scale, 0);
		this.health = initialHealth;
		this.stamina = initialStamina;
		this.broken = false;
		this.mvmntSpeed = 0;

		// Player no longer started
		this.started = false;
	}

	/**
	 * Detects when player is near the end to start moving end game obstacles.
	 * 
	 * @return true if player is near the finish line, false otherwise
	 */
	public boolean lateGame() {
		if (this.getPosition().x * Utils.scale > 4930) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the player has started.
	 * 
	 * @return true if player has started, false otherwise
	 */
	public boolean isStarted() {
		return this.started;
	}
}
