package com.manata.even.states;

import static com.manata.even.handlers.B2DVars.PPM;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.manata.even.handlers.CollisionHandler;
import com.manata.even.handlers.GameStateManager;
import com.manata.even.handlers.ScoreHandler;
import com.manata.even.handlers.Sounds;
import com.manata.even.main.Game;
import com.manata.even.units.Player;
import com.manata.even.units.Segments;
import com.manata.even.units.Borders;
import com.manata.even.units.Walls;

public class Play extends GameState {

	private Player player;
	private ScoreHandler scorehandler;
	private CollisionHandler collisions;
	private Texture segmentTexture;
	
	private World zawarudo;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dCam;
	private GlyphLayout layout;
	private BitmapFont Font;

	private int difficulty = 1;

	private float yGravity;
	private float segmentY = 0;
	private float score = 0;
	private float recordfade = 0;

	private boolean debug;
	private boolean timeStopped = false;
	private boolean newrecord = false;
	private boolean beast = false;
	private boolean change = true;

	private ArrayList<Body> bodylist = new ArrayList<Body>();
	private ArrayList<Walls> walls = new ArrayList<Walls>();
	private ArrayList<Walls> wallsRem = new ArrayList<Walls>();
	private ArrayList<Segments> segments = new ArrayList<Segments>();
	private ArrayList<Segments> segsRem = new ArrayList<Segments>();
	private ArrayList<Vector2> toadd = new ArrayList<Vector2>();

	private Segments lastSegment;
	private Matrix4 debugMatrix;
	private Random randomizer = new Random();

	public Play(GameStateManager gsm) {
		super(gsm);

		zawarudo = new World(new Vector2(0f, 0f), true);
		segmentTexture = Game.res.getTexture("segment");
		collisions = new CollisionHandler();
		zawarudo.setContactListener(collisions);
		b2dr = new Box2DDebugRenderer();
		scorehandler = new ScoreHandler();
		layout = new GlyphLayout();
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("res/fonts/Hyperspace Bold.ttf"));

		FreeTypeFontParameter s = new FreeTypeFontParameter();
		s.size = 24;
		s.color = Color.BLACK;
		s.borderColor = Color.WHITE;
		Font = gen.generateFont(s);
		layout.setText(Font, "" + score);

		player = new Player(zawarudo);

		Sounds.stopall();
		//Sounds.play("flash of the blade");
		//Sounds.loop("flash of the blade");

		for (int i = 0; i <= 23; i++)
			newSegment(segmentY);

		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
	}

	public void newWall(float posx, float posy) {

		Walls wall = new Walls();
		wall.newWall(zawarudo, posx, posy);
		wall.border = wall.newBorder(posx, posy);

		walls.add(wall);
	}

	public void newSegment(float y) {

		Borders border = new Borders();
		border.newChain(new Vector2(0f, segmentY), new Vector2(0f, segmentY + 0.32f), zawarudo);

		border.newChain(new Vector2(3.04f, segmentY), new Vector2(3.04f, segmentY + 0.32f), zawarudo);

		Segments segment = new Segments();
		segment.newSegment(zawarudo, y, segmentTexture);

		segmentY += 0.32f;
		segments.add(segment);

	}

	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			zawarudo.setGravity(new Vector2(0f, 0f));
			timeStop();
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			debug = !debug;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			walls.clear();
			segments.clear();
			wallsRem.clear();
			segsRem.clear();
			gsm.setState(GameStateManager.MENU);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

			switch (difficulty) {
			case 1:
				player.getBody().setLinearVelocity(-6f, zawarudo.getGravity().y);
				break;
			case 2:
				player.getBody().setLinearVelocity(-8f, zawarudo.getGravity().y);
				break;
			case 3:
				player.getBody().setLinearVelocity(-9f, zawarudo.getGravity().y);
				break;
			case 4:
				player.getBody().setLinearVelocity(-12f, zawarudo.getGravity().y);
				break;
			case 5:
				player.getBody().setLinearVelocity(-15f, zawarudo.getGravity().y);
				break;
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			switch (difficulty) {
			case 1:
				player.getBody().setLinearVelocity(6f, zawarudo.getGravity().y);
				break;
			case 2:
				player.getBody().setLinearVelocity(8f, zawarudo.getGravity().y);
				break;
			case 3:
				player.getBody().setLinearVelocity(9f, zawarudo.getGravity().y);
				break;
			case 4:
				player.getBody().setLinearVelocity(12f, zawarudo.getGravity().y);
				break;
			case 5:
				player.getBody().setLinearVelocity(15f, zawarudo.getGravity().y);
				break;
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.getBody().setLinearVelocity( player.getBody().getLinearVelocity().x, -3f );
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.getBody().setLinearVelocity( player.getBody().getLinearVelocity().x, 3f );
		}
	}

	public void update(float dt) {
		checkCollision();
		
		cam.position.set(Game.V_WIDTH / 2, (player.getBody().getPosition().y * PPM) + 280f, 0);
		cam.update();
		
		b2dCam.position.set( (Game.V_WIDTH  / PPM ) / 2, player.getBody().getPosition().y + 280f / 100, 10);
		b2dCam.update();
		
		handleSegments();
		handleInput();
		
		//newRandom();
		for (Vector2 vec : toadd)
			newWall(vec.x, vec.y);
		toadd.clear();

		if (score > scorehandler.topscore) {
			if (!beast) {
				Sounds.stopall();
				//Sounds.play("16 beast");
				//Sounds.loop("16 beast");
			}
			beast = true;
			newrecord = true;
		}

		if (score > 49 && change == true) {
			change = false;
		}

		score += 0.02;
		
		if( !timeStopped ){
			yGravity = ( (score / 10) * 2 ) + 3;
			zawarudo.setGravity(new Vector2( 0f, yGravity));	
		}
		
		for (Body b : bodylist) {
			zawarudo.destroyBody(b);
		}
		bodylist.clear();

		for (Segments s : segsRem) {
			segments.remove(s);
			s.shape.dispose();
		}
		segsRem.clear();

		for (Walls s : wallsRem) {
			s.shape.dispose();
			walls.remove(s);
		}
		wallsRem.clear();

		zawarudo.step(dt, 6, 2);
		player.update(dt);

	}

	public void render() {
		
		System.out.println(Gdx.graphics.getFramesPerSecond());

		player.getBody().setLinearVelocity(0, zawarudo.getGravity().y);

		Gdx.gl20.glClearColor(0, 0, 0.5f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		sb.setProjectionMatrix(cam.combined);
		sb.begin();

		for (Segments segment : segments) {
			if ((!cam.frustum.pointInFrustum(segment.position))
					&& segment.body.getPosition().y + 5 < player.getPosition().y) {
				bodylist.add(segment.body);
				segsRem.add(segment);
			} else {
				segment.render(sb, debug);
			}
		}

		for (Walls wall : walls) {
			if (!cam.frustum.pointInFrustum(wall.position) && wall.body.getPosition().y < player.getPosition().y) {
				bodylist.add(wall.body);
				wallsRem.add(wall);
			} else {
				wall.render(sb, debug);
			}
		}

		layout.setText(Font, "Score:" + (Float.toString(score)).substring(0,
				((Float.toString(score)).length() < 4) ? (Float.toString(score)).length() : 4));
		Font.draw(sb, layout, 0f, cam.position.y - 300);

		if (newrecord) {
			if (recordfade >= 2) {
				newrecord = false;
			} else {
				recordfade += 0.01;
				layout.setText(Font, "New Record!");
				Font.draw(sb, layout, cam.position.x + layout.width, cam.position.y);
			}
		}

		if (debug) {
			debugMatrix = new Matrix4(b2dCam.combined);
			debugMatrix.scale(1, 1, 1);
			b2dr.render(zawarudo, debugMatrix);
		}
		
		player.render(sb, debug);		
		sb.end();
	}

	public void newRandom() {
		if (walls.size() > 30)
			return;

		Rectangle intersection = new Rectangle();

		float randomx = randomizer.nextFloat() * (6f - 0.5f) + 0.5f;
		float randomy = randomizer.nextFloat()
				* ((player.getPosition().y + (Game.V_HEIGHT / 100))
						- (player.getPosition().y + player.getHeight() + (player.getHeight() / 2)))
				+ (player.getPosition().y + player.getHeight() + (player.getHeight() / 2));
		if (walls.size() == 0) {
			toadd.add(new Vector2(randomx, randomy));
			return;
		}

		Rectangle tmpborder = newBorder(randomx, randomy);
		for (Walls wall : walls) {

			if (!Intersector.intersectRectangles(tmpborder, wall.border, intersection)
					&& wall == walls.get(walls.size() - 1)) {
				toadd.add(new Vector2(randomx, randomy));
				return;
			}
		}
	}

	public Rectangle newBorder(float posx, float posy) {

		float x = posx - ((Walls.width / 100) + 0.3f);
		float y = posy - ((Walls.height / 100) + 0.3f);

		Rectangle rec = new Rectangle(x, y, 1.12f + 0.3f, 0.32f + 0.3f);
		return rec;
	}

	public void dispose() {}
	public void handleSegments(){
		System.out.println("CAM Y => "+cam.position.y);
	
		if (segments.size() > 0)
			lastSegment = segments.get(segments.size() - 1);
		
		if ( segments.size() == 0){
			System.out.println("DEBUGGER PLS");	
		}
		
		if( cam.position.y > lastSegment.nextposition.y ){
			System.out.println("CAM > LAST SEGMENT");
		}
		
//		System.out.println("GOT SEGMENT Y => "+lastSegment.position.y);
//		System.out.println("NEXT SEGMENT Y => "+lastSegment.nextposition.y);
//		System.out.println("SEGMENT SIZE => "+segments.size());
		
//		if(!(cam.frustum.pointInFrustum(lastSegment.position))){
//			return;
//		}
		
		while (segments.size() < 55) {
			newSegment(segmentY);
		}
		return;
		
	}
	public void checkCollision(){
		if (collisions.getCollapse()) {
			scorehandler.print((Float.toString(score)).substring(0,
					((Float.toString(score)).length() < 4) ? (Float.toString(score)).length() : 4) + ";");
			gsm.setState(GameStateManager.PLAY);
		}
	}
	public void timeStop(){ timeStopped = !timeStopped; }
}