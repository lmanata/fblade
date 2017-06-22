package com.manata.even.units;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.manata.even.handlers.B2DVars;

public class Borders {
	
	public void newChain(Vector2 bot, Vector2 top,World world){
		Vector2 locs[] = new Vector2[2];
		locs[0] = bot;
		locs[1] = top;
		
		BodyDef chaindef = new BodyDef();
		chaindef.position.set(bot);
		chaindef.type = BodyType.StaticBody;
		
		Body chainbody = world.createBody(chaindef);
		
		ChainShape chain = new ChainShape();
		chain.createChain(locs);
		
		FixtureDef chainfdef = new FixtureDef();
		chainfdef.shape = chain;
		chainfdef.filter.categoryBits = B2DVars.bit_wall;
		
		Fixture chain_fixture = chainbody.createFixture(chainfdef);
		chain_fixture.setUserData("boundaries");
		
	}
	
}
