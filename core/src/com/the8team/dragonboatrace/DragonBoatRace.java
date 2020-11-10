package com.the8team.dragonboatrace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

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

	// Sprite rendering
	SpriteBatch batch;

	// Music
	Sound startMusic;
	
	@Override
	public void create () {
		// Get height and width of window for camera
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Create a new top-down camera to fit the window
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);

		// Create a box2d world to hold the objects
		world = new World(new Vector2(0,0), false);
		dr = new Box2DDebugRenderer();

		// Create music
		startMusic = Gdx.audio.newSound(Gdx.files.internal("sound/Race.wav"));
        startMusic.play(0.2f);

		// Create the player
		player = new Player(700, 340, 48, 16, 100, 10, 10, 100f, 2.0f, world, "sprites/boat.png");

		// Create a sprite batch for rendering objects
		batch = new SpriteBatch();

		// Load in the tilemap
		map = new TmxMapLoader().load("map/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);

		// Parse the tilemap for collision objects/boundaries
		TiledObjects.parseTiledObjectLayer(world, map.getLayers().get("Collision").getObjects());

	}

	@Override
	public void render () {

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
		player.draw(batch);
		batch.end();

		// Debug renderer
		dr.render(world, camera.combined.scl(scale));

		// Allows game to be quit with escape key
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}

	// Potential resize function if game brief changes
	//@Override
	//public void resize(int width, int height) {
	//	camera.setToOrtho(false, width, height);
	//}
	
	/**
	 * Disposes of objects for efficiency
	 */
	@Override
	public void dispose () {
		world.dispose();
		dr.dispose();
		batch.dispose();
		tmr.dispose();
		map.dispose();
		startMusic.dispose();
	}

	/**
	 * Updates the game logic
	 * 
	 * @param delta
	 */
	public void update(float delta) {

		// Takes a time step for the collisions detection, physics etc.
		// Should *NOT* use delta as time step, should be constant (target framerate)
		world.step(1/60f, 6, 2);

		// Checks for player input
		player.inputUpdate(delta);

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
		position.y = 720/2;
		camera.position.set(position);

		camera.update();
	}


}
