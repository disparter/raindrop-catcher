package com.mygdx.drop.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Raindrop {
    private Texture dropImage;
    private Vector2 position;
    private float speed;
    private Rectangle hitbox;
    private boolean isCaught;

    public Raindrop(Texture dropImage) {
        this.dropImage = dropImage;
        position = new Vector2();
        speed = 200;
        hitbox = new Rectangle(position.x, position.y, dropImage.getWidth(), dropImage.getHeight());
        isCaught = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta) {
        if (!isCaught) {
            position.y -= speed * delta;
            hitbox.setPosition(position.x, position.y);
        }
    }

    public boolean getIsCaught() {
        return isCaught;
    }

    public void setIsCaught(boolean isCaught) {
        this.isCaught = isCaught;
    }

    public void draw(Batch batch) {
        batch.draw(dropImage, position.x, position.y);
    }

    public float getHeight() {
        return dropImage.getHeight();
    }

    public float getWidth() {
        return dropImage.getWidth();
    }
}
