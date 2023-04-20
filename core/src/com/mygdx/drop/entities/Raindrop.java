package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.drop.entities.Constants.UPDATE_INTERVAL;

public class Raindrop {
    private final float speed;
    private final Sprite sprite;
    private Rectangle bounds;
    private Vector2 velocity;
    private float timeSinceLastUpdate;

    public Raindrop(float x, float y, float speed) {
        sprite = new Sprite(new Texture(Gdx.files.internal("droplet.png")));
        bounds = new Rectangle(x, y, 32, 32);
        velocity = new Vector2(0, -speed);
        this.speed = speed;
        timeSinceLastUpdate = 0;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void update(float delta) {
        // Update the time since the last update
        timeSinceLastUpdate += delta;

        // If it's time to update the position of the raindrop, do so
        if (timeSinceLastUpdate >= UPDATE_INTERVAL) {
            // Calculate the new position of the raindrop based on its velocity
            bounds.y += velocity.y * (timeSinceLastUpdate / UPDATE_INTERVAL);

            // Reset the time since the last update
            timeSinceLastUpdate = 0;
        }
    }
}
