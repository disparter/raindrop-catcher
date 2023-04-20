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

    public void moveLeft(float deltaTime) {
        bounds.x -= speed * deltaTime;
        if (bounds.x < 0) {
            bounds.x = 0;
        }
    }

    public void moveRight(float deltaTime) {
        bounds.x += speed * deltaTime;
        if (bounds.x > 320 - 64) {
            bounds.x = 320 - 64;
        }
    }

    public void dispose(){
        sprite.getTexture().dispose();
    }

    public void updateBounds() {
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public Sprite getSprite() {
        return sprite;
    }
}
