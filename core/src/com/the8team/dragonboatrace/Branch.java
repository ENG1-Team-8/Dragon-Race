package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

public class Branch extends Obstacle {

   public Branch(int horizontalVel, int x, int y, World world) {

      super(horizontalVel, 0, 2, x, y, 64, 16, world, "sprites/branch.png");

   }

}