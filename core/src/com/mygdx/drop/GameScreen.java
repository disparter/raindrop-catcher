package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Constants;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

public class GameScreen extends ScreenAdapter {
	private Texture dropImage;
	private Texture bucketImage;

	private Bucket bucket;
	private Array<Raindrop> raindrops;
	private RaindropPool raindropPool;

	private long lastDropTime;

	@Override
	public void show() {
		bucket = new Bucket();
		bucket.setPosition(Constants.VIEWPORT_WIDTH / 2 - Constants.BUCKET_WIDTH / 2, Constants.BUCKET_HEIGHT);

		raindrops = new Array<>();
		raindropPool = new RaindropPool();
	}

	@Override
	public void render(float delta) {
		// spawn new raindrops
		if (TimeUtils.nanoTime() - lastDropTime > Constants.RAINDROPS_SPAWN_DELAY) {
			spawnRaindrop();
		}

		// update raindrops and bucket
		update(delta);

		// clear the screen with a dark blue color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT);

		// render raindrops and bucket
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		for (Raindrop raindrop : raindrops) {
			batch.draw(dropImage, raindrop.getX(), raindrop.getY());
		}
		batch.draw(bucketImage, bucket.getX(), bucket.getY());
		batch.end();
	}

	private void spawnRaindrop() {
		Raindrop raindrop = raindropPool.obtain();
		raindrop.init(MathUtils.random(Constants.WIDTH - Constants.DROP_WIDTH), Constants.HEIGHT);
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void update(float delta) {
		// update bucket position
		float bucketX = MathUtils.clamp(Gdx.input.getX(), 0, Constants.WIDTH - Constants.BUCKET_WIDTH);
		bucket.setPosition(bucketX, Constants.BUCKET_Y);

		// update raindrop positions and remove any that are beneath the bottom edge of the screen
		for (int i = 0; i < raindrops.size; i++) {
			Raindrop raindrop = raindrops.get(i);
			raindrop.update(delta);
			if (raindrop.getY() + Constants.DROP_HEIGHT < 0) {
				raindrops.removeIndex(i);
				raindropPool.free(raindrop);
			}
		}
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		raindropPool.clear();
	}
}
