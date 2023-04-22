package com.disparter.github.raindrop.catcher.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.disparter.github.raindrop.catcher.DropGame;
import com.disparter.github.raindrop.catcher.ui.core.CollisionHandler;
import com.disparter.github.raindrop.catcher.ui.entities.Bucket;
import com.disparter.github.raindrop.catcher.ui.entities.Constants;
import com.disparter.github.raindrop.catcher.ui.entities.Raindrop;
import com.disparter.github.raindrop.catcher.ui.entities.RaindropPool;

public class GameScreen implements Screen, InputProcessor {
	final DropGame game;
	private final int speed;
	private final Sound dropSound;
	private final Sound rainMusic;
	OrthographicCamera camera;
	SpriteBatch batch;

	// entities
	Bucket bucket;
	RaindropPool raindropPool;
	Array<Raindrop> raindrops;

	// game state
	BitmapFont font;
	int dropsGathered;
	long lastDropTime;

	public GameScreen(final DropGame game, final int speed) {
		this.game = game;

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newSound(Gdx.files.internal("rain.mp3"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

		batch = new SpriteBatch();

		bucket = new Bucket();

		raindropPool = new RaindropPool();
		raindrops = new Array<>();

		font = new BitmapFont();

		this.speed = speed;

	}

	@Override
	public void show() {
	}

	@Override
	public void render(final float delta) {
		// clear the screen with a dark blue color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		final Array<Raindrop> raindropsToRemove = new Array<>();

		// update the camera's matrices
		camera.update();

		// render the bucket
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucket.getSprite(), bucket.getBounds().x, bucket.getBounds().y);
		batch.end();

		handleInput();

		// Spawn a new raindrop if it's time
		if (TimeUtils.nanoTime() - lastDropTime > Constants.LAST_DROP_FREQUENCY_IN_NANOS) {
			spawnRaindrop();
		}

		batch.begin();
		// Move the raindrops and remove any that are below the bottom of the screen
		for (final Raindrop raindrop : raindrops) {
			raindrop.update(delta);
			batch.draw(raindrop.getSprite(), raindrop.getBounds().x, raindrop.getBounds().y);

			if (raindrop.getBounds().y + raindrop.getBounds().height < 0) {
				game.setScreen(new GameOverScreen(game));
			}

			// Check for collisions between the raindrops and the bucket
			if (CollisionHandler.collidesWith(bucket.getBounds(), raindrop.getBounds())) {
				dropsGathered++;
				raindropsToRemove.add(raindrop);
			}
		}

		final float speed = (float) Math.round(raindropPool.getSpeed() * 100) / 100;

		// Remove any raindrops that were caught or have gone off the screen
		raindrops.removeAll(raindropsToRemove, false);
		raindropsToRemove.clear();
		font.draw(batch, "Drops Collected: " + dropsGathered, 0, Constants.DROPS_COLLECTED_MESSAGE_POSITION_Y);
		font.draw(batch, "Speed: " + speed, Constants.VIEWPORT_WIDTH - 150, Constants.DROPS_COLLECTED_MESSAGE_POSITION_Y);

		batch.end();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			bucket.moveTo(bucket.getBounds().x - Constants.BUCKET_WIDTH / 2, bucket.getBounds().y);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			bucket.moveTo(bucket.getBounds().x + Constants.BUCKET_WIDTH / 2, bucket.getBounds().y);
		}

		if (Gdx.input.isTouched()) {
			final Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.moveTo(touchPos.x - Constants.BUCKET_WIDTH / 2, bucket.getBounds().y);
		}
	}
	@Override
	public void resize(final int width, final int height) {

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

	private void spawnRaindrop() {
		if (raindrops.size < Constants.MAX_DROPS) {
			raindropPool.setSpeedLevel(speed, dropsGathered);
			final Raindrop raindrop = raindropPool.obtain();
			raindrop.getBounds().x = MathUtils.random(0, Gdx.graphics.getWidth() - Constants.RAINDROP_WIDTH * 2);
			raindrop.getBounds().y = Gdx.graphics.getHeight() - Constants.RAINDROP_HEIGHT * 2;

			raindrops.add(raindrop);
			lastDropTime = TimeUtils.nanoTime();
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		raindropPool.clear();
		bucket.dispose();
	}

	@Override
	public boolean keyDown(final int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(final int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(final char character) {
		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
		return false;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(final float amountX, final float amountY) {
		return false;
	}
}
