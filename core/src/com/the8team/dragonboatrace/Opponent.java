package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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
	public Opponent(int x, int y, int width, int height, boolean isStatic, int health,
					int stamina, float acceleration, float maneuverability, World world) {
		super(x, y, width, height, isStatic, health, stamina, acceleration, maneuverability, world);
		this.sprite = new Texture("sprites/boat.png");
    }

    public void move(float delta) {
        this.updateMovement(1, 0, delta);
    }

}
