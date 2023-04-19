package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.drop.entities.Constants.BUCKET_HEIGHT;
import static com.mygdx.drop.entities.Constants.BUCKET_MOVEMENT;
import static com.mygdx.drop.entities.Constants.BUCKET_WIDTH;
import static com.mygdx.drop.entities.Constants.VIEWPORT_WIDTH;

public class Bucket {
    private final Vector2 position = new Vector2();
    private final Sprite sprite;

    public Bucket() {
        Texture texture = new Texture(Gdx.files.internal("bucket.png"));
        sprite = new Sprite(texture);
        sprite.setSize(BUCKET_WIDTH, BUCKET_HEIGHT);
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

    public void draw(Batch batch) {
        batch.draw(sprite, position.x, position.y);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, sprite.getTexture().getWidth(), sprite.getTexture().getHeight());
    }

    public void moveRight() {
        if (position.x < VIEWPORT_WIDTH - sprite.getWidth()) {
            position.x += BUCKET_MOVEMENT;
        }
    }

    public void moveLeft() {
        if (position.x > 0) {
            position.x -= BUCKET_MOVEMENT;
        }
    }
}
