package com.mygdx.drop.entities;

import com.badlogic.gdx.utils.Pool;

import static com.mygdx.drop.entities.Constants.RAINDROP_HEIGHT;

public class RaindropPool extends Pool<Raindrop> {

    @Override
    public Raindrop newObject() {
        return new Raindrop(0, 0, RAINDROP_HEIGHT / 2);
    }

    @Override
    public void free(Raindrop raindrop) {
        super.free(raindrop);
    }
}
