package com.disparter.github.raindrop.catcher.core;

import com.disparter.github.raindrop.catcher.ui.core.CollisionHandler;
import com.disparter.github.raindrop.catcher.ui.entities.Bucket;
import com.disparter.github.raindrop.catcher.ui.entities.Raindrop;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CollisionHandlerTest {

    @Test
    @Disabled
    public void testCollisionDetection() {
        final Bucket bucket = new Bucket();
        final Raindrop raindrop = new Raindrop(0, 0, 0);

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
