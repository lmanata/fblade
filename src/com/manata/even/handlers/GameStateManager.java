package com.manata.even.handlers;


import com.manata.even.main.Game;
import com.manata.even.states.GameState;
import com.manata.even.states.MenuState;
import com.manata.even.states.Play;
import com.manata.even.states.ScoreState;

public class GameStateManager {

	private Game game;
	private GameState gameState;
	public static final int PLAY = 1337;
	public static final int MENU = 0;
	public static final int SCORE = 420;
	
	public GameStateManager(Game game,Content res){
		this.game = game;
		setState(MENU);
	}
	
	public Game game(){ return game; }
	
	public void update(float dt){
		gameState.update(dt);
	}
	
	public void render(){
		gameState.render();
	}
	
	
	public void setState(int state){
		if(state == PLAY)  gameState = new Play(this);
		
		if(state == MENU)	gameState = new MenuState(this);
		
		if(state == SCORE) gameState = new ScoreState(this);
	}
}