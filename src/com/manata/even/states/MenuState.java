package com.manata.even.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Matrix4;
import com.manata.even.handlers.GameStateManager;
import com.manata.even.handlers.Sounds;
import com.manata.even.main.Game;

public class MenuState extends GameState {

	private BitmapFont font;
	private BitmapFont tFont;
	private GlyphLayout Tlayout;
	private GlyphLayout layout;
	Matrix4 normalProjection;

	private final String title = "Flash of the Blade";

	private int currentItem;
	private String[] menuItems;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		create();
	}

	public void create() {

		Sounds.stop("16 beast");
		Sounds.stop("flash of the blade");
		Sounds.play("menu");
		Tlayout = new GlyphLayout();
		layout = new GlyphLayout();
		cam = new OrthographicCamera();
		currentItem = 1;

		normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("res/fonts/Hyperspace Bold.ttf"));

		FreeTypeFontParameter s = new FreeTypeFontParameter();
		s.size = 54;
		s.color = Color.WHITE;
		s.borderColor = Color.BLACK;
		tFont = gen.generateFont(s);

		s.size = 26;
		s.borderColor = Color.BLACK;
		font = gen.generateFont(s);
		Tlayout.setText(tFont, title);

		menuItems = new String[] { "", "Play", "Highscores", "Quit" };
	}

	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (currentItem == 1) {
				currentItem = 3;
			} else if (currentItem > 1) {
				currentItem--;
			}

		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (currentItem == 3) {
				currentItem = 1;
			} else {
				currentItem++;
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			select();
		}
	}

	public void select() {
		if (currentItem == 1) {
			gsm.setState(GameStateManager.PLAY);
		} else if (currentItem == 2) {
			gsm.setState(GameStateManager.SCORE);
		} else if (currentItem == 3) {
			Gdx.app.exit();
		}
	}

	public void update(float dt) {
		handleInput();
	}

	public void render() {

		Gdx.gl20.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(normalProjection);
		sb.begin();
		tFont.draw(sb, Tlayout, (Game.V_WIDTH - Tlayout.width) / 2, Game.V_HEIGHT - Tlayout.height);

		for (int i = 1; i < menuItems.length; i++) {

			if (currentItem == i)
				font.setColor(Color.RED);
			else
				font.setColor(Color.WHITE);
			layout.setText(font, menuItems[i]);
			font.draw(sb, layout, (Game.V_WIDTH - layout.width) / 2, ((Game.V_HEIGHT - layout.height - 115) - 100 * i));

		}

		sb.end();

	}

	public void dispose() {

	}

}
