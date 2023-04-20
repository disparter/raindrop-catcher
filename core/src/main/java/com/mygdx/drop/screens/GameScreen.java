package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.core.CollisionHandler;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Constants;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

import static com.mygdx.drop.entities.Constants.BUCKET_WIDTH;
import static com.mygdx.drop.entities.Constants.DROPS_COLLECTED_MESSAGE_POSITION_Y;
import static com.mygdx.drop.entities.Constants.LAST_DROP_FREQUENCY_IN_NANOS;
import static com.mygdx.drop.entities.Constants.MAX_DROPS;
import static com.mygdx.drop.entities.Constants.RAINDROP_HEIGHT;
import static com.mygdx.drop.entities.Constants.RAINDROP_WIDTH;
import static com.mygdx.drop.entities.Constants.VIEWPORT_WIDTH;

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

	public GameScreen(final DropGame game, int speed) {
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
	public void render(float delta) {
		// clear the screen with a dark blue color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Array<Raindrop> raindropsToRemove = new Array<>();

		// update the camera's matrices
		camera.update();

		// render the bucket
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucket.getSprite(), bucket.getBounds().x, bucket.getBounds().y);
		batch.end();

		handleInput();

		// Spawn a new raindrop if it's time
		if (TimeUtils.nanoTime() - lastDropTime > LAST_DROP_FREQUENCY_IN_NANOS) {
			spawnRaindrop();
		}

		batch.begin();
		// Move the raindrops and remove any that are below the bottom of the screen
		for (Raindrop raindrop : raindrops) {
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

		float speed = (float) Math.round(raindropPool.getSpeed() * 100) / 100;

		// Remove any raindrops that were caught or have gone off the screen
		raindrops.removeAll(raindropsToRemove, false);
		raindropsToRemove.clear();
		font.draw(batch, "Drops Collected: " + dropsGathered, 0, DROPS_COLLECTED_MESSAGE_POSITION_Y);
		font.draw(batch, "Speed: " + speed, VIEWPORT_WIDTH - 150, DROPS_COLLECTED_MESSAGE_POSITION_Y);

		batch.end();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			bucket.moveTo(bucket.getBounds().x - BUCKET_WIDTH / 2, bucket.getBounds().y);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			bucket.moveTo(bucket.getBounds().x + BUCKET_WIDTH / 2, bucket.getBounds().y);
		}

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.moveTo(touchPos.x - BUCKET_WIDTH / 2, bucket.getBounds().y);
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

	private void spawnRaindrop() {
		if (raindrops.size < MAX_DROPS) {
			raindropPool.setSpeedLevel(speed, dropsGathered);
			Raindrop raindrop = raindropPool.obtain();
			raindrop.getBounds().x = MathUtils.random(0, Gdx.graphics.getWidth() - RAINDROP_WIDTH*2);
			raindrop.getBounds().y = Gdx.graphics.getHeight() - RAINDROP_HEIGHT*2;

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
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
