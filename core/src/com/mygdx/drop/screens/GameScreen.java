package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

public class GameScreen extends ScreenAdapter {

	private Texture dropImage;
	private Texture bucketImage;
	private SpriteBatch batch;
	private Bucket bucket;
	private RaindropPool raindropPool;
	private Vector2 touchPos = new Vector2();

	@Override
	public void show() {
		batch = new SpriteBatch();
		bucket = new Bucket();
		raindropPool = new RaindropPool();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		bucket.draw(batch);
		for (Raindrop raindrop : raindropPool.getActiveItems()) {
			raindrop.draw(batch);
		}
		batch.end();

		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());
			bucket.move(touchPos.x - bucketImage.getWidth() / 2, Constants.BUCKET_Y);
		}

		if (MathUtils.random() < Constants.RAINDROP_SPAWN_CHANCE) {
			Raindrop raindrop = raindropPool.obtain();
			if (raindrop != null) {
				raindrop.reset(MathUtils.random(Gdx.graphics.getWidth()), Gdx.graphics.getHeight());
			}
		}

		raindropPool.freeAll(raindrop -> raindrop.getPosition().y <= 0);

		bucket.update(delta);
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		batch.dispose();
		raindropPool.clear();
	}

}
