package com.the8team.dragonboatrace;


//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Boat {
    //Boat characterisitcs
    float mvmntSpeed;
    int health;
    int stamina;

    //Boat position
    //not sure how this works with the map we are using
    float mapX,mapY;
    float width, height;

    //graphics
    Texture sprite;

    public Boat(float mvmntSpeed, int health, int stamina, float mapX, float mapY, float width, float height, Texture sprite){};

    public void draw(){}; //not sure what it does or how it works lol
}
