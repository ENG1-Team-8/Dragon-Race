package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.World;

/**
 * A branch obstacle, extends from Obstacle
 * 
 * @author Josh Stafford
 * @see Obstacle
 */
public class Branch extends Obstacle {

   /**
    * Construct a branch
    *
    * @param horizontalVel The horizontal velocity of the branch
    * @param x             The starting x coordinate
    * @param y             The starting y coordinate
    * @param world         The world in which to create the branch
    */
   public Branch(int horizontalVel, int x, int y, World world) {
      super(horizontalVel, 0, 2, x, y, 64, 16, world, "sprites/branch.png");
   }

}