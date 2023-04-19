package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

public class GameScreen extends ScreenAdapter {

	private final DropGame game;
	private final BitmapFont font;
	private final SpriteBatch batch;
	private final Array<Raindrop> raindrops;
	private final Bucket bucket;
	private final RaindropPool raindropPool;
	private long lastDropTime;

	public GameScreen(DropGame game) {
		this.game = game;
		font = new BitmapFont();
		batch = new SpriteBatch();
		raindrops = new Array<>();
		bucket = new Bucket();
		raindropPool = new RaindropPool();
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the bucket to update its position
		bucket.setPosition(Gdx.input.getAccelerometerX());

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			spawnRaindrop();
		}

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we increase the
		// score variable and add a sound effect.
		for (int i = 0; i < raindrops.size; i++) {
			Raindrop raindrop = raindrops.get(i);
			raindrop.update(delta);
			if (raindrop.getPosition().y + raindrop.getHeight() < 0) {
				raindrops.removeIndex(i);
				raindropPool.free(raindrop);
			} else if (raindrop.getBounds().overlaps(bucket.getBounds())) {
				game.playDropSound();
				raindrops.removeIndex(i);
				raindropPool.free(raindrop);
			}
		}

		// draw the bucket and all drops
		batch.begin();
		bucket.draw(batch);
		for (Drop raindrop : raindrops) {
			raindrop.draw(batch);
		}
		font.draw(batch, "Drops Collected: " + game.getScore(), 0, Gdx.graphics.getHeight());
		batch.end();
	}

	private void spawnRaindrop() {
		Raindrop raindrop = raindropPool.obtain();
		raindrop.setPosition(MathUtils.random(0, Gdx.graphics.getWidth() - raindrop.getWidth()), Gdx.graphics.getHeight());
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		raindropPool.clear();
	}
}
