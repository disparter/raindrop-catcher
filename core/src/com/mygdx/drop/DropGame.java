package com.mygdx.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.drop.screens.GameScreen;

public class DropGame extends ApplicationAdapter {
    private GameScreen gameScreen;

    @Override
    public void create () {
        gameScreen = new GameScreen(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameScreen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose () {
        gameScreen.dispose();
    }
}
