package com.mygdx.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.drop.screens.MainMenuScreen;

public class DropGame extends Game {
    private BitmapFont font;
    private MainMenuScreen mainMenuScreen;

    @Override
    public void create () {
        mainMenuScreen = new MainMenuScreen(this);
        font = new BitmapFont();
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        mainMenuScreen.dispose();
    }
    public BitmapFont getFont() {
        return font;
    }

}
