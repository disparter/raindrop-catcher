package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.drop.core.CollisionHandler;

import static com.mygdx.drop.entities.Constants.BUCKET_HEIGHT;
import static com.mygdx.drop.entities.Constants.BUCKET_MOVEMENT;
import static com.mygdx.drop.entities.Constants.BUCKET_WIDTH;
import static com.mygdx.drop.entities.Constants.VIEWPORT_WIDTH;

public class Bucket {
    private final Vector2 position = new Vector2();
    private final Sprite sprite;
    private final Texture texture;
    private Rectangle hitbox;
    private int raindropsGathered;

    public Bucket() {
        texture = new Texture(Gdx.files.internal("bucket.png"));
        sprite = new Sprite(texture);
        sprite.setSize(BUCKET_WIDTH, BUCKET_HEIGHT);
        hitbox = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        raindropsGathered = 0;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        sprite.setPosition(x, y);
        hitbox.setPosition(x, y);
    }
    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void draw(Batch batch) {
        batch.draw(sprite, position.x, position.y);
    }

    public boolean catchRaindrop(Raindrop raindrop) {
        Rectangle bucketRect = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        Rectangle raindropRect = raindrop.getHitbox();

        if (CollisionHandler.isColliding(bucketRect, raindropRect)) {
            raindropsGathered++;
            return true;
        }
        return false;
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

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight();
        }
    }

    public void updateBounds() {
        hitbox.set(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public int getRaindropsGathered() {
        return raindropsGathered;
    }
}
