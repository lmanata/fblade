package com.manata.even.units;

import static com.manata.even.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.manata.even.handlers.B2DVars;
import com.manata.even.main.Game;

public class Player {

	protected Animation animation;
	protected float width;
	protected float height;
	public Body playerbody;
	public BodyDef bdef;
	public FixtureDef fdef;
	public Fixture shape_fixture;
	public World world;

	public Player(World world) {

		bdef = new BodyDef();
		bdef.position.set((Game.V_WIDTH / 2) / PPM, 0.2f);
		bdef.type = BodyType.DynamicBody;

		playerbody = world.createBody(bdef);
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(12 / PPM, 12 / PPM);

		fdef = new FixtureDef();
		fdef.shape = pshape;
		fdef.friction = 0;
		fdef.density = 0f;
		fdef.filter.categoryBits = B2DVars.bit_player_ultra;
		fdef.filter.maskBits = B2DVars.bit_wall;
		fdef.isSensor = false;

		shape_fixture = playerbody.createFixture(fdef);
		shape_fixture.setUserData("player");
		playerbody.setUserData("player");

		Texture tex = Game.res.getTexture("player");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];

		animation = new Animation();
		setAnimation(sprites, 1 / 35f);
	}

	public void setAnimation(TextureRegion[] region, float delay) {
		animation.setFrames(region, delay);
		width = region[0].getRegionWidth();
		height = region[0].getRegionHeight();

	}

	public void update(float dt) {
		animation.update(dt);
	}

	public void render(SpriteBatch sb, Boolean debug) {
		if (debug)
			return;
			
		sb.draw(animation.getFrame(), playerbody.getPosition().x * B2DVars.PPM - width / 2,
				playerbody.getPosition().y * B2DVars.PPM - height / 2);
	}

	public Body getBody() {
		return playerbody;
	}

	public Vector2 getPosition() {
		return playerbody.getPosition();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
