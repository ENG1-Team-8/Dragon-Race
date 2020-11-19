package com.the8team.dragonboatrace;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Branch extends Obstacle {

   public Branch(int horizontalVel, int verticalVel, int damageDealt, int x, int y, int width, int height, int maxSpeed,
                 boolean isStatic, World world, String textureFile, Steerable<Vector2> owner) {

      super(horizontalVel, verticalVel, damageDealt, x, y, width, height, maxSpeed, isStatic, world, textureFile,owner);

   }

}