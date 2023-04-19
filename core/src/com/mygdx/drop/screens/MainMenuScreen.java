package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.drop.DropGame;

public class MainMenuScreen implements Screen {

	final DropGame game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture playButtonImg;
	Rectangle playButton;
	Texture background;

	public MainMenuScreen(final DropGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		playButtonImg = new Texture("playButton.png");
		playButton = new Rectangle();
		playButton.x = 800 / 2 - 250 / 2;
		playButton.y = 200;
		playButton.width = 250;
		playButton.height = 100;
		background = new Texture("bg.png");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0, 800, 480);
		batch.draw(playButtonImg, playButton.x, playButton.y, playButton.width, playButton.height);
		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if (playButton.contains(touchPos.x, touchPos.y)) {
				game.create();
				dispose();
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		playButtonImg.dispose();
		background.dispose();
	}
}
