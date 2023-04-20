package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.core.CollisionHandler;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Constants;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

import java.util.Iterator;

import static com.mygdx.drop.entities.Constants.MAX_DROPS;

public class GameScreen implements Screen, InputProcessor {
	final DropGame game;
	private Sound dropSound;
	private Sound rainMusic;
	private boolean movingLeft;
	private boolean movingRight;
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
	BitmapFont font;

	int dropsGathered;

	long lastDropTime;

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

		font = new BitmapFont();

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

		Array<Raindrop> raindropsToRemove = new Array<>();

		handleInput();
		bucket.update();
		bucket.updateBounds();
		spawnRaindrop();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.draw(batch, "Drops Collected: " + dropsGathered, 0, 480);
		bucket.draw(batch);
		for (Raindrop raindrop : raindrops) {
			if (CollisionHandler.collidesWith(bucket.getPosition().x, bucket.getPosition().y,
					bucket.getBounds().width, bucket.getBounds().height,
					raindrop.getPosition().x, raindrop.getPosition().y,
					raindrop.getBounds().width, raindrop.getBounds().height)) {
				raindropsToRemove.add(raindrop);
				dropsGathered++;
			}


			raindrop.draw(batch);
		}
		raindrops.removeAll(raindropsToRemove, true);
		batch.end();
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
			Raindrop raindrop = raindropPool.obtain();
			raindrop.getPosition().x = MathUtils.random(0, Gdx.graphics.getWidth() - 64);
			raindrop.getPosition().y = Gdx.graphics.getHeight() - 64;

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
		if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
			movingLeft = true;
		} else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
			movingRight = true;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
			movingLeft = false;
		} else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
			movingRight = false;
		}
		return true;
	}

	private void handleInput() {
		// Move bucket with keyboard
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			bucket.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			bucket.moveRight();
		}

		// Move bucket with mouse
		if (Gdx.input.isTouched()) {
			int screenX = Gdx.input.getX();
			if (screenX < camera.viewportWidth / 2) {
				bucket.moveLeft();
			} else {
				bucket.moveRight();
			}
		}
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
