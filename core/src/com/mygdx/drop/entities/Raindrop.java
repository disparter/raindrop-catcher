package com.mygdx.drop.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.drop.core.CollisionHandler;

public class Raindrop {
    private Texture dropImage;
    private Vector2 position;
    private float speed;
    private Rectangle hitbox;
    private boolean isCaught;
    private boolean visible;

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

    public void update(float deltaTime) {
        position.y -= speed * deltaTime;

        Rectangle screenRect = new Rectangle(0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

        if (CollisionHandler.isColliding(hitbox, screenRect)) {
            isCaught = true;
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
    public Rectangle getHitbox() {
        return hitbox;
    }
}
