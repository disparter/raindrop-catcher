package com.disparter.github.raindrop.catcher.ui.entities;

import com.badlogic.gdx.utils.Pool;

import java.util.Random;

public class RaindropPool extends Pool<Raindrop> {

    private int speedLevel;
    private int dropsGathered;
    private static final float DROPS_SPEED_FACTOR = 0.17F;
    private float speed;
    @Override
    public Raindrop newObject() {
        final Random random = new Random();
        final int randomValue = random.nextInt(dropsGathered + 1 ) + 1;
        this.speed = Constants.RAINDROP_HEIGHT * 2 * speedLevel + (DROPS_SPEED_FACTOR * randomValue * dropsGathered);
        return new Raindrop(0, 0, speed);
    }

    @Override
    public void free(final Raindrop raindrop) {
        super.free(raindrop);
    }
    
    public void setSpeedLevel(final int speedLevel, final int dropsGathered){
        this.speedLevel = speedLevel;
        this.dropsGathered = dropsGathered;
    }

    public float getSpeed() {
        return speed;
    }
}
