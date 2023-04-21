package com.disparter.github.raindrop.catcher;

import com.badlogic.gdx.Game;
import com.disparter.github.raindrop.catcher.screens.MainMenuScreen;

public class DropGame extends Game {
    private MainMenuScreen mainMenuScreen;
    @Override
    public void create () {
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }
    public void render() {
        super.render();
    }

    public void dispose() {
        mainMenuScreen.dispose();
    }




}
