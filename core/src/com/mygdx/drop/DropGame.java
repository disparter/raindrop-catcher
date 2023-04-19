package com.mygdx.drop;

import com.badlogic.gdx.Game;
import com.mygdx.drop.screens.MainMenuScreen;

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
