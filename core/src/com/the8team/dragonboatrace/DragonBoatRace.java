package com.the8team.dragonboatrace;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DragonBoatRace extends Game {

	// Title Screen
	Skin gameSkin;
	Boolean play = false;

	// Camera and map
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer tmr;
	TiledMap map;

	// Box2d and objects
	Box2DDebugRenderer dr;
	World world;
	Player player;
	ArrayList<Opponent> opponents;
	TargetableObject finishLine;

	ArrayList<Obstacle> obs, lateObs;

	// Sprite rendering
	SpriteBatch batch, uiBatch;
	ShapeRenderer healthBar, staminaBar;
	Texture brokenScreen, endScreen, dnqScreen, ui;
	BitmapFont font;

	// Leg counting and timing
	int leg;
	float timer;
	boolean started, finished, dnq;

	// Music
	Music startMusic;

	// Objects to delete
	static ArrayList<MovingObject> toDelete = new ArrayList<>();

	// Random number generation
	Random random = new Random();

	@Override
	public void create() {

		// Display title screen
		gameSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
		this.setScreen(new TitleScreen(this));

		// Get height and width of window for camera
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Create a new top-down camera to fit the window
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);

		// Set the leg and booleans
		leg = 1;
		finished = false;
		dnq = false;

		// Create a box2d world to hold the objects
		world = new World(new Vector2(0, 0), false);
		world.setContactListener(new b2ContactListener());
		dr = new Box2DDebugRenderer();

		// Create music
		startMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/Race.wav"));
		startMusic.setLooping(true);
		startMusic.setVolume(0.25f);
		startMusic.play();

		// Creat the finish line for AI to track
		finishLine = new TargetableObject(6380, 412, 16, 560, world);

		// Obstacle list creation
		obs = new ArrayList<>();
		lateObs = new ArrayList<>();

		// Random obstacle placement
		// Between x:700 and y:16 or y:704
		for (int i = 0; i < 15 * leg; i++) {
			obs.add(new Branch(-(1 + random.nextInt(4)), 1000 + random.nextInt(5340), 80 + random.nextInt(561), world));
			obs.add(new Goose(-(1 + random.nextInt(4)), 2, 1000 + random.nextInt(5340), 80 + random.nextInt(561),
					world));
		}

		// Creation of late game obstacles
		for (int i = 0; i < 5 * leg; i++) {
			lateObs.add(
					new Branch(-(1 + random.nextInt(4)), 4930 + random.nextInt(1410), 80 + random.nextInt(561), world));
		}

		// Create a sprite batch for rendering objects
		batch = new SpriteBatch();
		uiBatch = new SpriteBatch();

		// Create the font
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(2);

		// Load in the tilemap
		map = new TmxMapLoader().load("map/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);

		// Parse the tilemap for collision objects/boundaries
		Utils.parseTiledObjectLayer(world, map.getLayers().get("Collision").getObjects());

		// Create ui elements
		healthBar = new ShapeRenderer();
		healthBar.setColor(1, 0, 0, 0);
		staminaBar = new ShapeRenderer();
		staminaBar.setColor(0, 1, 0, 0);

		// Create the ui
		brokenScreen = new Texture("ui/brokenScreen.png");
		endScreen = new Texture("ui/endScreen.png");
		dnqScreen = new Texture("ui/dnqScreen.png");
		ui = new Texture("ui/ui.png");

		// Set the timer
		timer = 0f;

	}

	/**
	 * Reset the player, opponents and obstacles.
	 */
	public void reset() {

		player.reset();
		for (Opponent opponent : opponents) {
			opponent.reset();
		}
		resetObstacles();

		// Reset timer
		timer = 0f;

	}

	@Override
	public void render() {
		// If the game is set to play
		if (!this.play) {
			screen.render(Gdx.graphics.getDeltaTime());
		} else {
			// Update finished screen
			if (updateFinished())
				return;

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

			// Draw late game obstacle
			for (Obstacle obstacle : lateObs) {
				obstacle.draw(batch);
			}

			// Draw player
			player.draw(batch);

			// Draw opponents
			for (Opponent opponent : opponents) {
				opponent.draw(batch);
			}

			batch.end();

			// Update broken player screen
			if (updateBroken())
				return;

			// Update the ui
			updateUI();

			// Debug renderer, enable to see object bodies
			// dr.render(world, camera.combined.scl(Utils.scale));

			// Allows game to be quit with escape key
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
				Gdx.app.exit();
		}
	}

	/**
	 * Updates the game logic.
	 * 
	 * @param delta The delta time between frames
	 */
	public void update(float delta) {

		// Takes a time step for the collisions detection, physics etc.
		// Should *NOT* use delta as time step, should be constant (target framerate)
		world.step(1 / 60f, 6, 2);
		updateCollisionBodies();

		// Increments timer
		if (player.isStarted())
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

		// Opponents' movement and behavior
		for (Opponent opponent : opponents) {
			if (!player.isStarted())
				opponent.mvmntSpeed = 0;
			opponent.update(delta);
			for (Obstacle obstacle : obs) {
				opponent.avoidObstacle(obstacle, delta);
			}
			for (Obstacle obstacle : lateObs) {
				opponent.avoidObstacle(obstacle, delta);
			}
		}

		// Reduces or adds stamina based on movement speed
		player.updateStamina();

		// Updates the leg
		updateLeg(timer);

		// Updates the camera
		updateCamera();
		tmr.setView(camera);

		// Updates the projection matrix for the sprite batch
		batch.setProjectionMatrix(camera.combined);

	}

	/**
	 * Updates the camera.
	 * <p>
	 * Tracks the players x-position and keeps the full height of the map in frame.
	 *
	 */
	public void updateCamera() {
		Vector3 position = camera.position;

		// Take the players position correctly scaled
		position.x = player.getPosition().x * Utils.scale;

		// Follow the y-centre of the map
		position.y = 720 / 2f;
		camera.position.set(position);

		camera.update();
	}

	/**
	 * Update ui elements.
	 */
	public void updateUI() {

		// Renders a rectangle with transformed player health as width
		healthBar.begin(ShapeRenderer.ShapeType.Filled);
		healthBar.rect(615, (player.getPosition().y * Utils.scale) + 16, player.health * 5, 5);
		healthBar.end();

		// Renders a rectangle with transformed player stamina as width
		staminaBar.begin(ShapeRenderer.ShapeType.Filled);
		staminaBar.rect(615, (player.getPosition().y * Utils.scale) + 10, player.stamina / 20, 5);
		staminaBar.end();

		// Draws the leg number and timer on the screen
		uiBatch.begin();
		uiBatch.draw(ui, 0, 0);
		font.draw(uiBatch, "Leg: " + Integer.toString(leg), 48, 685);
		font.draw(uiBatch, "Time: " + Float.toString((float) Math.round(timer * 100) / 100), 148, 685);
		uiBatch.end();
	}

	/**
	 * Remove physics bodies for each object in the toDelete list.
	 * <p>
	 * This method is required so that bodies are modified on world step, avoiding
	 * bugs and crashes.
	 */
	public void updateCollisionBodies() {
		// Deletes physics objects which are added to the delete list
		if (toDelete.size() > 0) {
			for (MovingObject obj : toDelete) {
				obj.removeCollision();
			}

			// Empty the toDelete list
			toDelete.clear();
		}
	}

	/**
	 * Check if all boats have reached the finish line and updates leg accordingly.
	 * 
	 * @param timer The current leg time
	 */
	public void updateLeg(float timer) {

		// If first leg, prevent fastest time from being overwritten (practice leg)
		if (leg == 1) {
			timer = 10000;
		}

		// Check for the opponents to have finished
		boolean opponentsFinished = true;
		for (Opponent opponent : opponents) {
			if (!opponent.isFinished(timer)) {
				opponentsFinished = false;
				continue;
			}
		}

		// Increment the leg and reset the gamestate
		if (player.isFinished(timer) && opponentsFinished) {
			leg += 1;
			// If final leg
			if (leg == 4) {
				// Sort the opponents in time order
				opponents.sort(Utils.boatSorter);
				// Check if player has qualified
				if (player.getFastestTime() < opponents.get(2).getFastestTime()) {
					for (int i = 0; i < 3; i++) {
						if (i <= 1) {
							// Reset fastest time for boats that qualify so can order first, second and
							// third place
							opponents.get(i).resetFastestTime();
						}
						// Remove opponents that did not qualify
						opponents.remove(opponents.size() - 1);
					}
					player.resetFastestTime();
					reset();
				} else {
					// If player did not qualify, finish the game
					finished = true;
					dnq = true;
				}
			} else if (leg == 5) {
				// If final leg has ended, do not reset gamestate and set finished to true
				finished = true;
			} else {
				reset();
			}
		}
	}

	/**
	 * Resets the obstacles in the game world.
	 */
	public void resetObstacles() {

		// Deletes bodies of all obstacles
		for (Obstacle obstacle : obs) {
			if (obstacle.getBody() != null) {
				obstacle.removeCollision();
			}
		}

		// Deletes bodies of all late game obstacles
		for (Obstacle obstacle : lateObs) {
			if (obstacle.getBody() != null) {
				obstacle.removeCollision();
			}
		}

		// Clear the lists of obstacles
		obs.clear();
		lateObs.clear();

		// Recreation of obstacles
		for (int i = 0; i < 15 * leg; i++) {
			obs.add(new Branch(-(1 + random.nextInt(4)), 1000 + random.nextInt(5340), 80 + random.nextInt(561), world));
			obs.add(new Goose(-(1 + random.nextInt(4)), 2, 1000 + random.nextInt(5340), 80 + random.nextInt(561),
					world));
		}

		// Recreation of late game obstacles
		for (int i = 0; i < 5 * leg; i++) {
			lateObs.add(
					new Branch(-(1 + random.nextInt(4)), 4930 + random.nextInt(1410), 80 + random.nextInt(561), world));
		}
	}

	/**
	 * Check if the race has finished.
	 * 
	 * @return true if race has finished, false otherwise
	 */
	public boolean updateFinished() {
		// Show the end screen if the last leg has been completed
		if (finished) {
			// Clear the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			tmr.render();

			// If player did not qualify, show the dnq screen
			if (dnq) {
				uiBatch.begin();
				uiBatch.draw(dnqScreen, 0, 0);
				uiBatch.end();
			} else {
				// Show position of final
				Boat first, second, third;
				opponents.sort(Utils.boatSorter);
				if (player.getFastestTime() < opponents.get(0).getFastestTime()) {
					first = player;
					second = opponents.get(0);
					third = opponents.get(1);
				} else if (player.getFastestTime() < opponents.get(1).getFastestTime()) {
					first = opponents.get(0);
					second = player;
					third = opponents.get(1);
				} else {
					first = opponents.get(0);
					second = opponents.get(1);
					third = player;
				}
				uiBatch.begin();
				uiBatch.draw(endScreen, 0, 0);
				font.setColor(Color.WHITE);
				font.getData().setScale(3f);
				font.draw(uiBatch, "First place: " + first.getName(), Gdx.graphics.getWidth() / 2 - 200,
						Gdx.graphics.getHeight() / 3);
				font.draw(uiBatch, "Second place: " + second.getName(), Gdx.graphics.getWidth() / 2 - 200,
						Gdx.graphics.getHeight() / 3 - 60);
				font.draw(uiBatch, "Third place: " + third.getName(), Gdx.graphics.getWidth() / 2 - 200,
						Gdx.graphics.getHeight() / 3 - 120);
				uiBatch.end();
			}

			// Exits the game if escape is pressed
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
				Gdx.app.exit();
			return true;
		}
		return false;
	}

	/**
	 * Displays the broken boat graphic if the player's boat has broken.
	 * 
	 * @return true if broken screen is showing, false otherwise
	 */
	public boolean updateBroken() {
		// Checks if the player has broken their boat and displays broken screen
		if (player.isBroken()) {
			uiBatch.begin();
			uiBatch.draw(brokenScreen, 0, 0);
			uiBatch.end();

			// Restarts the game if player presses enter key
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
				reset();
			return true;
		}
		return false;
	}

	/**
	 * Disposes of objects for efficiency.
	 */
	@Override
	public void dispose() {
		world.dispose();
		dr.dispose();
		batch.dispose();
		uiBatch.dispose();
		tmr.dispose();
		map.dispose();
		endScreen.dispose();
		dnqScreen.dispose();
		brokenScreen.dispose();
		ui.dispose();
		healthBar.dispose();
		staminaBar.dispose();
		font.dispose();
		startMusic.dispose();
	}

}
