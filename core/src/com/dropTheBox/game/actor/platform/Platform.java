package com.dropTheBox.game.actor.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dropTheBox.game.actor.Actor;
import com.dropTheBox.game.layer.ActorLayer;

public class Platform extends Actor {
	
	public void init(ActorLayer layer, float x, float y, float w, float h){
		super.init(layer, BodyType.KinematicBody, x, y, w, h);
		getFixture().getFilterData().groupIndex = 3;
	}


	@Override
	public void draw(SpriteBatch batch) {
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected Shape createShape() {
		PolygonShape shape = new PolygonShape();
	    shape.setAsBox(width/ 2f,height /2f);
	    return shape;
	}

	@Override
	public void setSize(float w, float h) {
		PolygonShape shape = (PolygonShape) fixture.getShape();
		shape.setAsBox(w/ 2f, h /2f);
	}

}
