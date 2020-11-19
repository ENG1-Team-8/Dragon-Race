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
	 * @param maxSpeed
	 * @param health
	 * @param stamina
	 * @param acceleration
	 * @param maneuverability
	 * @param world
	 * @param textureFile
	 */
	public Player(int x, int y, int width, int height, int maxSpeed, int health,
					int stamina, float acceleration, float maneuverability, World world, String textureFile) {
		super(x, y, width, height, maxSpeed, health, stamina, acceleration, maneuverability, world, textureFile);
		this.sprite = new Texture(textureFile);
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

	//as all of the obstacles move there ends up not being any at the end
	//this detects when the player is near the end to spawn new obstacles
	public Boolean lateGame(){
		if(this.getPosition().x * DragonBoatRace.scale > 4930){
			return true;
		}
		else{
			return false;
		}
	}

}
