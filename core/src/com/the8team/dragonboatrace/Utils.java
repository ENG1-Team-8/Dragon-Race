package com.the8team.dragonboatrace;

import java.util.Comparator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Utility class for some game functions.
 * 
 * @author Matt Tomlinson
 */
public class Utils {

    // Game scale
    static float scale = 16;

    // A comparator used for sorting opponents ArrayList by fastest time order
    static Comparator<Opponent> boatSorter = new Comparator<Opponent>() {
        public int compare(Opponent o1, Opponent o2) {
            return Float.compare(o1.getFastestTime(), o2.getFastestTime());
        }
    };

    /**
     * Parses the tiled map for objects in the given 'objects' object and creates
     * box2d representations for them.
     * 
     * @param world   The world to create the objects, from the tiled map, in
     * @param objects The set of tiled map objects
     */
    public static void parseTiledObjectLayer(World world, MapObjects objects) {

        // Iterate through map objects
        for (MapObject object : objects) {
            Shape shape;
            // If the object is a polyline, create a line barrier
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }

            // Create the box2d body for the object
            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    /**
     * Takes a tiled map polyline and returns a box2d chain shape representation.
     * 
     * @param polyline A given polyline on the tiled map
     * @return A ChainShape
     */
    private static ChainShape createPolyline(PolylineMapObject polyline) {

        // Gets the vertices for the polyline object
        float[] vertices = polyline.getPolyline().getTransformedVertices();

        // Removes duplicate vertices
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / scale, vertices[i * 2 + 1] / scale);
        }

        // Creates and returns the chain shape from given vertices
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
}
