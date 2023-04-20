package com.mygdx.drop.core;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionHandler {

    public static boolean collidesWith(float x1, float y1, float width1, float height1,
                                       float x2, float y2, float width2, float height2) {
        Rectangle rect1 = new Rectangle(x1, y1, width1, height1);
        Rectangle rect2 = new Rectangle(x2, y2, width2, height2);
        return Intersector.overlaps(rect1, rect2);
    }

}
