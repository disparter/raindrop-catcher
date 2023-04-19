package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bucket {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;

    private final Vector2 position = new Vector2();
    private final Sprite sprite;

    public Bucket() {
        Texture texture = new Texture(Gdx.files.internal("bucket.png"));
        sprite = new Sprite(texture);
        sprite.setSize(WIDTH, HEIGHT);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        sprite.setPosition(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
