package com.the8team.dragonboatrace;


import com.badlogic.gdx.graphics.Texture;

public abstract class Boat {
    //Boat characterisitcs
    int mvmntSpeed;
    int health;
    int stamina;
    int acceleration;
    int maneuverability;

    //Boat position
    //not sure how this works with the map we are using
    float mapX,mapY;
    float width, height;

    //graphics
    Texture sprite;

    public Boat(int mvmntSpeed, int health, int stamina, int acceleration, int maneuverability, float mapX, float mapY, float width, float height, Texture sprite){};

    public void draw(){}; //not sure what it does or how it works lol
}
