package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.drop.Constants.WIDTH;

public class Bucket {
    private Texture bucketImage;
    private Vector2 position;
    private Rectangle bounds;

    public Bucket() {
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        position = new Vector2();
        position.x = WIDTH / 2 - bucketImage.getWidth() / 2;
        position.y = 20;

        bounds = new Rectangle(position.x, position.y, bucketImage.getWidth(), bucketImage.getHeight());
    }

    public void update(float delta) {
        // Move the bucket left or right based on user input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= BUCKET_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += BUCKET_SPEED * delta;
        }

        // Make sure the bucket stays within the screen bounds
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > WIDTH - bounds.width) {
            position.x = WIDTH - bounds.width;
        }

        // Update the bounding rectangle's position to match the bucket's position
        bounds.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(bucketImage, position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        bucketImage.dispose();
    }
}
