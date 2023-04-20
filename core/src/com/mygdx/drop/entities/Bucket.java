package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bucket {
    private Texture bucketImage;
    private Rectangle bounds;
    private float speed = 200;

    public Bucket() {
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        bounds = new Rectangle(320 / 2 - 64 / 2, 20, 64, 64);
    }

    public Texture getBucketImage() {
        return bucketImage;
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
}
