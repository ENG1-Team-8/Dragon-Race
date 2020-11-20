package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * The contact listener, runs on collisions
 * 
 * @author Josh Stafford
 */
public class b2ContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        // Gets the user data for the contacting fixtures
        // In this case, moving objects set user data to itself (this)
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();

        // If the colliding object is a boat
        if (objectA instanceof Boat) {
            // Type casts the object to a boat to access methods
            Boat boat = (Boat) objectA;
            // If the other object colliding is an obstacle
            if (objectB instanceof Obstacle) {
                // Type cast the object to obstacle to access methods
                Obstacle obs = (Obstacle) objectB;
                // Update the player's health
                boat.updateHealth(obs.damageDealt);
                // Delete the obstacle
                DragonBoatRace.toDelete.add(obs);
            }
        } else if (objectA instanceof Obstacle) {
            Obstacle obs = (Obstacle) objectA;
            if (objectB instanceof Boat) {

                Boat boat = (Boat) objectB;
                boat.updateHealth(obs.damageDealt);
                DragonBoatRace.toDelete.add(obs);

            } else if (contact.getFixtureB().getShape() instanceof ChainShape) {

                // If Goose is colliding with a ChainShape (the map boundaries)
                if (objectA instanceof Goose) {

                    // Bounce the goose
                    Goose goose = (Goose) obs;
                    goose.bounce();

                }

            }

        } else if (contact.getFixtureA().getShape() instanceof ChainShape) {

            // If Goose is colliding with a ChainShape (the map boundaries)
            if (objectB instanceof Goose) {

                // Bounce the goose
                Goose goose = (Goose) objectB;
                goose.bounce();

            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        // On end contact
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}