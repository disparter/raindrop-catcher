package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Raindrop {
    private Texture raindropImage;
    private Rectangle bounds;
    private Vector2 velocity;

    public Raindrop(float x, float y, float speed) {
        raindropImage = new Texture(Gdx.files.internal("droplet.png"));
        bounds = new Rectangle(x, y, 32, 32);
        velocity = new Vector2(0, -speed);
    }

    public Texture getRaindropImage() {
        return raindropImage;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void update(float deltaTime) {
        bounds.setPosition(bounds.x + velocity.x * deltaTime, bounds.y + velocity.y * deltaTime);
    }
    public void remove() {
        raindropImage.dispose();
    }
}
