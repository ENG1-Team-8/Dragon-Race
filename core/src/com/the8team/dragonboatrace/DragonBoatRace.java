package com.the8team.dragonboatrace;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	Opponent[] opponents = new Opponent[5];
	Obstacle[] obs = new Obstacle[10];

	// Sprite rendering
	SpriteBatch batch;
	ShapeRenderer healthBar, staminaBar;

	// Music
	Sound startMusic;

	// Objects to delete
	static ArrayList<MovingObject> toDelete = new ArrayList<MovingObject>();
	
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
		world.setContactListener(new b2ContactListener());
		dr = new Box2DDebugRenderer();

		// Create music
		startMusic = Gdx.audio.newSound(Gdx.files.internal("sound/Race.wav"));
        startMusic.play(0.2f);

		// Create the player
		player = new Player(700, 224, 48, 16, 100, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");
		
		// Create the opponenets
		opponents[0] = new Opponent(700, 128, 48, 16, 100, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");
		opponents[1] = new Opponent(700, 320, 48, 16, 100, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");
		opponents[2] = new Opponent(700, 416, 48, 16, 100, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");
		opponents[3] = new Opponent(700, 512, 48, 16, 100, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");
		opponents[4] = new Opponent(700, 608, 48, 16, 100, 10, 1000, 5f, 2.0f, world, "sprites/purple_boat.png");


		// Obstacle test
		for(int i=0;i<9;i++){
			obs[i] = new Branch(-3, 0, 2, 1200, 340, 64, 16, 8, false, world, "sprites/branch.png");
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

		//Draw opponents
		for(int i=0;i<5;i++){
			opponents[i].draw(batch);
		}

		// Draw obstacles
		for(int i=0;i<9;i++){
			obs[i].draw(batch);
		}

		batch.end();

		uiUpdate();


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

		// Deletes physics objects which are added to the delete list
		if(toDelete.size() > 0) {
			for (MovingObject obj : toDelete) {
				obj.removeCollision();
			}
			toDelete.clear();
		}

		// Checks for player input & update movements
		player.inputUpdate(delta);

		// Obstacle test
		for(int i=0;i<9;i++){
			obs[i].updateMovement();
		}
    
    	//reduces or adds stamina based on movement speed
		player.updateStamina();

		
		//checks if the player is outside the lane
		player.inLane();

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

	public void uiUpdate() {
		healthBar.begin(ShapeRenderer.ShapeType.Filled);
		healthBar.rect(615, (player.getPosition().y * 16) + 16, player.health*5, 5);
		healthBar.end();
		staminaBar.begin(ShapeRenderer.ShapeType.Filled);
		staminaBar.rect(615, (player.getPosition().y * 16) + 10, player.stamina/20, 5);
        staminaBar.end();
	}


}
