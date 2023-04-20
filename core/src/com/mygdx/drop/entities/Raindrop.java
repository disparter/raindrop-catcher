package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.drop.entities.Constants.RAINDROP_HEIGHT;
import static com.mygdx.drop.entities.Constants.RAINDROP_WIDTH;

public class Raindrop {
    private final float speed;
    private final Sprite sprite;
    private Rectangle bounds;

    public Raindrop(float x, float y, float speed) {
        sprite = new Sprite(new Texture(Gdx.files.internal("droplet.png")));
        bounds = new Rectangle(x, y, RAINDROP_WIDTH, RAINDROP_HEIGHT);
        this.speed = speed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void update(float delta) {
        bounds.y -= speed * delta;
    }
}
