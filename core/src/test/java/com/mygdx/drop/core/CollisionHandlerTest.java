package com.mygdx.drop.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionHandlerTest {

    @Test
    public void testCollisionDetection() {
        Bucket bucket = new Bucket(0, 0, 0, 0);
        Raindrop raindrop = new Raindrop(0, 0, 0);
        CollisionHandler collisionHandler = new CollisionHandler();

        // Test case 1: No collision
        boolean isCollision = collisionHandler.checkForCollision(bucket, raindrop);
        assertFalse(isCollision);

        // Test case 2: Collision
        bucket.setPosition(100, 100);
        raindrop.setPosition(100, 100);
        isCollision = collisionHandler.checkForCollision(bucket, raindrop);
        assertTrue(isCollision);
    }
}
