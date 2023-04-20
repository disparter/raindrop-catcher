package com.mygdx.drop.entities;

import com.badlogic.gdx.utils.Pool;

import java.util.Random;

import static com.mygdx.drop.entities.Constants.RAINDROP_HEIGHT;

public class RaindropPool extends Pool<Raindrop> {

    private int speedLevel;
    private int dropsGathered;
    private static final float DROPS_SPEED_FACTOR = 0.17F;
    private float speed;
    @Override
    public Raindrop newObject() {
        Random random = new Random();
        int randomValue = random.nextInt(dropsGathered + 1 ) + 1;
        this.speed = RAINDROP_HEIGHT * 2 * speedLevel + (DROPS_SPEED_FACTOR * randomValue * dropsGathered);
        return new Raindrop(0, 0, speed);
    }

    @Override
    public void free(Raindrop raindrop) {
        super.free(raindrop);
    }
    
    public void setSpeedLevel(int speedLevel, int dropsGathered){
        this.speedLevel = speedLevel;
        this.dropsGathered = dropsGathered;
    }

    public float getSpeed() {
        return speed;
    }
}
