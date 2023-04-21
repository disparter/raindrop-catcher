package com.disparter.github.raindrop.catcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.disparter.github.raindrop.catcher.DropGame;

public class GameOverScreen implements Screen {

    private final DropGame game;
    private final Stage stage;
    private final Label gameOverLabel;

    private float stateTime;

    public GameOverScreen(final DropGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        gameOverLabel = new Label("Game Over", new Label.LabelStyle(new BitmapFont(), Color.RED));
        gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2f - gameOverLabel.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        stage.addActor(gameOverLabel);
        stateTime = 0f;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(final float delta) {
        stateTime += delta;
        if (stateTime >= 5f) {
            game.dispose();
            Gdx.app.exit();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
