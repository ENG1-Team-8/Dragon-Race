package com.the8team.dragonboatrace;

import com.badlogic.gdx.graphics.Texture;

public class Branch extends Obstacle {

   public Branch(int damage_dealt, int health, float xPosition, float yPosition, float width, float height,
         Texture objectTexture, float movementSpeed, float acceleration, float handling, float stamina) {

      super(damage_dealt, health, xPosition, yPosition, width, height, objectTexture, movementSpeed, acceleration,
            handling, stamina);
   }

}