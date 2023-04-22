package com.github.disparter.raindrop.catcher.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.disparter.github.raindrop.catcher.DropGame;

import static com.disparter.github.raindrop.catcher.ui.entities.Constants.VIEWPORT_HEIGHT;
import static com.disparter.github.raindrop.catcher.ui.entities.Constants.VIEWPORT_WIDTH;

public class DesktopLauncher {
	public static void main(final String[] arg) {
		final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Raindrop Catcher");
		config.setWindowedMode(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new DropGame(), config);
	}
}
