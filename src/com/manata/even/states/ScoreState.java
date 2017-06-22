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
import com.manata.even.handlers.ScoreHandler;
import com.manata.even.main.Game;

public class ScoreState extends GameState{

	private ScoreHandler scoreh;
	private BitmapFont font;
	private BitmapFont tFont;
	private GlyphLayout Tlayout;
	private GlyphLayout layout;
	Matrix4 normalProjection;

	private final String title = "Flash of the Blade";
	
	private int currentItem;
	private String[] menuItems;
	private float[] scoresl;
	
	public ScoreState(GameStateManager gsm) {
		super(gsm);
		create();
	}

	
	public void create(){
		
		scoreh = new ScoreHandler();
		Tlayout = new GlyphLayout();
		layout = new GlyphLayout();
		cam = new OrthographicCamera();
		currentItem = 1;
		
		normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fonts/Hyperspace Bold.ttf")
				);
		
		FreeTypeFontParameter s = new FreeTypeFontParameter();
		s.size = 54;
		s.color = Color.WHITE;
		s.borderColor = Color.BLACK;
		tFont = gen.generateFont(s);
		
		s.size = 26;
		s.borderColor = Color.BLACK;
		font = gen.generateFont(s);
		Tlayout.setText(tFont, title);
		
		scoresl = scoreh.getScores(scoreh.scores);
		
		menuItems = new String[]{"Voltar","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'","'0'",};
		
		if(scoresl == null) System.out.println("itried");
		for(int i = 0; i<scoresl.length;i++){
			if(i == 0){menuItems[i] = "Voltar";continue;}
			if(i == 10) break;
			menuItems[i] = "'"+Float.toString(scoresl[i-1])+"'";
		};
	}
	
	
	public void handleInput() {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && currentItem == 0) currentItem = 1;
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && currentItem == 0) currentItem = 1;
		else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) currentItem = 0;
		
		
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			if(currentItem == 0){
				currentItem = 10;
			}
			else if(currentItem > 0) {
				currentItem--;
			}
			
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			if(currentItem == 10){currentItem=0;}
			else{currentItem++;}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){select();}
	}
	public void select(){
		if(currentItem == 0){
			gsm.setState(GameStateManager.MENU);
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
		tFont.draw(sb, Tlayout, (Game.V_WIDTH - Tlayout.width) /2, Game.V_HEIGHT - Tlayout.height);
		
		int cnt = 0;
		for(int i = 0; i<menuItems.length;i++){
			if(cnt == 11) break;
			if(currentItem == i) font.setColor(Color.RED);
			else font.setColor(Color.WHITE);
			if(i == 0){
				layout.setText(font, menuItems[i]);
				font.draw(sb, layout, (Game.V_WIDTH - layout.width) / 3 - 15, Game.V_HEIGHT/2);cnt++;continue;
			}
			layout.setText(font, menuItems[i]);
			font.draw(sb, layout, (Game.V_WIDTH - layout.width) / 2, ((Game.V_HEIGHT - layout.height - 115) -30 * i ));
			cnt++;
		}
		
		sb.end();
		
	}


	public void dispose() {
		
		
	}
}
