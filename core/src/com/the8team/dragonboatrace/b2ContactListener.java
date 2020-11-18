package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class b2ContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Boat) {
            System.out.println("boat col");
            Boat boat = (Boat) objectA;
            if (objectB instanceof Obstacle) {
                Obstacle obs = (Obstacle) objectB;
                boat.updateHealth(obs.damageDealt);
                DragonBoatRace.toDelete.add(obs);
            }
        } else if (objectA instanceof Obstacle) {
            System.out.println("obstacle col");
            Obstacle obs = (Obstacle) objectA;
            if (objectB instanceof Boat) {

                Boat boat = (Boat) objectB;
                boat.updateHealth(obs.damageDealt);
                DragonBoatRace.toDelete.add(obs);

            } else if (objectB instanceof ChainShape) {

                if (objectA instanceof Goose) {

                    Goose goose = (Goose) obs;
                    goose.bounce();

                }

            }

        } else if (objectA instanceof ChainShape) {

            if (objectB instanceof Goose) {

                Goose goose = (Goose) objectB;
                goose.bounce();

            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Boat) {

            if (objectB instanceof Obstacle) {

                Boat boat = (Boat) objectA;
                Obstacle obs = (Obstacle) objectB;
                DragonBoatRace.toAdd.add(obs);

            }

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}