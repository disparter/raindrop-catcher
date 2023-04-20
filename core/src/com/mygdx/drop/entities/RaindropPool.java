package com.mygdx.drop.entities;

import com.badlogic.gdx.utils.Pool;

import static com.mygdx.drop.entities.Constants.RAINDROP_HEIGHT;

public class RaindropPool extends Pool<Raindrop> {

    private int speedLevel;

    @Override
    public Raindrop newObject() {
        return new Raindrop(0, 0, (RAINDROP_HEIGHT / 2) * speedLevel);
    }

    @Override
    public void free(Raindrop raindrop) {
        super.free(raindrop);
    }
    
    public void setSpeedLevel(int speedLevel){
        this.speedLevel = speedLevel;
    }
}
