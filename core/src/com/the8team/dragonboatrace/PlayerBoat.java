package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;

public class PlayerBoat extends Boat{
    // hello
    public PlayerBoat(int mvmntSpeed, int health, int stamina, int acceleration, int maneuverability, float mapX, float mapY, float width, float height, Texture sprite){
        super(mvmntSpeed,health,stamina,acceleration,maneuverability,mapX,mapY,width,height,sprite);
    };
}