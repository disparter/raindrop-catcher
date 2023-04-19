package com.mygdx.drop.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import static com.mygdx.drop.entities.Constants.VIEWPORT_HEIGHT;
import static com.mygdx.drop.entities.Constants.VIEWPORT_WIDTH;

public class Raindrop {
    private static final float SPEED = 200;

    private float x;
    private float y;
    private Texture texture;

    public void update(float deltaTime) {
        this.y -= SPEED * deltaTime;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void reset() {
        this.x = MathUtils.random(VIEWPORT_WIDTH - texture.getWidth());
        this.y = VIEWPORT_HEIGHT;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }
}
