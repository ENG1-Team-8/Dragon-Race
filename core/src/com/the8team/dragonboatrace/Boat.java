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

    //fastest time
    float fastestTime;

    public Boat(int mvmntSpeed, int health, int stamina, int acceleration, int maneuverability, float mapX, float mapY, float width, float height, Texture sprite){this.mvmntSpeed = mvmntSpeed;
        this.health = health;
        this.stamina = stamina;
        this.acceleration = acceleration;
        this.maneuverability = maneuverability;
        this.mapX = mapX;
        this.mapY = mapY;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    };

    public void draw(){}; //not sure what it does or how it works lol
}
