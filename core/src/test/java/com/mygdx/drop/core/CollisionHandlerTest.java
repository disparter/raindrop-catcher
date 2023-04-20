package com.mygdx.drop.core;

public class CollisionHandlerTest {

    @Test
    public void testCollisionDetection() {
        Bucket bucket = new com.mygdx.drop.entities.Bucket(0, 0, 0, 0);
        Raindrop raindrop = new com.mygdx.drop.entities.Raindrop(0, 0, 0);
        CollisionHandler collisionHandler = new com.mygdx.drop.core.CollisionHandler();

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
