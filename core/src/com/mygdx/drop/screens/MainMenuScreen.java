package com.mygdx.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
	private final Skin skin;
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
		this.skin = new Skin();
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;
		skin.add("default", labelStyle);
		this.titleLabel = new Label("Main Menu", skin);
		titleLabel.setPosition(VIEWPORT_WIDTH / 2f, 3 * VIEWPORT_HEIGHT / 4f, Align.center);
		stage.addActor(titleLabel);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Button exitButton = new TextButton("EXIT", skin, "default");
		exitButton.setPosition(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 3f, Align.center);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		Button startButton = new TextButton("START", skin, "default");
		startButton.setPosition(VIEWPORT_WIDTH / 2f, 2 * VIEWPORT_HEIGHT / 3f, Align.center);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
			}
		});

		stage.addActor(exitButton);
		stage.addActor(startButton);

		stage.act(delta);
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