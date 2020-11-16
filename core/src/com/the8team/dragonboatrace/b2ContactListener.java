package com.the8team.dragonboatrace;

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
                Obstacle obj = (Obstacle) objectB;
                boat.updateHealth(obj.damageDealt);
                DragonBoatRace.toDelete.add(obj);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Boat || objectB instanceof Boat) {
            System.out.println("boat stopped col");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}