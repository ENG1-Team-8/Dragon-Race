package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

public class Opponent extends Boat {

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
	public Opponent(int x, int y, int maxSpeed, int health,
					int stamina, float acceleration, float maneuverability, World world, String textureFile) {
		super(x, y, health, maxSpeed, stamina, acceleration, maneuverability, world, textureFile);
    }

    public void move(float delta) {
        this.updateMovement(1, 0, delta);
    }

}
