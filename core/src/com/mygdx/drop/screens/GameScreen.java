package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Constants;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

import java.util.Iterator;

public class GameScreen implements Screen {
	private static final int SCORE_INCREMENT_THRESHOLD = 999;
	private static final Object SPEED_INCREMENT = 1;
	final DropGame game;
	private float lastDropTime;
	private Sound dropSound;
	private Sound rainMusic;

	OrthographicCamera camera;
	SpriteBatch batch;

	// entities
	Bucket bucket;
	RaindropPool raindropPool;
	Array<Raindrop> raindrops;

	// game state
	int score;
	String scoreString;
	GlyphLayout scoreLayout;

	public GameScreen(final DropGame game) {
		this.game = game;

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newSound(Gdx.files.internal("rain.mp3"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

		batch = new SpriteBatch();

		bucket = new Bucket();
		bucket.setPosition(Constants.VIEWPORT_HEIGHT / 2f - Constants.BUCKET_WIDTH / 2f, Constants.BUCKET_HEIGHT);

		raindropPool = new RaindropPool();
		raindrops = new Array<Raindrop>();

		score = 0;
		scoreString = "Score: 0";
		scoreLayout = new GlyphLayout();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		bucket.draw(batch);
		for (Raindrop raindrop : raindrops) {
			raindrop.draw(batch);
		}
		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.setPosition(touchPos.x - Constants.BUCKET_WIDTH / 2f, Constants.BUCKET_HEIGHT);
		}

		if (MathUtils.random() < delta * Constants.RAINDROP_SPAWN_FREQUENCY) {
			Raindrop raindrop = raindropPool.obtain();
			raindrop.setPosition(MathUtils.random(0, Constants.VIEWPORT_WIDTH - Constants.RAINDROP_WIDTH), Constants.VIEWPORT_HEIGHT);
			raindrops.add(raindrop);
		}

		Iterator<Raindrop> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Raindrop raindrop = iter.next();
			raindrop.update(delta);
			if (raindrop.getBounds().overlaps(bucket.getBounds())) {
				playDropSound();
				iter.remove();
				incrementScore();
			}
			if (raindrop.getPosition().y + raindrop.getHeight() < 0) {
				iter.remove();
				raindropPool.free(raindrop);
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	public void playDropSound() {
		dropSound.play();
	}

	private void incrementScore() {
		score++;
		scoreString = "Score: " + score;
		scoreLayout.setText(game.getFont(), scoreString);
	}


	@Override
	public void dispose() {
		batch.dispose();
		raindropPool.clear();
	}
}
