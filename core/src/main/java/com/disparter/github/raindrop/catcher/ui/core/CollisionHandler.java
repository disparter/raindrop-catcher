package com.disparter.github.raindrop.catcher.ui.core;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionHandler {

    public static boolean collidesWith(final float x1, final float y1, final float width1, final float height1,
                                       final float x2, final float y2, final float width2, final float height2) {
        final Rectangle rect1 = new Rectangle(x1, y1, width1, height1);
        final Rectangle rect2 = new Rectangle(x2, y2, width2, height2);
        return Intersector.overlaps(rect1, rect2);
    }

    public static boolean collidesWith(final Rectangle rect1, final Rectangle rect2) {
        return Intersector.overlaps(rect1, rect2);
    }

}
