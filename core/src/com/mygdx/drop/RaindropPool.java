package com.mygdx.drop;

import com.badlogic.gdx.utils.Array;

public class RaindropPool {
    private final Array<Raindrop> raindrops;
    private final int maxSize;

    public RaindropPool(int maxSize) {
        this.raindrops = new Array<>(maxSize);
        this.maxSize = maxSize;

        for (int i = 0; i < maxSize; i++) {
            this.raindrops.add(new Raindrop());
        }
    }

    public Raindrop obtain() {
        return raindrops.size > 0 ? raindrops.pop() : new Raindrop();
    }

    public void free(Raindrop raindrop) {
        if (raindrops.size < maxSize) {
            raindrop.reset();
            raindrops.add(raindrop);
        }
    }
}
