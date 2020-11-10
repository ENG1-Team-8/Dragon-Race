package com.the8team.dragonboatrace;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.MapObject;

public class TiledObjects {

    // Game scale
    static float scale = 16;

    /**
     * Parses the tiled map for objects in the given 'objects' object and creates box2d representations for them
     * 
     * @param world
     * @param objects
     */
    public static void parseTiledObjectLayer(World world, MapObjects objects) {

        // Iterate through map objects
        for(MapObject object : objects) {
            Shape shape;
            // If the object is a polyline, create a line barrier
            if(object instanceof PolylineMapObject) {
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
     * Takes a tiled map polyline and returns a box2d chain shape representation
     * 
     * @param polyline
     * @return
     */
    private static ChainShape createPolyline(PolylineMapObject polyline) {

        // Gets the vertices for the polyline object
        float[] vertices = polyline.getPolyline().getTransformedVertices();

        // Removes duplicate vertices
        Vector2[] worldVertices = new Vector2[vertices.length/2];
        for(int i=0; i<worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i*2]/scale, vertices[i*2+1]/scale);
        }

        // Creates and returns the chain shape from given vertices
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
}
