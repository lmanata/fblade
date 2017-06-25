package com.manata.even.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.manata.even.handlers.Content;
import com.manata.even.handlers.GameStateManager;
import com.manata.even.handlers.Sounds;

public class Game implements ApplicationListener {

	public static final String TITLE = "Flash of the Blade";
	public static final int V_WIDTH = 608;
	public static final int V_HEIGHT = 640;
	public static final int SCALE = 1;
	public static final float STEP = 1 / 30f;
	public static Content res;

	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameStateManager gsm;

	public void create() {

		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		res = new Content();
		loadRes();
		gsm = new GameStateManager(this, res);

	}

	public void render() {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}

	public void dispose() {
	}

	public void setInputProcessor(InputProcessor dealer) {
	};

	public SpriteBatch getSpriteBatch() {
		return sb;
	}

	public OrthographicCamera getCamera() {
		return cam;
	}

	public OrthographicCamera getHudCamera() {
		return hudCam;
	}

	public Content getContent() {
		return res;
	}

	public void resize(int w, int h) {
	}

	public void pause() {
	}

	public void resume() {
	}

	private void loadRes() {
		res.loadTexture("res/sprites/spritesheet.png", "player");
		res.loadTexture("res/maps/wallt.png", "wall");
		res.loadTexture("res/maps/tr.jpg", "segment");

		Sounds.load("res/music/flash of the blade.mp3", "flash of the blade");
		// Sounds.load("res/music/lesnar titantron.mp3", "beast from the east");
		Sounds.load("res/music/lesnar 16 bit.mp3", "16 beast");
		Sounds.load("res/music/wyatt 16 bit.mp3", "menu");
	}
}
