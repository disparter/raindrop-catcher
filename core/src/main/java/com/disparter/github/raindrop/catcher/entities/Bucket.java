package com.disparter.github.raindrop.catcher.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import static com.disparter.github.raindrop.catcher.entities.Constants.BUCKET_HEIGHT;
import static com.disparter.github.raindrop.catcher.entities.Constants.BUCKET_WIDTH;

public class Bucket {
    private final Sprite sprite;
    private final Rectangle bounds;

    public Bucket() {
        sprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        bounds = new Rectangle(320 / 2 - 64 / 2, 20, BUCKET_WIDTH, BUCKET_HEIGHT);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose(){
        sprite.getTexture().dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void moveTo(final float x, final float y) {
        bounds.x = x;
        bounds.y = y;
    }
}
