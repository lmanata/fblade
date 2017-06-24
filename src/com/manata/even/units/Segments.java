package com.manata.even.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.manata.even.handlers.B2DVars;
import com.manata.even.main.Game;

public class Segments {

	public Body body;
	public World world;
	public BodyDef def;
	public PolygonShape shape;
	public FixtureDef fdef;
	public Texture texture;
	public Sprite sprite;
	public Vector3 position;
	public Vector3 nextposition;

	public int width = 304;
	public int height = 16;

	public void newSegment(World world, float posy) {
		def = new BodyDef();
		shape = new PolygonShape();
		fdef = new FixtureDef();

		texture = Game.res.getTexture("segment");
		sprite = new Sprite(texture);

		def.position.set(3.04f, posy);
		def.type = BodyType.StaticBody;

		body = world.createBody(def);
		shape.setAsBox(3.04f, 0.16f);

		fdef.shape = shape;
		fdef.filter.categoryBits = 0;
		fdef.filter.maskBits = 0;

		Fixture shape_fixture = body.createFixture(fdef);
		shape_fixture.setUserData("segment");

		this.world = world;

		position = new Vector3(3.04f, posy * B2DVars.PPM, 0f);
		nextposition = new Vector3(3.04f, (posy + 0.32f) * B2DVars.PPM, 0);

	}

	public void dispose() {
		shape.dispose();
	}

	public void render(SpriteBatch sb) {
		sb.draw(texture, this.x() * B2DVars.PPM - width, this.y() * B2DVars.PPM - height);
	}

	public float x() {
		return body.getPosition().x;
	};

	public float y() {
		return body.getPosition().y;
	};

}
