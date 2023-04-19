package com.mygdx.drop.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Raindrop {
    private Texture dropImage;
    private Vector2 position;
    private float speed;

    public Raindrop(Texture dropImage) {
        this.dropImage = dropImage;
        position = new Vector2();
        speed = 200;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta) {
        position.y -= speed * delta;
    }

    public void draw(Batch batch) {
        batch.draw(dropImage, position.x, position.y);
    }

}
