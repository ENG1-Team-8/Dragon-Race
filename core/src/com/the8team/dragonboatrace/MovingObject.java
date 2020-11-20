package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An instance of a moving object (parent of Boat and Obstacle)
 * 
 * @author Ionut Manasia
 * @author Matt Tomlinson
 * @see Boat
 * @see Obstacle
 */
public class MovingObject {

	// Game scaling
	static float scale = 16;

	// Visuals and physics
	Body bBody;
	Texture sprite;

	// Characteristics
	float mvmntSpeed = 0;
	float maxSpeed;
	int initialX;
	int initialY;
	int width;
	int height;

	/**
	 * Should be used as a super constructor for objects in the game or a generic
	 * moving object
	 * 
	 * @param x           The starting x coordinate
	 * @param y           The starting y coordinate
	 * @param width       The width of the object
	 * @param height      The height of the object
	 * @param world       The world in which to create the object
	 * @param textureFile The texture/sprite for the object
	 */
	public MovingObject(int x, int y, int width, int height, World world, String textureFile) {

		// Creates the box2d body in the game world
		this.bBody = createBox(x, y, width, height, false, world);
		this.sprite = new Texture(textureFile);

		// Initial values stored for resetting object
		this.initialX = x;
		this.initialY = y;
		this.width = width;
		this.height = height;

		// Used for obtaining MovingObject methods when object is involved in collision
		this.bBody.setUserData(this);
	}

	/**
	 * Draws the sprite for the object on screen
	 * 
	 * @param batch The spritebatch in which to draw the sprite
	 */
	public void draw(Batch batch) {
		// Checks that the object has a sprite/texture
		if (this.sprite != null) {
			// Attaches sprite to the bottom left of the body
			batch.draw(sprite, this.getPosition().x * scale - (this.sprite.getWidth() / 2),
					this.getPosition().y * scale - (this.sprite.getHeight() / 2));
		}
	}

	/**
	 * Creates a box2d body for the object
	 * <p>
	 * The body is used for physics, movement, object placement and collision
	 * 
	 * @param x        The x coordinate of the body
	 * @param y        The y coordinate of the body
	 * @param width    The width of the body
	 * @param height   The height of the body
	 * @param isStatic Whether the body is static (here for testing puropses and
	 *                 possibility of none-moving objects)
	 * @param world    The world to add the body to
	 * @return The new box2d body
	 */
	private Body createBox(int x, int y, int width, int height, boolean isStatic, World world) {

		// Creates a body and body definition (properties for a body)
		Body body;
		BodyDef def = new BodyDef();

		// Allows the programmer to define whether a body is static or not
		if (isStatic) {
			def.type = BodyDef.BodyType.StaticBody;
		} else {
			def.type = BodyDef.BodyType.DynamicBody;
		}

		// Sets the position of the body according to the scale of the game
		def.position.set(x / scale, y / scale);

		// Fixes the rotation of the object
		def.fixedRotation = true;

		// Adds the body to the game world
		body = world.createBody(def);

		// Sets the shape of the body to be a box polygon
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / scale, height / 2 / scale);

		// Fixes the box to the body
		body.createFixture(shape, 1.0f);

		// Disposes of the used shape
		shape.dispose();
		return body;
	}

	/**
	 * Gets the current position of the object
	 * 
	 * @return Position of object
	 */
	public Vector2 getPosition() {
		// Checks that the object has a body
		if (this.bBody != null) {
			return this.bBody.getPosition();
		}
		// Returns a (0, 0) position if there is no body
		return new Vector2(0, 0);
	}

	/**
	 * Destroys the objects body to remove from the game world and its collision
	 */
	public void removeCollision() {
		this.bBody.getWorld().destroyBody(this.bBody);
		this.bBody = null;
		this.sprite = null;
	}

}
