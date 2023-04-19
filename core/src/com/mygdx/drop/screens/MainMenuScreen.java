package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.drop.DropGame;

import static com.mygdx.drop.entities.Constants.VIEWPORT_HEIGHT;
import static com.mygdx.drop.entities.Constants.VIEWPORT_WIDTH;

public class MainMenuScreen implements Screen {
	private Skin skin;
	private DropGame game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Stage stage;
	private BitmapFont font;
	private Label titleLabel;

	public MainMenuScreen(DropGame game) {
		this.game = game;
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
		this.stage = new Stage(viewport, new SpriteBatch());
		this.font = new BitmapFont();
		this.skin = setupSkin();
		this.titleLabel = new Label("Main Menu", skin);
		titleLabel.setPosition(VIEWPORT_WIDTH / 2f, 3 * VIEWPORT_HEIGHT / 4f, Align.center);
		stage.addActor(titleLabel);
	}

	private Skin setupSkin() {
		return new Skin(Gdx.files.internal("uiskin.json"));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		// create buttons
		TextButton exitButton = new TextButton("EXIT", skin);
		TextButton startButton = new TextButton("START", skin);

		// add listeners
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
			}
		});

		// set button positions
		exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);

		// add buttons to stage
		stage.addActor(exitButton);
		stage.addActor(startButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.update();
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

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		font.dispose();
	}

}