package com.mygdx.drop.entities;

import com.badlogic.gdx.utils.Pool;

public class RaindropPool extends Pool<Raindrop> {

    @Override
    public Raindrop newObject() {
        return new Raindrop(0, 0, 0);
    }

    @Override
    public void free(Raindrop raindrop) {
        super.free(raindrop);
    }
}
