package com.disparter.github.raindrop.catcher.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.disparter.github.raindrop.catcher.DropGame;
import com.disparter.github.raindrop.catcher.ui.entities.Constants;

public class MainMenuScreen implements Screen {
	private final Skin skin;
	private final DropGame game;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final Stage stage;
	private final BitmapFont font;
	private final Label titleLabel;
	private final Label speedLabel;
	private int speed;

	public MainMenuScreen(final DropGame game) {
		this.game = game;
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
		this.stage = new Stage(viewport, new SpriteBatch());
		this.font = new BitmapFont();
		this.skin = setupSkin();
		this.titleLabel = new Label("Main Menu", skin);
		this.speedLabel = new Label("Level: ", skin);
		titleLabel.setPosition(Constants.VIEWPORT_WIDTH / 2f, 3 * Constants.VIEWPORT_HEIGHT / 4f, Align.center);
		stage.addActor(titleLabel);
		speed = 1;
	}

	private Skin setupSkin() {
		return new Skin(Gdx.files.internal("uiskin.json"));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		// create buttons
		final TextButton exitButton = new TextButton("EXIT", skin);
		final TextButton startButton = new TextButton("START", skin);

		// level selector
		final SelectBox<String> speedBox = new SelectBox<>(skin);
		speedBox.setItems("Easy", "Normal", "Hard", "God");
		speedBox.setSelected("Normal"); // Default to normal speed
		speedBox.setWidth(100);

		// add listeners
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				Gdx.app.exit();
			}
		});
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				game.setScreen(new GameScreen(game, speed));
			}
		});
		speedBox.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				// Get the selected speed box level
				final int selectedIndex = speedBox.getSelectedIndex();

				// Set the speed based on the selected level
				switch (selectedIndex) {
					// Easy
					case 1:
						speed = 2; // Normal
						break;
					case 2:
						speed = 4; // Hard
						break;
					case 3:
						speed = 8; // God
						break;
					default:
						speed = 1; // Default to easy if something went wrong
				}
			}
		});

		// set button positions
		exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);
		speedBox.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 100);

		// add buttons to stage
		stage.addActor(exitButton);
		stage.addActor(startButton);
		stage.addActor(speedBox);
	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
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