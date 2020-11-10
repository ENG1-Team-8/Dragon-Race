package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Boat {

	/**
	 * Constructs a player boat from the boat class
	 * 
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
	public Player(int x, int y, int width, int height, boolean isStatic, int health,
					int stamina, float acceleration, float maneuverability, World world) {
		super(x, y, width, height, isStatic, health, stamina, acceleration, maneuverability, world);
		this.sprite = new Texture("sprites/boat.png");
    }

	/**
	 * Updates the forces applied to the player's boat according to their input
	 * 
	 * @param delta
	 */
	public void inputUpdate(float delta) {

		// Assumes force is 0 by default
		int horizontalForce = 0;
		int verticalForce = 0;
		
		// Applies a set force of 1 for each direction according to key press
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce  -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			verticalForce  -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			verticalForce += 1;
		}

		// Uses the updateMovement method
		this.updateMovement(horizontalForce, verticalForce, delta);
		
	}

}
