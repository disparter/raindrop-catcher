package com.mygdx.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.drop.Constants.HEIGHT;
import static com.mygdx.drop.Constants.WIDTH;

public class Raindrop {
    private static final float SPEED = 200;

    private float x;
    private float y;
    private Texture texture;

    public Raindrop(Texture texture, RaindropPool raindropPool) {
        super(texture);
        this.raindropPool = raindropPool;
        velocity = new Vector2(0, -200);
    }

    public void update(float deltaTime) {
        this.y -= SPEED * deltaTime;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void reset() {
        this.x = MathUtils.random(WIDTH - texture.getWidth());
        this.y = HEIGHT;
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
