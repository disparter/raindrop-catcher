package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Bucket {
    private final Sprite sprite;
    private Rectangle bounds;
    private float speed = 200;

    public Bucket() {
        sprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        bounds = new Rectangle(320 / 2 - 64 / 2, 20, 64, 64);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose(){
        sprite.getTexture().dispose();
    }

    public void updateBounds() {
        // update bucket position
        float x = Gdx.input.getX() - 64 / 2;
        bounds.setPosition(x, 20);

        // update bucket sprite position
        sprite.setPosition(bounds.x, bounds.y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void moveTo(float x, float y) {
        bounds.x = x;
        bounds.y = y;
    }
}
