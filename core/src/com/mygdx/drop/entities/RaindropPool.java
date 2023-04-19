package com.mygdx.drop.entities;

import com.badlogic.gdx.utils.Pool;

public class RaindropPool extends Pool<Raindrop> {

    @Override
    protected Raindrop newObject() {
        return new Raindrop();
    }

    @Override
    public void free(Raindrop raindrop) {
        super.free(raindrop);
        raindrop.reset();
    }
}
