package com.the8team.dragonboatrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Game;

public class DragonBoatRace extends Game{
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    Sprite sprite;
    Sound startMusic;
    ShapeRenderer shape;
    
    @Override
    public void create () {

        //Create music
        startMusic = Gdx.audio.newSound(Gdx.files.internal("sound/Race.wav"));
        startMusic.play(0.2f);
        
        //Create map and camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("map/test.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //Create test shape
        shape = new ShapeRenderer();
    }
    @Override
    public void render () {
        //Clear screen and sets blend function
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update camera on tiled map
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        //Test shape
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(128, 128, 64, 16);
        shape.end();
        camera.translate(2, 0);
    }

    public void dispose () {
        startMusic.dispose();
    }
}
