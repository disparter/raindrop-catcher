package com.mygdx.drop.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Raindrop {
    private Texture dropImage;
    private Vector2 position;
    private Rectangle bounds;
    private float speed;

    public Raindrop(Texture dropImage) {
        this.dropImage = dropImage;
        position = new Vector2();
        bounds = new Rectangle();
        speed = 1;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void init(float x) {
        position.set(x, 480);
        bounds.set(position.x, position.y, dropImage.getWidth(), dropImage.getHeight());
    }

    public void update(float delta) {
        bounds.y -= speed * delta;
        setPosition(bounds.x, bounds.y);
    }

    public void draw(Batch batch) {
        batch.draw(dropImage, position.x, position.y);
    }

    public boolean overlaps(Rectangle otherBounds) {
        return bounds.overlaps(otherBounds);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.bounds.setPosition(x, y);
    }

    public float getHeight() {
        return dropImage.getHeight();
    }

    public int getWidth() {
        return dropImage.getWidth();
    }

}
