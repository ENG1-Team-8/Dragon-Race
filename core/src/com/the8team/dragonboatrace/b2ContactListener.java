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
        if (objectA instanceof Player) {
            System.out.println("player col");
            Player player = (Player) objectA;
            // if(objectB instanceof obstacle) {
            // Obstacle ob = (Obstacle) objectB;
            // player.updateHealth(ob.damage)
            // DragonBoatRace.toDelete.add(ob);
            if (objectB instanceof Obstacle) {

                Obstacle obj = (Obstacle) objectB;
                player.updateHealth(obj.damageDealt);
                DragonBoatRace.toDelete.add(obj);

            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Player || objectB instanceof Player) {
            System.out.println("player stopped col");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}