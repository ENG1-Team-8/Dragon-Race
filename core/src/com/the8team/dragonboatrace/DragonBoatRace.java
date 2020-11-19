package com.the8team.dragonboatrace;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class DragonBoatRace extends ApplicationAdapter {

	// Camera and map
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer tmr;
	TiledMap map;

	// Game scale
	static float scale = 16;

	// Box2d and objects
	Box2DDebugRenderer dr;
	World world;
	Player player;
	Opponent[] opponents;
	ArrayList<Obstacle> obs, lateObs;

	// Sprite rendering
	SpriteBatch batch;
	ShapeRenderer healthBar, staminaBar;
	Texture brokenScreen, endScreen;

	// Leg counting and timing
	int leg;
	float timer;

	// Music
	Sound startMusic;

	// Objects to delete
	static ArrayList<MovingObject> toDelete = new ArrayList<MovingObject>();

	// Random number genertion
	Random random = new Random();

	@Override
	public void create() {

		// Get height and width of window for camera
		Gdx.graphics.setWindowedMode(1280, 720);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Create a new top-down camera to fit the window
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);

		// Set the leg
		leg = 1;

		// Create a box2d world to hold the objects
		world = new World(new Vector2(0, 0), false);
		world.setContactListener(new b2ContactListener());
		dr = new Box2DDebugRenderer();

		// Create music
		startMusic = Gdx.audio.newSound(Gdx.files.internal("sound/Race.wav"));
		// startMusic.play(0.2f);

		// Create the player
		player = new Player(720, 224, 50, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");

		// Create the opponenets
		opponents = new Opponent[5];
		opponents[0] = new Opponent(720, 128, 100, 10, 1000, 5f, 2.0f, world, "sprites/red_boat.png");
		opponents[1] = new Opponent(720, 320, 100, 10, 1000, 5f, 2.0f, world, "sprites/blue_boat.png");
		opponents[2] = new Opponent(720, 416, 100, 10, 1000, 5f, 2.0f, world, "sprites/green_boat.png");
		opponents[3] = new Opponent(720, 512, 100, 10, 1000, 5f, 2.0f, world, "sprites/yellow_boat.png");
		opponents[4] = new Opponent(720, 608, 100, 10, 1000, 5f, 2.0f, world, "sprites/pink_boat.png");

		// Obtsacle list creation
		obs = new ArrayList<Obstacle>();
		lateObs = new ArrayList<Obstacle>();

		// Random obstacle placement
		// Between x:700 and y:16 or y:704
		for (int i = 0; i < 25 * leg; i++) {
			obs.add(new Branch(-(1 + random.nextInt(4)), 1000 + random.nextInt(5340), 80 + random.nextInt(561), world));
			obs.add(new Goose(-(1 + random.nextInt(4)), 2, 1000 + random.nextInt(5340), 80 + random.nextInt(561), world));
		}

		// Creation of late game obstacles
		for (int i = 0; i < 10 * leg; i++) {
			lateObs.add(new Branch(-(1 + random.nextInt(4)), 4930 + random.nextInt(1410), 80 + random.nextInt(561), world));
		}

		// Create a sprite batch for rendering objects
		batch = new SpriteBatch();

		// Load in the tilemap
		map = new TmxMapLoader().load("map/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);

		// Parse the tilemap for collision objects/boundaries
		TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("Collision").getObjects());

		// Create ui elements
		healthBar = new ShapeRenderer();
		healthBar.setColor(1, 0, 0, 0);
		staminaBar = new ShapeRenderer();
		staminaBar.setColor(0, 1, 0, 0);

		// Create the screens
		brokenScreen = new Texture("screens/brokenScreen.png");
		endScreen = new Texture("screens/endScreen.png");

		// Set the timer
		timer = 0f;

	}

	public void reset() {

		player.reset();
		for (Opponent opponent : opponents) {
			opponent.reset();
		}
		resetObstacles();
		timer = 0f;

	}

	@Override
	public void render() {

		if (leg == 3) {
			batch.begin();
			batch.draw(endScreen, camera.position.x - (1280 / 2), camera.position.y - (720 / 2));
			batch.end();
			return;
		}

		// Checks if the player has broken their boat and displays broken screen
		if (player.broken) {
			batch.begin();
			batch.draw(brokenScreen, camera.position.x - (1280 / 2), camera.position.y - (720 / 2));
			batch.end();
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
				create();
			return;
		}

		// Updates game logic
		update(Gdx.graphics.getDeltaTime());

		// Clear the screen and render the map
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tmr.render();

		// Uses sprite batch to render object textures
		// Use draw functions here
		batch.begin();

		// Draw obstacles
		for (Obstacle obstacle : obs) {
			obstacle.draw(batch);
		}

		// late game obstacle
		for (Obstacle obstacle : lateObs) {
			obstacle.draw(batch);
		}

		// Draw player
		player.draw(batch);

		// Draw opponents
		for (int i = 0; i < 5; i++) {
			opponents[i].draw(batch);
		}

		batch.end();

		// Update the ui
		uiUpdate();

		// Debug renderer
		dr.render(world, camera.combined.scl(scale));

		// Allows game to be quit with escape key
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	/**
	 * Updates the game logic
	 * 
	 * @param delta
	 */
	public void update(float delta) {

		// Takes a time step for the collisions detection, physics etc.
		// Should *NOT* use delta as time step, should be constant (target framerate)
		world.step(1 / 60f, 6, 2);
		updateCollisionBodies();

		// Increments timer
		timer += delta;

		// Checks for player input & update movements
		player.inputUpdate(delta);

		// Obstacle movement
		for (Obstacle obstacle : obs) {
			obstacle.updateMovement();
		}

		// Late game obstacle movement
		if (player.lateGame()) {
			for (Obstacle obstacle : lateObs) {
				obstacle.updateMovement();
			}
		}

		// Reduces or adds stamina based on movement speed
		player.updateStamina();

		// Updates the leg
		updateLeg(timer);

		// Updates the camera
		cameraUpdate(delta);
		tmr.setView(camera);

		// Updates the projection matrix for the sprite batch
		batch.setProjectionMatrix(camera.combined);

	}

	/**
	 * Updates the camera
	 * 
	 * Tracks the players x-position and keeps the full height of the map in frame
	 * 
	 * @param delta
	 */
	public void cameraUpdate(float delta) {
		Vector3 position = camera.position;

		// Take the players position correctly scaled
		position.x = player.getPosition().x * scale;

		// Follow the y-centre of the map
		position.y = 720 / 2;
		camera.position.set(position);

		camera.update();
	}

	public void uiUpdate() {
		healthBar.begin(ShapeRenderer.ShapeType.Filled);
		healthBar.rect(615, (player.getPosition().y * 16) + 16, player.health * 5, 5);
		healthBar.end();
		staminaBar.begin(ShapeRenderer.ShapeType.Filled);
		staminaBar.rect(615, (player.getPosition().y * 16) + 10, player.stamina / 20, 5);
		staminaBar.end();
	}

	public void updateCollisionBodies() {
		// Deletes physics objects which are added to the delete list
		if (toDelete.size() > 0) {
			for (MovingObject obj : toDelete) {
				obj.removeCollision();
			}
			toDelete.clear();
		}
	}

	public void updateLeg(float timer) {
		if (leg == 1) {
			timer = 10000;
		} else if (leg == 2) {
			if (player.isFinished(timer)) {
				System.out.println("final time: " + player.fastestTime);
				leg += 1;
				return;
			}
		}
		if (player.isFinished(timer)) {
			System.out.println(player.fastestTime);
			leg += 1;
			reset();
		}
	}

	public void resetObstacles() {

		for (Obstacle obstacle : obs) {
			if (obstacle.bBody != null) {
				obstacle.removeCollision();
			}
		}

		for (Obstacle obstacle : lateObs) {
			if (obstacle.bBody != null) {
				obstacle.removeCollision();
			}
		}

		obs.clear();
		lateObs.clear();

		// Recreation of obstacles
		for (int i = 0; i < 25 * leg; i++) {
			obs.add(new Branch(-(1 + random.nextInt(4)), 1000 + random.nextInt(5340), 80 + random.nextInt(561), world));
			obs.add(new Goose(-(1 + random.nextInt(4)), 2, 1000 + random.nextInt(5340), 80 + random.nextInt(561), world));
		}

		// Recreation of late game obstacles
		for (int i = 0; i < 10 * leg; i++) {
			lateObs.add(new Branch(-(1 + random.nextInt(4)), 4930 + random.nextInt(1410), 80 + random.nextInt(561), world));
		}
	}

	/**
	 * Disposes of objects for efficiency
	 */
	@Override
	public void dispose() {
		world.dispose();
		dr.dispose();
		batch.dispose();
		tmr.dispose();
		map.dispose();
		startMusic.dispose();
	}

}
