package com.mygdx.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.drop.screens.GameScreen;

public class DropGame extends Game {
    private GameScreen gameScreen;

    private Sound dropSound;
    private Sound rainMusic;
    private BitmapFont font;

    @Override
    public void create () {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newSound(Gdx.files.internal("rain.mp3"));
        font = new BitmapFont();
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        gameScreen.dispose();
    }

    public void playDropSound() {
        dropSound.play();
    }

    public BitmapFont getFont() {
        return font;
    }

}
