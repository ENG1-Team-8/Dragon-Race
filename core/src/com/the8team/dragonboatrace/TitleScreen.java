package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The title screen displayed on loading.
 * 
 * @author Charlie Hayes
 */
public class TitleScreen implements Screen {

    // Stage and game
    private Stage stage;
    private DragonBoatRace game;

    /**
     * COnstruct the title screen.
     * 
     * @param dragonBoatRace The instance of the game running
     */
    public TitleScreen(DragonBoatRace dragonBoatRace) {
        game = dragonBoatRace;
        stage = new Stage(new ScreenViewport());

        // Displays the game title
        Label title = new Label("Dragon Boat Race: The Game", game.gameSkin, "title");
        title.setFontScale(0.5f, 0.5f);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        // Displays the button that takes you to the boat selection screen
        TextButton playButton = new TextButton("Play!", game.gameSkin);
        playButton.setWidth(Gdx.graphics.getWidth() / 2);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SelectScreen(game));
                stage.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 213 / 255f, 1, 1); // Divide by 255f
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
