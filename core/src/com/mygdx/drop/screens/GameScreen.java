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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.entities.Bucket;
import com.mygdx.drop.entities.Constants;
import com.mygdx.drop.entities.Raindrop;
import com.mygdx.drop.entities.RaindropPool;

import java.util.Iterator;

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
		handleInput();

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
		scoreLayout.setText(font, scoreString);
	}


	@Override
	public void dispose() {
		batch.dispose();
		raindropPool.clear();
	}

	public Batch getBatch(){
		return this.batch;
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
		if (movingLeft) {
			bucket.moveLeft();
		}
		if (movingRight) {
			bucket.moveRight();
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
