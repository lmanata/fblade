package com.manata.even.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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

public class Walls {

	public Body body;
	public Rectangle rectangle;
	public Rectangle border;
	public World world;
	public BodyDef def;
	public PolygonShape shape;
	public FixtureDef fdef;
	public Fixture shape_fixture;
	public Texture texture;
	public Sprite sprite;
	public float y;

	public Vector3 position;
	public static int width = 56;
	public static int height = 16;

	public void newWall(World world, float posx, float posy) {

		y = posy;
		def = new BodyDef();
		shape = new PolygonShape();
		fdef = new FixtureDef();

		texture = Game.res.getTexture("wall");
		sprite = new Sprite(texture);

		def.position.set(posx, posy);
		def.type = BodyType.StaticBody;

		body = world.createBody(def);
		shape.setAsBox(0.56F, 0.16F);

		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.bit_wall;

		shape_fixture = body.createFixture(fdef);
		shape_fixture.setUserData("wall");
		body.setUserData("wall");
		this.world = world;

		position = new Vector3(posx, (posy * 100) + 16, 0);
		rectangle = newRectangle(posx, posy);
		border = newBorder(posx, posy);
	}

	public Rectangle newRectangle(float posx, float posy) {
		float x = posx - (width / B2DVars.PPM);
		float y = posy - (height / B2DVars.PPM);

		Rectangle rec = new Rectangle(x, y, 1.12f, 0.32f);
		return rec;
	}

	public Rectangle newBorder(float posx, float posy) {

		float x = posx - ((width / B2DVars.PPM) + 0.3f);
		float y = posy - ((height / B2DVars.PPM) + 0.3f);

		Rectangle rec = new Rectangle(x, y, 1.12f + 0.3f, 0.32f + 0.3f);
		return rec;
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
