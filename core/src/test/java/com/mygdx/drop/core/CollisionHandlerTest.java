package com.mygdx.drop.core;

import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Raindrop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CollisionHandlerTest {

    @Test
    public void testCollisionDetection() {
        Bucket bucket = new Bucket();
        Raindrop raindrop = new Raindrop(0, 0, 0);

        // Test case 1: No collision
        boolean isCollision = CollisionHandler.collidesWith(bucket.getBounds(),
                raindrop.getBounds());

        assertFalse(isCollision);

        // Test case 2: Collision
        bucket.getBounds().setPosition(100, 100);
        raindrop.getBounds().setPosition(100, 100);
        isCollision = CollisionHandler.collidesWith(bucket.getBounds(),
                raindrop.getBounds());

        assertTrue(isCollision);
    }
}
