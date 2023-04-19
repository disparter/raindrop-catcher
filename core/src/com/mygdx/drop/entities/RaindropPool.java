package com.mygdx.drop.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class RaindropPool extends Pool<Raindrop> {
    @Override
    protected Raindrop newObject() {
        return new Raindrop( new Texture(Gdx.files.internal("droplet.png")));
    }

    @Override
    public void free(Raindrop raindrop) {
        super.free(raindrop);
    }
}
