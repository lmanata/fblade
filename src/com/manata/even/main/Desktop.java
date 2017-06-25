package com.manata.even.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Desktop {

	public static void main(String[] args) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = Game.TITLE;
		cfg.width = Game.V_WIDTH * Game.SCALE;
		cfg.height = Game.V_HEIGHT * Game.SCALE;
		cfg.foregroundFPS = 240;
		cfg.vSyncEnabled = false;
		new LwjglApplication(new Game(), cfg);
	}
}
