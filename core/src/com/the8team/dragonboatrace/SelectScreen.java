package com.the8team.dragonboatrace;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * the boat selection screen displayed after the title screen 
 * 
 * @author Charlie Hayes
 */

public class SelectScreen implements Screen {
    private Stage stage;
    private DragonBoatRace game;

    public SelectScreen(DragonBoatRace dragonBoatRace) {
        game = dragonBoatRace;
        stage = new Stage(new ScreenViewport());

        //displays "Select your boat"
        Label title = new Label("Select your boat", game.gameSkin,"subtitle");
        title.setPosition(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*5/6);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        /*
        *   All comments will only be displayed once as there is a lot of repeated code
        */

        //displays the blue boat
        Texture blueTexture = new Texture(Gdx.files.internal("sprites/blue_boat.png"));
        Image blue = new Image(blueTexture);
        blue.setSize(blueTexture.getWidth()*3,blueTexture.getHeight()*3);

        //position done by eye
        blue.setPosition(Gdx.graphics.getWidth()/6-blue.getWidth()*1.25f,Gdx.graphics.getHeight()*2/3-blue.getHeight()/2);
        blue.setOrigin(blue.getWidth()/2,blue.getHeight()/2);
        blue.rotateBy(90);
        stage.addActor(blue);

        //display blue boat stats
        Label blueStats = new Label("Higher Stamina",game.gameSkin,"default");
        blueStats.setFontScale(0.9f);

        //the position of the stats display is different for every boat
        blueStats.setPosition(Gdx.graphics.getWidth()/6-blue.getWidth()*1.375f,Gdx.graphics.getHeight()*2/5);
        stage.addActor(blueStats);

        //Blue Select Button (see here for button comments so as not repeated)
        TextButton blueButton = new TextButton("Blue",game.gameSkin);
        blueButton.setWidth(blue.getWidth());
        blueButton.setPosition(Gdx.graphics.getWidth()/6-blue.getWidth()*1.3f,Gdx.graphics.getHeight()*2/3-blue.getHeight()*8);
        blueButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                //sets the player boat
                game.player = new Player(Boat.blue, game.world);

                //sets the opponents boats
                game.opponents = new ArrayList<Opponent>();
                game.opponents.add(0, new Opponent(Boat.red, game.world));
		        game.opponents.add(1, new Opponent(Boat.purple, game.world));
		        game.opponents.add(2, new Opponent(Boat.green, game.world));
		        game.opponents.add(3, new Opponent(Boat.yellow, game.world));
                game.opponents.add(4, new Opponent(Boat.pink, game.world));

                //sets where the opponents are trying to get to
                for (Opponent opponent : game.opponents) {
                    opponent.arriveAt(game.finishLine);
                }

                //starts the main sequence of the game
                game.play = true;
                stage.dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(blueButton);

        //displays the green boat
        Texture greenTexture = new Texture(Gdx.files.internal("sprites/green_boat.png"));
        Image green = new Image(greenTexture);
        green.setSize(greenTexture.getWidth()*3,greenTexture.getHeight()*3);
        green.setPosition((Gdx.graphics.getWidth()/6)*2-green.getWidth()*1.25f,Gdx.graphics.getHeight()*2/3-green.getHeight()/2);
        green.setOrigin(green.getWidth()/2,green.getHeight()/2);
        green.rotateBy(90);
        stage.addActor(green);

        //display green boat stats
        Label greenStats = new Label("Higher Acceleration",game.gameSkin,"default");
        greenStats.setFontScale(0.9f);
        greenStats.setPosition((Gdx.graphics.getWidth()/6)*2-green.getWidth()*1.55f,Gdx.graphics.getHeight()*2.3f/5);
        stage.addActor(greenStats);

        //green Select Button
        TextButton greenButton = new TextButton("green",game.gameSkin);
        greenButton.setWidth(green.getWidth());
        greenButton.setPosition((Gdx.graphics.getWidth()/6)*2-green.getWidth()*1.3f,Gdx.graphics.getHeight()*2/3-green.getHeight()*8);
        greenButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.player = new Player(Boat.green, game.world);
                game.opponents = new ArrayList<Opponent>();
                game.opponents.add(0, new Opponent(Boat.purple, game.world));
		        game.opponents.add(1, new Opponent(Boat.blue, game.world));
		        game.opponents.add(2, new Opponent(Boat.red, game.world));
		        game.opponents.add(3, new Opponent(Boat.yellow, game.world));
                game.opponents.add(4, new Opponent(Boat.pink, game.world));
                for (Opponent opponent : game.opponents) {
                    opponent.arriveAt(game.finishLine);
                }
                game.play = true;
                stage.dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(greenButton);        

        //displays the pink boat
        Texture pinkTexture = new Texture(Gdx.files.internal("sprites/pink_boat.png"));
        Image pink = new Image(pinkTexture);
        pink.setSize(pinkTexture.getWidth()*3,pinkTexture.getHeight()*3);
        pink.setPosition((Gdx.graphics.getWidth()/6)*3-pink.getWidth()*1.25f,Gdx.graphics.getHeight()*2/3-pink.getHeight()/2);
        pink.setOrigin(pink.getWidth()/2,pink.getHeight()/2);
        pink.rotateBy(90);
        stage.addActor(pink);

        //display pink boat stats
        Label pinkStats = new Label("All Rounder",game.gameSkin,"default");
        pinkStats.setFontScale(0.9f);
        pinkStats.setPosition((Gdx.graphics.getWidth()/6)*3-pink.getWidth()*1.25f,Gdx.graphics.getHeight()*2/5);
        stage.addActor(pinkStats);

        //pink Select Button
        TextButton pinkButton = new TextButton("pink",game.gameSkin);
        pinkButton.setWidth(pink.getWidth());
        pinkButton.setPosition((Gdx.graphics.getWidth()/6)*3-pink.getWidth()*1.3f,Gdx.graphics.getHeight()*2/3-pink.getHeight()*8);
        pinkButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.player = new Player(Boat.pink, game.world);
                game.opponents = new ArrayList<Opponent>();
                game.opponents.add(0, new Opponent(Boat.red, game.world));
		        game.opponents.add(1, new Opponent(Boat.blue, game.world));
		        game.opponents.add(2, new Opponent(Boat.green, game.world));
		        game.opponents.add(3, new Opponent(Boat.yellow, game.world));
                game.opponents.add(4, new Opponent(Boat.purple, game.world));
                for (Opponent opponent : game.opponents) {
                    opponent.arriveAt(game.finishLine);
                }
                game.play = true;
                stage.dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(pinkButton);

        //displays the purple boat
        Texture purpleTexture = new Texture(Gdx.files.internal("sprites/purple_boat.png"));
        Image purple = new Image(purpleTexture);
        purple.setSize(purpleTexture.getWidth()*3,purpleTexture.getHeight()*3);
        purple.setPosition((Gdx.graphics.getWidth()/6)*4-purple.getWidth()*1.25f,Gdx.graphics.getHeight()*2/3-purple.getHeight()/2);
        purple.setOrigin(purple.getWidth()/2,purple.getHeight()/2);
        purple.rotateBy(90);
        stage.addActor(purple);

        //display purple boat stats
        Label purpleStats = new Label("Higher Top Speed",game.gameSkin,"default");
        purpleStats.setFontScale(0.9f);
        purpleStats.setPosition((Gdx.graphics.getWidth()/6)*4-purple.getWidth()*1.5f,Gdx.graphics.getHeight()*2.3f/5);
        stage.addActor(purpleStats);

        //purple Select Button
        TextButton purpleButton = new TextButton("purple",game.gameSkin);
        purpleButton.setWidth(purple.getWidth());
        purpleButton.setPosition((Gdx.graphics.getWidth()/6)*4-purple.getWidth()*1.3f,Gdx.graphics.getHeight()*2/3-purple.getHeight()*8);
        purpleButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.player = new Player(Boat.purple, game.world);
                game.opponents = new ArrayList<Opponent>();
                game.opponents.add(0, new Opponent(Boat.red, game.world));
		        game.opponents.add(1, new Opponent(Boat.blue, game.world));
		        game.opponents.add(2, new Opponent(Boat.green, game.world));
		        game.opponents.add(3, new Opponent(Boat.yellow, game.world));
                game.opponents.add(4, new Opponent(Boat.pink, game.world));
                for (Opponent opponent : game.opponents) {
                    opponent.arriveAt(game.finishLine);
                }
                game.play = true;
                stage.dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(purpleButton);

        //displays the red boat
        Texture redTexture = new Texture(Gdx.files.internal("sprites/red_boat.png"));
        Image red = new Image(redTexture);
        red.setSize(redTexture.getWidth()*3,redTexture.getHeight()*3);
        red.setPosition((Gdx.graphics.getWidth()/6)*5-red.getWidth()*1.25f,Gdx.graphics.getHeight()*2/3-red.getHeight()/2);
        red.setOrigin(red.getWidth()/2,red.getHeight()/2);
        red.rotateBy(90);
        stage.addActor(red);

        //display red boat stats
        Label redStats = new Label("More Health",game.gameSkin,"default");
        redStats.setFontScale(0.9f);
        redStats.setPosition((Gdx.graphics.getWidth()/6)*5-red.getWidth()*1.275f,Gdx.graphics.getHeight()*2/5);
        stage.addActor(redStats);

        //red Select Button
        TextButton redButton = new TextButton("red",game.gameSkin);
        redButton.setWidth(red.getWidth());
        redButton.setPosition((Gdx.graphics.getWidth()/6)*5-red.getWidth()*1.3f,Gdx.graphics.getHeight()*2/3-red.getHeight()*8);
        redButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.player = new Player(Boat.red, game.world);
                game.opponents = new ArrayList<Opponent>();
                game.opponents.add(0, new Opponent(Boat.purple, game.world));
		        game.opponents.add(1, new Opponent(Boat.blue, game.world));
		        game.opponents.add(2, new Opponent(Boat.green, game.world));
		        game.opponents.add(3, new Opponent(Boat.yellow, game.world));
                game.opponents.add(4, new Opponent(Boat.pink, game.world));
                for (Opponent opponent : game.opponents) {
                    opponent.arriveAt(game.finishLine);
                }
                game.play = true;
                stage.dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(redButton);

        ////displays the yellow boat
        Texture yellowTexture = new Texture(Gdx.files.internal("sprites/yellow_boat.png"));
        Image yellow = new Image(yellowTexture);
        yellow.setSize(yellowTexture.getWidth()*3,yellowTexture.getHeight()*3);
        yellow.setPosition(Gdx.graphics.getWidth()-yellow.getWidth()*1.25f,Gdx.graphics.getHeight()*2/3-yellow.getHeight()/2);
        yellow.setOrigin(yellow.getWidth()/2,yellow.getHeight()/2);
        yellow.rotateBy(90);
        stage.addActor(yellow);

        //display yellow boat stats
        Label yellowStats = new Label("More Manoeuvrable",game.gameSkin,"default");
        yellowStats.setFontScale(0.9f);
        yellowStats.setPosition(Gdx.graphics.getWidth()-yellow.getWidth()*1.59f,Gdx.graphics.getHeight()*2.3f/5);
        stage.addActor(yellowStats);

        //yellow Select Button
        TextButton yellowButton = new TextButton("yellow",game.gameSkin);
        yellowButton.setWidth(yellow.getWidth());
        yellowButton.setPosition(Gdx.graphics.getWidth()-yellow.getWidth()*1.3f,Gdx.graphics.getHeight()*2/3-yellow.getHeight()*8);
        yellowButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.player = new Player(Boat.yellow, game.world);
                game.opponents = new ArrayList<Opponent>();
                game.opponents.add(0, new Opponent(Boat.red, game.world));
		        game.opponents.add(1, new Opponent(Boat.blue, game.world));
		        game.opponents.add(2, new Opponent(Boat.green, game.world));
		        game.opponents.add(3, new Opponent(Boat.purple, game.world));
                game.opponents.add(4, new Opponent(Boat.pink, game.world));
                for (Opponent opponent : game.opponents) {
                    opponent.arriveAt(game.finishLine);
                }
                game.play = true;
                stage.dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(yellowButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
 
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 213/255f, 1, 1); // divide by 255f
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
 
    @Override
    public void resize(int width, int height) {
 
    }
 
    @Override
    public void pause() {
 
    }
 
    @Override
    public void resume() {
 
    }
 
    @Override
    public void hide() {
 
    }
 
    @Override
    public void dispose() {
        stage.dispose();
    }
}