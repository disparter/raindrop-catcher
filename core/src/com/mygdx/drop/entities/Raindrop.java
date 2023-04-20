package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Raindrop {
    private Texture raindropImage;
    private Rectangle bounds;
    private float speed;

    public Raindrop(float x, float y, float speed) {
        raindropImage = new Texture(Gdx.files.internal("droplet.png"));
        bounds = new Rectangle(x, y, 32, 32);
        this.speed = speed;
    }

    public Texture getRaindropImage() {
        return raindropImage;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void update(float deltaTime) {
        bounds.y -= speed * deltaTime;
    }

    public void remove() {
        raindropImage.dispose();
    }
}
