package com.manata.even.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener {

	public boolean collapse = false;

	@Override
	public void beginContact(Contact c) {
		if (c.getFixtureA().getUserData() == "player"
				|| c.getFixtureB().getUserData() == "player" && c.getFixtureA().getUserData() == "wall"
				|| c.getFixtureB().getUserData() == "wall")
			collapse = true;
	}

	@Override
	public void endContact(Contact c) {
	}

	@Override
	public void preSolve(Contact c, Manifold m) {
	}

	@Override
	public void postSolve(Contact c, ContactImpulse i) {
	}

	public boolean getCollapse() {
		return collapse;
	}

}