package com.disparter.github.raindrop.catcher.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Raindrop {
    private final float speed;
    private final Sprite sprite;
    private final Rectangle bounds;

    public Raindrop(final float x, final float y, final float speed) {
        sprite = new Sprite(new Texture(Gdx.files.internal("droplet.png")));
        bounds = new Rectangle(x, y, Constants.RAINDROP_WIDTH, Constants.RAINDROP_HEIGHT);
        this.speed = speed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void update(final float delta) {
        bounds.y -= speed * delta;
    }
}
