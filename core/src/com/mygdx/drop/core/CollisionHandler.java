package com.mygdx.drop.core;

import com.badlogic.gdx.math.Rectangle;

public class CollisionHandler {
    
    public static boolean isColliding(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }
}
