package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MovingObject {

	// Game scaling
	static float scale = 16;
	
	// Visuals and physics
	Body bBody;
	Texture sprite;

	// Characteristics
	float mvmntSpeed = 0;
	float maxSpeed = 5;
	int initialX;
	int initialY;


	public MovingObject(int x, int y, int width, int height, int maxSpeed, boolean isStatic, World world, String textureFile) {

		// Creates the box2d body in the game world
        this.bBody = createBox(x, y, width, height, isStatic, world);
        this.sprite = new Texture(textureFile);
		this.maxSpeed = maxSpeed;
		this.initialX = x;
		this.initialY = y;
		this.bBody.setUserData(this);
	}
	
	/**
	 * Draws the sprite for the object on screen
	 * 
	 * @param batch
	 */
	public void draw(Batch batch) {
		// Attaches sprite to the bottom left of the body
		batch.draw(sprite, this.getPosition().x * scale - (this.sprite.getWidth()/2), this.getPosition().y * scale - (this.sprite.getHeight()/2));
	}

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

		// Sets the position of the body according to the scale of the game
		def.position.set(x/scale, y/scale);

		// Fixes the rotation of the object
		def.fixedRotation = true;

		// Adds the body to the game world
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
	 * Returns the position of the object
	 * 
	 * @return
	 */
	public Vector2 getPosition() {
		return this.bBody.getPosition();
	}
	
	public void removeCollision() {
		for(Fixture fixture : this.bBody.getFixtureList()) {
			this.bBody.destroyFixture(fixture);
		}
	}

}
